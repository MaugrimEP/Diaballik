package diaballik.model.states;

import diaballik.model.joueur.Joueur;

public class EtatJ1vsJ2 extends EtatTour {

    @Override
    public Joueur getJoueurCourant(final AutomateGameManager automate) {
        return null;
    }

    @Override
    public EtatTour getEtatSuivant() {
        return null;
    }
}
