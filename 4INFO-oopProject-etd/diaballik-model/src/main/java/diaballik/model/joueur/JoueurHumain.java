package diaballik.model.joueur;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import diaballik.model.game.GameManager;

import java.awt.Color;

public class JoueurHumain extends Joueur {
    @JsonCreator
    public JoueurHumain(@JsonProperty("couleur") final Color couleur, @JsonProperty("pseudo") final String pseudo, @JsonProperty("gm") final GameManager gm) {
        super(couleur, pseudo, gm);
    }

    public JoueurHumain(final Color couleur, final String pseudo) {
        this(couleur, pseudo, null);
    }
}
