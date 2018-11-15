package diaballik.model.plateau;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import diaballik.model.coordonnee.Coordonnee;
import diaballik.model.joueur.Joueur;

import java.util.Map;

public class PlateauStandard extends Plateau {
    @JsonCreator
    protected PlateauStandard(@JsonProperty("lescases") final Map<Coordonnee, Case> lescases) {
        super(lescases);
    }

    public PlateauStandard() {
    }

    @Override
    public void init(final Joueur j1, final Joueur j2) {
        super.init(j1, j2);
    }


}
