package diaballik.model.states;

import diaballik.model.game.GameManager;
import diaballik.model.joueur.Joueur;

public class AutomateGameManager {

    private EtatTour etatCourant;

    private GameManager gm;

    public AutomateGameManager(final GameManager gm, final EtatTour etatInitial) {
        this.gm = gm;
        this.etatCourant = etatInitial;
    }

    public void setEtatCourant(final EtatTour newEtatCourrant) {

    }

    public int eventJouer() {
        return 0;
    }

    public Joueur getJoueurCourant() {
        return null;
    }

    public GameManager getGameManager() {
        return null;
    }

}
