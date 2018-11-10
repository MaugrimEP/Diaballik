package diaballik.model.joueur;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import diaballik.model.commande.Action;
import diaballik.model.game.GameManager;

import java.awt.Color;


public class IA extends Joueur {
    @JsonIgnore
    private StrategieIA strategieIA;

    @JsonCreator
    public IA(@JsonProperty("couleur") final Color couleur, @JsonProperty("pseudo") final String pseudo, @JsonProperty("gm") final GameManager gm) {
        super(couleur, pseudo, gm);
    }

    public Action reflechir() {
        return null;
    }

}
