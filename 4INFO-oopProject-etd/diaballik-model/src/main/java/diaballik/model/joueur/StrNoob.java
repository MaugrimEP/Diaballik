package diaballik.model.joueur;

import com.fasterxml.jackson.annotation.JsonCreator;
import diaballik.model.commande.Action;

import java.util.Collection;

public class StrNoob extends StrategieIA {

    @JsonCreator
    public StrNoob() {

    }

    /**
     * @see StrategieIA
     */
    @Override
    public Action reflechir(final Joueur joueur) {
        final Collection<Action> candidatesCasesToMove = this.getCandidateActions(joueur);
        return candidatesCasesToMove.stream()
                .skip((int) (candidatesCasesToMove.size() * Math.random()))
                .findFirst().get();
    }

}
