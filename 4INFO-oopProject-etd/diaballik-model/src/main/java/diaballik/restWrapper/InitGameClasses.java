package diaballik.restWrapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import diaballik.model.game.TypePartie;
import diaballik.model.joueur.Joueur;
import diaballik.model.plateau.Plateau;

import java.util.Objects;

// We want to add a JSON attribute to know the type of the object.
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
// We add a unique identifier to the Json object
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class InitGameClasses {
    private final Joueur j1;
    private final Joueur j2;
    private final Plateau plateau;
    private final TypePartie typePartie;

    @JsonCreator
    public InitGameClasses(@JsonProperty("j1") final Joueur j1, @JsonProperty("j2") final Joueur j2, @JsonProperty("plateau") final Plateau plateau, @JsonProperty("typePartie") final TypePartie typePartie) {
        this.j1 = j1;
        this.j2 = j2;
        this.plateau = plateau;
        this.typePartie = typePartie;
    }

    public Joueur getJ1() {
        return j1;
    }

    public Joueur getJ2() {
        return j2;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public TypePartie getTypePartie() {
        return typePartie;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final InitGameClasses that = (InitGameClasses) o;
        return Objects.equals(j1, that.j1) &&
                Objects.equals(j2, that.j2) &&
                Objects.equals(plateau, that.plateau) &&
                typePartie == that.typePartie;
    }

    @Override
    public int hashCode() {
        return Objects.hash(j1, j2, plateau, typePartie);
    }
}
