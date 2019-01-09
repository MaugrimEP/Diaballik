package diaballik.model.joueur;

import com.fasterxml.jackson.annotation.JsonCreator;
import diaballik.model.commande.Action;
import diaballik.model.plateau.Plateau;

import java.util.Collection;
import java.util.Comparator;

public class StrStarting extends StrategieIA {

    @JsonCreator
    public StrStarting() {
    }

    /**
     * @param plateau
     * @return la valeur du plateau suivant l'heuristique suivante:
     * - plus notre balle est proche du bord opposé mieux c'est
     * - distance de nos pions par rapport au bord adverse
     * - nb pions dans les diagonales et lignes de la balle adverse
     */
    private int simpleHeuristique(final Plateau plateau, final Joueur joueur) {
        return -plateau.distanceBalleToBordAdverse(joueur) * 1000 + plateau.distancePionsBordOppose(joueur) * -100 + plateau.quantiteDInterceptionBalle(joueur);
    }

    private int evaluateAction(final Action a, final Joueur joueur) {
        a.doAction();
        final int plateauValue = simpleHeuristique(a.getPlateau(), joueur);
        a.undoAction();

        return plateauValue;
    }


    /**
     * @see StrategieIA
     */
    @Override
    public Action reflechir(final Joueur joueur) {
        final Collection<Action> candidateActions = getCandidateActions(joueur);
        final Action action = candidateActions.stream().max(Comparator.comparingInt(a -> evaluateAction(a, joueur))).get();
        return action;
    }

}
