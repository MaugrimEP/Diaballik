package diaballik.model.states;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import diaballik.model.joueur.Joueur;

@JsonIgnoreProperties(ignoreUnknown = true)

public class EtatJ1vsIA extends EtatTour {
    @JsonCreator
    protected EtatJ1vsIA(@JsonProperty("nbCoup") final int nbCoup) {
        super(nbCoup);
    }

    public EtatJ1vsIA() {
    }

    @Override
    public int jouer(final AutomateGameManager automate) {
        int nbCoupTotal = super.jouer(automate);
        if (tourFini()) {
            nbCoupTotal += automate.eventJouer();
        }
        return nbCoupTotal;
    }

    @Override
    public Joueur getJoueurCourant(final AutomateGameManager automate) {
        return automate.getGameManager().getJoueur1();
    }

    @Override
    public EtatTour getEtatSuivant() {
        return new EtatIAvsJ1();
    }
}
