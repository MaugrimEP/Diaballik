package diaballik.model.states;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import diaballik.model.joueur.Joueur;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EtatIAvsJ1 extends EtatTour {


    @JsonCreator
    protected EtatIAvsJ1(@JsonProperty("nbCoup") final int nbCoup) {
        super(nbCoup);
    }

    public EtatIAvsJ1() {
    }

    @Override
    public int jouer(final AutomateGameManager automate) {
        int nbCoupTotal = super.jouer(automate);
        if (!tourFini()) {
            nbCoupTotal += automate.eventJouer();
        }
        return nbCoupTotal;
    }

    @Override
    public Joueur getJoueurCourant(final AutomateGameManager automate) {
        return automate.getGameManager().getJoueur2();
    }

    @Override
    public EtatTour getEtatSuivant() {
        return new EtatJ1vsIA();
    }

}
