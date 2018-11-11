package diaballik.model.plateau;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import diaballik.model.coordonnee.Coordonnee;
import diaballik.model.game.GameManager;
import diaballik.model.joueur.JoueurHumain;

import java.awt.Color;
import java.util.Map;

public class PlateauEnemyAmongUs extends Plateau {
    @JsonCreator
    protected PlateauEnemyAmongUs(@JsonProperty("lescases") final Map<Coordonnee, Case> lescases) {
        super(lescases);
        final Case aCase = new Case();
        aCase.setBalle(new Balle(new JoueurHumain(Color.RED, "red", new GameManager())));
        lescases.put(new Coordonnee(1, 2), aCase);
    }
    public PlateauEnemyAmongUs() {
    }

}
