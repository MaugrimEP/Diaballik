package diaballik.model.plateau;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import diaballik.model.joueur.Joueur;
import diaballik.model.plateau.piece.Balle;
import diaballik.model.plateau.piece.Pion;

import java.util.Objects;

// We want to add a JSON attribute to know the type of the object.
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
// We add a unique identifier to the Json object
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Case {
    private Pion pion;

    private Balle balle;

    public Case() {

    }

    @JsonCreator
    private Case(@JsonProperty("pion") final Pion pion, @JsonProperty("balle") final Balle balle) {
        this.pion = pion;
        this.balle = balle;
    }

    public Pion getPion() {
        return pion;
    }

    public void setPion(final Pion pion) {
        this.pion = pion;
    }

    public void setBalle(final Balle balle) {
        this.balle = balle;
    }

    public boolean isEmpty() {
        return pion == null;
    }

    public boolean hasBall() {
        return balle != null;
    }

    /**
     * @param j
     * @return
     */
    public boolean isTo(final Joueur j) {
        return !isEmpty() && pion.getJoueur().equals(j);
    }

    public Balle takeBall() {
        final Balle b = balle;
        balle = null;
        return b;
    }

    public Pion takePion() {
        final Pion p = pion;
        pion = null;
        return p;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Case aCase = (Case) o;
        return Objects.equals(pion, aCase.pion) &&
                Objects.equals(balle, aCase.balle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pion, balle);
    }

    @Override
    public String toString() {
        final String color = isEmpty() ? "\u001B[30m" : isTo(getPion().getJoueur().getGameManager().getJoueur1()) ? Joueur.getColorConsoleJ1() : Joueur.getColorConsoleJ2();
        if (hasBall()) {
            return color + "| B " + "\u001B[0m";
        } else if (!isEmpty()) {
            return color + "| P " + "\u001B[0m";
        }
        return color + "|   " + "\u001B[0m";
    }

    public Balle getBalle() {
        return balle;
    }

    public boolean asPionOnly() {
        return !isEmpty() && !hasBall();
    }
}
