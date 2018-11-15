package diaballik.model.coordonnee;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.stream.Stream;

// We want to add a JSON attribute to know the type of the object.
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
// We add a unique identifier to the Json object
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Coordonnee {

    public final int ligne;

    public final int colonne;

    @JsonCreator
    protected Coordonnee(@JsonProperty("ligne") final int ligne, @JsonProperty("colonne") final int colonne) {
        this.ligne = ligne;
        this.colonne = colonne;
    }

    public double distanceTo(final Coordonnee other) {
        final int distance = Math.abs(this.ligne - other.ligne) + Math.abs(this.colonne - other.colonne);
        return distance;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Coordonnee that = (Coordonnee) o;
        return ligne == that.ligne &&
                colonne == that.colonne;
    }

    public boolean sameDiagonal(final Coordonnee other) {
        return Math.abs(this.colonne - other.colonne) == Math.abs(this.ligne - other.ligne);
    }

    public boolean sameLigne(final Coordonnee other) {
        return this.ligne == other.ligne;
    }

    public boolean sameColonne(final Coordonnee other) {
        return this.colonne == other.colonne;
    }

    /**
     * précondition : même ligne meme colonne ou meme diagonale
     *
     * @param other
     * @return les elements entre les deux coordonnées (this et other)
     */
    public Collection<Coordonnee> pathTo(final Coordonnee other) {
        final List<Coordonnee> path = new ArrayList<>();


        final Function<Integer, Coordonnee> coordonnerRelation;
        final int minIterate;
        final int maxIterate;


        if (this.colonne == other.colonne) {
            minIterate = Math.min(this.ligne, other.ligne);
            maxIterate = Math.max(this.ligne, other.ligne);

            coordonnerRelation = (y) -> FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(y, this.colonne);

        } else {
            final int coeff = (this.ligne - other.ligne) / (this.colonne - other.colonne);
            final int ordoAOrigin = this.ligne - coeff * this.colonne;

            final IntUnaryOperator pathFinder = (x) -> coeff * x + ordoAOrigin;

            minIterate = Math.min(this.colonne, other.colonne);
            maxIterate = Math.max(this.colonne, other.colonne);

            coordonnerRelation = (x) -> FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(pathFinder.applyAsInt(x), x);
        }

        Stream.iterate(minIterate + 1, (x) -> x + 1)
                .limit(maxIterate - minIterate - 1)
                .forEach((x) -> path.add(coordonnerRelation.apply(x)));

        return path;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ligne, colonne);
    }

    @Override
    public String toString() {
        return ligne + ":" + colonne;
    }
}
