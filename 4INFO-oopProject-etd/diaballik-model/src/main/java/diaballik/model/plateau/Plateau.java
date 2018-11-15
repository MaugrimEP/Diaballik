package diaballik.model.plateau;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import diaballik.model.commande.Action;
import diaballik.model.coordonnee.Coordonnee;
import diaballik.model.coordonnee.FabriquePoidsMoucheCoordonnees;
import diaballik.model.deserializer.MapCoordonneeDeserializer;
import diaballik.model.joueur.Joueur;
import diaballik.model.plateau.piece.Balle;
import diaballik.model.plateau.piece.Pion;

import java.util.*;
import java.util.stream.Stream;

// We want to add a JSON attribute to know the type of the object.
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
// We have to identify the sub-classes to help the marshalling
@JsonSubTypes({
        @JsonSubTypes.Type(value = PlateauEnemyAmongUs.class),
        @JsonSubTypes.Type(value = PlateauRandomBall.class),
        @JsonSubTypes.Type(value = PlateauStandard.class)
})
// We add a unique identifier to the Json object
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public abstract class Plateau {
    public static int SIZE = 7;

    @JsonDeserialize(keyUsing = MapCoordonneeDeserializer.class)
    protected Map<Coordonnee, Case> lesCases;

    @JsonCreator
    protected Plateau(@JsonProperty("lesCases") final Map<Coordonnee, Case> lesCases) {
        this.lesCases = lesCases;
    }

    public Plateau() {
        lesCases = new HashMap<>();
        Stream.iterate(0, (x) -> x + 1).
                limit(Plateau.SIZE).
                forEach((ligne) -> //pour toutes les lignes
                        Stream.iterate(0, (x) -> x + 1).
                                limit(Plateau.SIZE).//pour toutes les colonnes
                                forEach((colonne) -> lesCases.put(FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(ligne, colonne), new Case())) //on initialise nos tableau de coordonnées
                );


    }

    /**
     * précondition : indexLigne est une index correct du tableau
     * donne une liste de coordonnee correspondant à une ligne
     *
     * @param indexLigne
     * @param size       - taille de ligne
     * @return liste de coordonnee correspondant à la ligne indexLigne
     */
    public static List<Coordonnee> getLigneCoordonnee(final int indexLigne, final int size) {
        final List<Coordonnee> ligneListe = new ArrayList<>();
        Stream.iterate(0, (x) -> x + 1).
                limit(size).
                forEach((colonne) -> ligneListe.add(FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(indexLigne, colonne)) //on initialise nos tableau de coordonnées
                );
        return ligneListe;
    }

    /**
     * initialise le plateau selon la stratégie standard
     *
     * @param j1
     * @param j2
     */
    public void init(final Joueur j1, final Joueur j2) {
        final List<Coordonnee> ligneJ1 = getLigneCoordonnee(Plateau.SIZE - 1, Plateau.SIZE);
        final List<Coordonnee> ligneJ2 = getLigneCoordonnee(0, Plateau.SIZE);

        ligneJ1.forEach((l) -> lesCases.get(l).setPion(new Pion(j1)));
        ligneJ2.forEach((l) -> lesCases.get(l).setPion(new Pion(j2)));

        final int milieu = Plateau.SIZE / 2 + 1;
        final Coordonnee milieuJ1 = FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(Plateau.SIZE - 1, milieu);
        final Coordonnee milieuJ2 = FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(0, milieu);
        lesCases.get(milieuJ1).setBalle(new Balle(j1));
        lesCases.get(milieuJ2).setBalle(new Balle(j2));
    }

    public void swapCase(final Coordonnee c1, final Coordonnee c2) {
        final Case balleJ1 = lesCases.get(c1);
        lesCases.put(c1, lesCases.get(c2));
        lesCases.put(c2, balleJ1);
    }

    /**
     * précondition : coup bon = true
     * déplace le pion ou la balle
     *
     * @param c1
     * @param c2
     */
    public void move(final Coordonnee c1, final Coordonnee c2) {
        final Case caseDepart = lesCases.get(c1);
        final Case caseArrivee = lesCases.get(c2);

        if (caseDepart.hasBall()) {
            caseArrivee.setBalle(caseDepart.takeBall());
        } else {
            caseArrivee.setPion(caseDepart.takePion());
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Plateau plateau = (Plateau) o;
        return Objects.equals(lesCases, plateau.lesCases);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lesCases);
    }

    public boolean coupBon(final Joueur joueur, final Action action) {
        final Coordonnee depart = action.getDepart();

        final Case caseDepart = lesCases.get(depart);

        final boolean caseVide = caseDepart.isEmpty();
        final boolean departNotMine = !caseDepart.getPion().getJoueur().equals(joueur);

        if (caseVide || departNotMine) {
            return false;
        }

        if (caseDepart.hasBall()) {
            return coupBonBall(joueur, action);
        } else {
            return coupBonPion(action);
        }
    }

    private boolean coupBonBall(final Joueur joueur, final Action action) {
        final Coordonnee depart = action.getDepart();
        final Coordonnee arrivee = action.getArrivee();

        final Case caseArrivee = lesCases.get(arrivee);

        final boolean sameArriveDepart = depart.equals(arrivee);
        final boolean arriveNotMine = !caseArrivee.getPion().getJoueur().equals(joueur);

        final boolean notSameLigne = !depart.sameLigne(arrivee);
        final boolean notSameColonne = !depart.sameColonne(arrivee);
        final boolean notSameDiagonal = !depart.sameDiagonal(arrivee);

        if (sameArriveDepart || arriveNotMine || notSameLigne || notSameColonne || notSameDiagonal) {
            return false;
        }

        final Collection<Coordonnee> path = depart.pathTo(arrivee);
        final boolean notAllEmpty = !path.stream().allMatch((coordonnee -> lesCases.get(coordonnee).isEmpty()));

        return !notAllEmpty;
    }

    private boolean coupBonPion(final Action action) {
        final Coordonnee depart = action.getDepart();
        final Coordonnee arrivee = action.getArrivee();

        final Case caseArrivee = lesCases.get(arrivee);

        final boolean notArriveVide = !caseArrivee.isEmpty();
        final boolean coupNonAdjacent = depart.distanceTo(arrivee) != 1;


        return !notArriveVide && !coupNonAdjacent;
    }

    public boolean isGameFinished(final Joueur j1, final Joueur j2) {
        return hasJ1Won(j1) || hasJ2Won(j2);
    }

    private boolean hasJ1Won(final Joueur j1) {
        final List<Coordonnee> ligneBas = getLigneCoordonnee(Plateau.SIZE - 1, Plateau.SIZE);
        return ligneBas.stream().anyMatch((c) -> lesCases.get(c).hasBall() && lesCases.get(c).getBalle().getJoueur().equals(j1));
    }

    private boolean hasJ2Won(final Joueur j2) {
        final List<Coordonnee> ligneHaut = getLigneCoordonnee(0, Plateau.SIZE); //j1 est en haut
        return ligneHaut.stream().anyMatch((c) -> lesCases.get(c).hasBall() && lesCases.get(c).getBalle().getJoueur().equals(j2));
    }

    /**
     * précondition : hasJ1Won(j1) || hasJ2Won(j2)
     *
     * @param joueur1
     * @param joueur2
     * @return le joueur gagnant
     */
    public Joueur getWinner(final Joueur joueur1, final Joueur joueur2) {
        return hasJ1Won(joueur1) ? joueur1 : joueur2;
    }
}
