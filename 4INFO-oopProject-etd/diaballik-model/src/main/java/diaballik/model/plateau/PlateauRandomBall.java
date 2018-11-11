package diaballik.model.plateau;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import diaballik.model.Coordonnee;

import java.util.Map;

public class PlateauRandomBall extends Plateau {
    @JsonCreator
    protected PlateauRandomBall(@JsonProperty("lescases") final Map<Coordonnee, Case> lescases) {
        super(lescases);
    }
    public PlateauRandomBall() {
    }
}
