package diaballik.model.joueur;

import com.fasterxml.jackson.annotation.JsonCreator;
import diaballik.model.commande.Action;

public class StrStarting extends StrategieIA {

    @JsonCreator
    public StrStarting() {
    }

    @Override
    public Action reflechir(final Joueur joueur) {
        return null;
    }

}
