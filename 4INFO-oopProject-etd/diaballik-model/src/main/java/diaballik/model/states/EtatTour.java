package diaballik.model.states;

import diaballik.model.joueur.Joueur;

public abstract class EtatTour {

    private int nbCoup;

    private AutomateGameManager automateGameManager;

    public int jouer(final AutomateGameManager automate) {
        return 0;
    }

    public abstract Joueur getJoueurCourant(AutomateGameManager automate);

    public abstract EtatTour getEtatSuivant();

    public boolean tourFini() {
        return false;
    }

}
