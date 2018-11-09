package diaballik.model.states;

import diaballik.model.joueur.Joueur;

public class EtatJ1vsIA extends EtatTour {

    @Override
    public Joueur getJoueurCourant(final AutomateGameManager automate) {
        return null;
    }

    @Override
    public EtatTour getEtatSuivant() {
        return null;
    }
}
