package diaballik.model.states;

import diaballik.model.joueur.Joueur;

public class EtatIAvsJ1 extends EtatTour {

    @Override
    public Joueur getJoueurCourant(final AutomateGameManager automate) {
        return null;
    }

    @Override
    public EtatTour getEtatSuivant() {
        return null;
    }
}
