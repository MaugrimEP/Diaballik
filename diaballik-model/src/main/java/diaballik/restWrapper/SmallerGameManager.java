package diaballik.restWrapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import diaballik.model.joueur.Joueur;

import java.util.Date;
import java.util.Objects;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class SmallerGameManager {
    final Joueur joueur1;
    final Joueur joueur2;
    final Date date;

    @JsonCreator
    public SmallerGameManager(@JsonProperty("joueur1") final Joueur joueur1, @JsonProperty("joueur2") final Joueur joueur2, @JsonProperty("date") final Date date) {
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        this.date = new Date(date.getTime());
    }

    public Joueur getJoueur1() {
        return joueur1;
    }

    public Joueur getJoueur2() {
        return joueur2;
    }

    public Date getDate() {
        return new Date(date.getTime());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SmallerGameManager that = (SmallerGameManager) o;
        return Objects.equals(joueur1, that.joueur1) &&
                Objects.equals(joueur2, that.joueur2) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(joueur1, joueur2, date);
    }
}
