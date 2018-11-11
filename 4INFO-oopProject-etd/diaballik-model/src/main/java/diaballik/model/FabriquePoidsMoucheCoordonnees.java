package diaballik.model;

import diaballik.model.exceptions.InvalidCoordinateException;
import diaballik.model.plateau.Plateau;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Fabrique poids mouche des coordonnées
 */
public class FabriquePoidsMoucheCoordonnees {

    public static FabriquePoidsMoucheCoordonnees INSTANCE = new FabriquePoidsMoucheCoordonnees();

    private List<List<Coordonnee>> coordonnees;

    public FabriquePoidsMoucheCoordonnees() {
        coordonnees = new ArrayList<>(Plateau.SIZE);
        Stream.iterate(0, (x) -> 0).//genere une liste
                limit(Plateau.SIZE).//qui possède SIZE elements
                forEach((ignored) -> coordonnees.add(new ArrayList<>(Plateau.SIZE))); //on itere dessus pour initaliser nos listes

        Stream.iterate(0, (x) -> x + 1).
                limit(Plateau.SIZE).
                forEach((ligne) -> //pour toutes les lignes
                        Stream.iterate(0, (x) -> x + 1).
                                limit(Plateau.SIZE).//pour toutes les colonnes
                                forEach((colonne) -> coordonnees.get(ligne).add(new Coordonnee(ligne, colonne))) //on initialise nos tableau de coordonnées
                );
    }

    /**
     * retourne la coordonnée poid-mouché
     *
     * @param ligne   - la ligne de la coordonnée voulue
     * @param colonne - la colonne de la coordonnée voulue
     * @return la coordonnée correspondante
     * @throws InvalidCoordinateException si les coordonnées ne sont pas sur le plateau
     */
    public Coordonnee getCoordonnees(/*@Nonnegative*/ final int ligne, /*@Nonnegative*/ final int colonne) {
        if (ligne >= Plateau.SIZE || colonne >= Plateau.SIZE || ligne < 0 || colonne < 0) {
            throw new InvalidCoordinateException("Invalide parameters ; getCoordonnes");
        }
        return coordonnees.get(ligne).get(colonne);
    }

}
