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
    public Joueur getJoueurCourant(final AutomateGameManager automate) {
        return null;
    }

    @Override
    public EtatTour getEtatSuivant() {
        return null;
    }
}
