package diaballik.model.plateau;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import diaballik.model.Coordonnee;
import diaballik.model.deserializer.MapCoordonneeDeserializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
public class Plateau {
    public static int SIZE = 7;

    @JsonDeserialize(keyUsing = MapCoordonneeDeserializer.class)
    protected Map<Coordonnee, Case> lescases;

    @JsonCreator
    protected Plateau(@JsonProperty("lescases") final Map<Coordonnee, Case> lescases) {
        this.lescases = lescases;
    }

    public Plateau() {
        lescases = new HashMap<>();
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
        return Objects.equals(lescases, plateau.lescases);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lescases);
    }
}
