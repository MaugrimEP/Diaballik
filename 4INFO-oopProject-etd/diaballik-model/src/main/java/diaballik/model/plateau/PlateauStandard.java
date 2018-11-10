package diaballik.model.plateau;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import diaballik.model.coordonnee.Coordonnee;

import java.util.Map;

public class PlateauStandard extends Plateau {
    @JsonCreator
    protected PlateauStandard(@JsonProperty("lescases") final Map<Coordonnee, Case> lescases) {
        super(lescases);
    }
    public PlateauStandard() {
    }
}
