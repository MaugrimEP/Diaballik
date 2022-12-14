package diaballik.model.commande;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import diaballik.model.coordonnee.Coordonnee;
import diaballik.model.plateau.Plateau;

import java.util.Objects;

// We want to add a JSON attribute to know the type of the object.
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
// We have to identify the sub-classes to help the marshalling

// We add a unique identifier to the Json object
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Action {

    private Coordonnee arrivee;

    private Coordonnee depart;

    private Plateau plateau;

    @JsonCreator
    public Action(@JsonProperty("depart") final Coordonnee depart, @JsonProperty("arrivee") final Coordonnee arrivee, @JsonProperty("plateau") final Plateau plateau) {
        this(depart, arrivee);
        this.plateau = plateau;
    }

    public Action(@JsonProperty("depart") final Coordonnee depart, @JsonProperty("arrivee") final Coordonnee arrivee) {
        this.arrivee = arrivee;
        this.depart = depart;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public Coordonnee getArrivee() {
        return arrivee;
    }

    public Coordonnee getDepart() {
        return depart;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Action action = (Action) o;
        return Objects.equals(arrivee, action.arrivee) &&
                Objects.equals(depart, action.depart) &&
                Objects.equals(plateau, action.plateau);
    }

    @Override
    public int hashCode() {
        return Objects.hash(arrivee, depart, plateau);
    }

    public void setPlateau(final Plateau plateau) {
        this.plateau = plateau;
    }

    @Override
    public String toString() {
        return "Action{" +
                "arrivee=" + arrivee +
                ", depart=" + depart +
                '}';
    }

    /**
     * pr??condition : l'action est correcte
     */
    public void doAction() {
        this.plateau.move(depart, arrivee);
    }

    public void undoAction() {
        this.getPlateau().move(arrivee, depart);
    }
}
