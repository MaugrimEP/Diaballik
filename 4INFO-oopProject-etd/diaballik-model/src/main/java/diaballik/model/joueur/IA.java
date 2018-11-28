package diaballik.model.joueur;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import diaballik.model.commande.Action;
import diaballik.model.game.GameManager;

import java.awt.Color;


public class IA extends Joueur {
    private StrategieIA strategieIA;

    @JsonCreator
    public IA(@JsonProperty("couleur") final Color couleur, @JsonProperty("pseudo") final String pseudo, @JsonProperty("gm") final GameManager gm, @JsonProperty("strategieIA") final StrategieIA strategieIA) {
        super(couleur, pseudo, gm);
        this.strategieIA = strategieIA;
    }

    @Override
    public void jouer() {
        final Action a = reflechir();
        getGameManager().ajouterAction(a);
        getGameManager().performLastAction();
    }

    public Action reflechir() {
        return strategieIA.reflechir(this);
    }

}
