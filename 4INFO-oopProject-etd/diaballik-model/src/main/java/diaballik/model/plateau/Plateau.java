package diaballik.model.plateau;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import diaballik.model.commande.Action;
import diaballik.model.coordonnee.Coordonnee;
import diaballik.model.coordonnee.FabriquePoidsMoucheCoordonnees;
import diaballik.model.exceptions.InvalidCoordinateException;
import diaballik.model.joueur.Joueur;
import diaballik.model.plateau.piece.Balle;
import diaballik.model.plateau.piece.Pion;
import diaballik.serialization.MapCoordonneeDeserializer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
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

    /**
     * @param joueur
     * @return l'ensemble des cases qui ont des éléments appartenant au joueur "joueur"
     */
    public Map<Coordonnee, Case> getCasesByJoueur(final Joueur joueur) {
        return lesCases.entrySet().stream()
                .filter(coordonneeCaseEntry -> coordonneeCaseEntry.getValue().isTo(joueur))
                .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
    }

    /**
     * précondition : cases à la coordonnée c !isEmpty() &&  !hasBalle()
     *
     * @param depart
     * @return l'ensemble des Coordonnées qui peuvent être candidatates au mouvement du pion sur la cases de coordonnée c
     */
    public Collection<Coordonnee> candidatesCasesToMovePion(final Coordonnee depart) {
        final Collection<Coordonnee> candidates = new HashSet<>();

        Stream.iterate(-1, ligne -> ligne + 1).limit(3).forEach(ligne -> {
            Stream.iterate(-1, colonne -> colonne + 1).limit(3).forEach(colonne -> {
                try {
                    final Coordonnee arrivee = FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(depart.ligne + ligne, depart.colonne + colonne);
                    final Action action = new Action(depart, arrivee, this);
                    if (coupBon(lesCases.get(depart).getPion().getJoueur(), action)) {
                        candidates.add(arrivee);
                    }
                } catch (final InvalidCoordinateException e) {
                }
            });
        });
        return candidates;
    }

    /**
     * précondition : cases à la coordonnée c &&  !hasBalle()
     *
     * @param depart
     * @return une liste des coordonnées d'arrivées possibles pour la coordonnées de départ
     */
    public Collection<Coordonnee> candidatesCasesToMoveBall(final Coordonnee depart) {
        final Collection<Coordonnee> candidates = new HashSet<>();
        candidates.addAll(depart.getDiagonal(Plateau.SIZE)); //on ajoute les deux diagonales dans les candidates non filtré
        candidates.addAll(getColonneCoordonnee(depart.colonne, SIZE)); //on ajoute la colonne non filtré
        candidates.addAll(getLigneCoordonnee(depart.ligne, SIZE)); //on ajoute ligne non filtré

        final Set<Coordonnee> filtered = candidates.stream().filter(arrivee -> {
            final Action action = new Action(depart, arrivee, this);
            return (coupBon(lesCases.get(depart).getPion().getJoueur(), action));
        }).collect(Collectors.toSet());
        return filtered;
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
     * précondition : indexColonne est une index correct du tableau
     * donne une liste de coordonnee correspondant à une colonne
     *
     * @param indexColonne
     * @param size         - taille de colonne
     * @return liste de coordonnee correspondant à la colonne indexColonne
     */
    public static List<Coordonnee> getColonneCoordonnee(final int indexColonne, final int size) {
        final List<Coordonnee> colonneListe = new ArrayList<>();
        Stream.iterate(0, (x) -> x + 1).
                limit(size).
                forEach((ligne) -> colonneListe.add(FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(ligne, indexColonne)) //on initialise nos tableau de coordonnées
                );
        return colonneListe;
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

        final int milieu = Plateau.SIZE / 2;
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
     * @param depart
     * @param arrivee
     */
    public void move(final Coordonnee depart, final Coordonnee arrivee) {
        final Case caseDepart = lesCases.get(depart);
        final Case caseArrivee = lesCases.get(arrivee);

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

        final boolean caseVideOrNotMine = caseDepart.isEmpty() || !caseDepart.getPion().getJoueur().equals(joueur);

        if (caseVideOrNotMine) {
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

        if (caseArrivee.getPion() == null) {
            return false;
        }
        final boolean arriveNotMine = !caseArrivee.getPion().getJoueur().equals(joueur);

        final boolean notSameLigne = !depart.sameLigne(arrivee);
        final boolean notSameColonne = !depart.sameColonne(arrivee);
        final boolean notSameDiagonal = !depart.sameDiagonal(arrivee);

        if (sameArriveDepart || arriveNotMine || (notSameLigne && notSameColonne && notSameDiagonal)) {
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

    @Override
    public String toString() {
        final StringBuilder tableau = new StringBuilder();
        final String separtor = Stream.iterate(0, x -> x + 1).limit(16).map(r -> "__").collect(Collectors.joining()) + "\n";
        Stream.iterate(0, x -> x + 1).limit(SIZE).forEach(ligne -> {
            final List<Coordonnee> ligneCoordonnee = getLigneCoordonnee(ligne, SIZE);
            tableau.append(separtor);
            tableau.append(ligne);
            tableau.append("  ");
            ligneCoordonnee.stream().forEach(c -> tableau.append(lesCases.get(c).toString()));
            tableau.append("\n");
        });
        tableau.append("     ");
        Stream.iterate(0, x -> x + 1).limit(SIZE).forEach(col -> {
            tableau.append(col);
            tableau.append("   ");
        });
        return tableau.toString();
    }

    private Coordonnee getBalleCoordonnes(final Joueur joueur) {
        return lesCases.entrySet().stream().filter(coordonneeCaseEntry -> coordonneeCaseEntry.getValue().hasBall() && coordonneeCaseEntry.getValue().isTo(joueur)).findFirst().get().getKey();
    }


    private Collection<Coordonnee> getPionsCoordonnes(final Joueur joueur) {
        return lesCases.entrySet().stream().
                filter(coordonneeCaseEntry ->
                        !coordonneeCaseEntry.getValue().isEmpty() &&
                                !coordonneeCaseEntry.getValue().hasBall() &&
                                coordonneeCaseEntry.getValue().isTo(joueur)).
                map(Map.Entry::getKey).
                collect(Collectors.toList());
    }

    public int distanceBalleToBordAdverse(final Joueur joueur) {
        final Coordonnee balleCoordonnes = getBalleCoordonnes(joueur);
        final boolean isJ1 = joueur.getGameManager().getJoueur1().equals(joueur);
        return (int) balleCoordonnes.distanceTo(getLigneCoordonnee(isJ1 ? 0 : Plateau.SIZE - 1, Plateau.SIZE).get(balleCoordonnes.colonne));

    }

    public int distancePionsBordOppose(final Joueur joueur) {
        final boolean isJ1 = joueur.getGameManager().getJoueur1().equals(joueur);
        final int ligneJoueurAdverse = isJ1 ? 0 : Plateau.SIZE - 1;
        final Collection<Coordonnee> pionsCoordonnes = getPionsCoordonnes(joueur);
        return pionsCoordonnes.stream()
                .map(coordonnee ->
                        coordonnee.distanceTo(
                                FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(ligneJoueurAdverse, coordonnee.colonne)))
                .reduce((d1, d2) -> d1 + d2).get().intValue();
    }

    public int quantiteDInterceptionBalle(final Joueur joueur) {
        final Coordonnee balleCoordonnes = getBalleCoordonnes(joueur.getGameManager().getJoueur1().equals(joueur) ? joueur.getGameManager().getJoueur2() : joueur.getGameManager().getJoueur1());
        final Collection<Coordonnee> diagonal = balleCoordonnes.getDiagonal(Plateau.SIZE);
        return diagonal.stream().map(coordonnee -> lesCases.get(coordonnee).asPionOnly() && lesCases.get(coordonnee).isTo(joueur) ? 1 : 0).reduce((integer, integer2) -> integer + integer2).get();
    }
}
