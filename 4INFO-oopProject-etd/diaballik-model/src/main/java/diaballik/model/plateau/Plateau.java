package diaballik.model.plateau;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import diaballik.model.Coordonnee;
import diaballik.model.FabriquePoidsMoucheCoordonnees;
import diaballik.model.deserializer.MapCoordonneeDeserializer;
import diaballik.model.joueur.Joueur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
     * initialise le plateau selon la stratégie
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

    public void move(final Coordonnee c1, final Coordonnee c2) {
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
}
