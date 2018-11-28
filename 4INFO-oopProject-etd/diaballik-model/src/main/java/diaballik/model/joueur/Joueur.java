package diaballik.model.joueur;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import diaballik.model.game.GameManager;

import javax.validation.constraints.NotNull;
import java.awt.Color;
import java.util.Objects;


// We want to add a JSON attribute to know the type of the object.
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
// We have to identify the sub-classes to help the marshalling
@JsonSubTypes({
        @JsonSubTypes.Type(value = JoueurHumain.class),
        @JsonSubTypes.Type(value = IA.class)
})
// We add a unique identifier to the Json object
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public abstract class Joueur {
    private Color couleur;

    private String pseudo;

    @JsonBackReference
    private GameManager gameManager;

    @JsonCreator
    public Joueur(@JsonProperty("couleur") final Color couleur, @JsonProperty("pseudo") final String pseudo, @JsonProperty("gameManager") final GameManager gm) {
        this.couleur = couleur;
        this.pseudo = pseudo;
        this.gameManager = gm;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    @JsonCreator
    public Joueur(@JsonProperty("couleur") final Color couleur, @JsonProperty("pseudo") final String pseudo) {
        this(couleur, pseudo, null);
    }

    public void setGameManager(@NotNull final GameManager gm) {
        this.gameManager = gm;
    }

    public static boolean checkPlayerConsistency(final Joueur j1, final Joueur j2) {
        return (!j1.couleur.equals(j2.couleur)) && (!j1.pseudo.equals(j2.pseudo));
    }

    public void jouer() {
        gameManager.performLastAction();
    }

    public Color getCouleur() {
        return couleur;
    }

    public String getPseudo() {
        return pseudo;
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Joueur joueur = (Joueur) o;
        return Objects.equals(couleur, joueur.couleur) &&
                Objects.equals(pseudo, joueur.pseudo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(couleur, pseudo);
    }

    public static String getColorConsoleJ1() {
        return "\u001B[31m";
    }

    public static String getColorConsoleJ2() {
        return "\u001B[32m";
    }
}
