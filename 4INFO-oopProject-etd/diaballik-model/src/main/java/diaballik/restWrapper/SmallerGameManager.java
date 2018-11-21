package diaballik.restWrapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import diaballik.model.joueur.Joueur;

import java.util.Date;

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
        this.date = date;
    }

    public Joueur getJoueur1() {
        return joueur1;
    }

    public Joueur getJoueur2() {
        return joueur2;
    }


    public Date getDate() {
        return date;
    }
}
