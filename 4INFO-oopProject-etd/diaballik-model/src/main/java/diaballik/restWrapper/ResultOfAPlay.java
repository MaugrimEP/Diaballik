package diaballik.restWrapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import diaballik.model.commande.Action;
import diaballik.model.joueur.Joueur;

import java.util.List;

// We want to add a JSON attribute to know the type of the object.
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")

// We add a unique identifier to the Json object
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class ResultOfAPlay {
    private List<Action> actions;
    private boolean gameWon;
    private Joueur winner;

    @JsonCreator
    public ResultOfAPlay(@JsonProperty("actions") final List<Action> actions, @JsonProperty("gameWon") final boolean gameWon, @JsonProperty("winner") final Joueur winner) {
        this.actions = actions;
        this.gameWon = gameWon;
        this.winner = winner;
    }

    public List<Action> getActions() {
        return actions;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public Joueur getWinner() {
        return winner;
    }
}
