package diaballik.model.states;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import diaballik.model.game.GameManager;
import diaballik.model.joueur.Joueur;

import javax.validation.constraints.NotNull;
import java.util.Objects;

// We want to add a JSON attribute to know the type of the object.
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")

// We add a unique identifier to the Json object
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AutomateGameManager {

    private EtatTour etatCourant;

    @JsonBackReference
    private GameManager gameManager;

    @JsonCreator
    public AutomateGameManager(@JsonProperty("gameManager") final GameManager gm, @JsonProperty("etatCourant") final EtatTour etatInitial) {
        this.gameManager = gm;
        this.etatCourant = etatInitial;
    }

    public AutomateGameManager(final EtatTour etatInitial) {
        this(null, etatInitial);
    }

    public void setEtatCourant(final EtatTour newEtatCourrant) {
        this.etatCourant = newEtatCourrant;
    }


    public int eventJouer() {
        return etatCourant.jouer(this);
    }

    @JsonIgnore
    public Joueur getJoueurCourant() {
        return etatCourant.getJoueurCourant(this);
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public void setGameManager(@NotNull final GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AutomateGameManager that = (AutomateGameManager) o;
        return Objects.equals(etatCourant, that.etatCourant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(etatCourant);
    }
}
