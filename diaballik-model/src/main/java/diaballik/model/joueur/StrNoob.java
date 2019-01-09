package diaballik.model.joueur;

import com.fasterxml.jackson.annotation.JsonCreator;
import diaballik.model.commande.Action;

import java.util.Collection;
import java.util.Random;

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
                .skip(new Random().nextInt(candidatesCasesToMove.size()))
                .findFirst().get();
    }

}
