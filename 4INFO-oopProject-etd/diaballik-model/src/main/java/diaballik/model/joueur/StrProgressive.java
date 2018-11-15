package diaballik.model.joueur;

import com.fasterxml.jackson.annotation.JsonCreator;
import diaballik.model.commande.Action;

//TODO add jsopn
public class StrProgressive implements StrategieIA {

    int nbTourJoue;

    private StrategieIA currentStrategie;

    private static int NB_TOUR_AVANT_CHANGEMENT = 5;

    @JsonCreator
    public StrProgressive() {
        currentStrategie = new StrNoob();
    }

    public Action reflechir() {
        nbTourJoue++;
        final Action reflechie = currentStrategie.reflechir();
        if (nbTourJoue > NB_TOUR_AVANT_CHANGEMENT * 3) {
            currentStrategie = new StrStarting();
        }
        return reflechie;
    }

}
