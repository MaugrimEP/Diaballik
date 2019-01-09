package diaballik.model.joueur;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import diaballik.model.commande.Action;

public class StrProgressive extends StrategieIA {

    int nbTourJoue;

    private StrategieIA currentStrategie;

    private static int NB_TOUR_AVANT_CHANGEMENT = 5;

    @JsonCreator
    public StrProgressive() {
        currentStrategie = new StrNoob();
    }

    @JsonCreator
    public StrProgressive(@JsonProperty("nbTourJoue") final int nbTourJoue, @JsonProperty("currentStrategie") final StrategieIA currentStrategie) {
        this.nbTourJoue = nbTourJoue;
        if (currentStrategie != null) {
            this.currentStrategie = currentStrategie;
        } else {
            this.currentStrategie = new StrNoob();
        }
    }


    @Override
    public Action reflechir(final Joueur joueur) {
        nbTourJoue++;
        final Action reflechie = currentStrategie.reflechir(joueur);
        if (nbTourJoue == NB_TOUR_AVANT_CHANGEMENT * 3) {
            currentStrategie = new StrStarting();
        }
        return reflechie;
    }

    public StrategieIA getCurrentStrategie() {
        return currentStrategie;
    }
}
