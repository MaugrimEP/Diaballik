package diaballik.model.joueur;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import diaballik.model.commande.Action;
import diaballik.model.coordonnee.Coordonnee;
import diaballik.model.plateau.Case;
import diaballik.model.plateau.Plateau;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
// We have to identify the sub-classes to help the marshalling
@JsonSubTypes({
        @JsonSubTypes.Type(value = StrStarting.class),
        @JsonSubTypes.Type(value = StrProgressive.class),
        @JsonSubTypes.Type(value = StrNoob.class),
})
// We add a unique identifier to the Json object
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public abstract class StrategieIA {
    /**
     * precondition : il y a forcement un coup a jouer
     *
     * @return une action réfléchie de manière
     */
    public abstract Action reflechir(final Joueur joueur);

    public Collection<Action> getCandidateActions(final Joueur joueur) {
        final Plateau plateau = joueur.getGameManager().getPlateau();

        final Map<Coordonnee, Case> casesByJoueur = plateau.getCasesByJoueur(joueur); //toutes les cases où on a des pions ou balles

        final Collection<Action> candidatesActions = new HashSet<>();

        casesByJoueur.entrySet().forEach(coordonneeCaseEntry -> {
            final Case laCase = coordonneeCaseEntry.getValue();
            final Coordonnee coordonnePiece = coordonneeCaseEntry.getKey();
            if (laCase.hasBall()) {
                candidatesActions.addAll(plateau.candidatesCasesToMoveBall(coordonnePiece).stream()
                        .map(arrive -> new Action(arrive, coordonnePiece, plateau)).collect(Collectors.toList())); //Récuperer l'ensemble des déplacement possible pour tout les pions
            } else {
                candidatesActions.addAll(plateau.candidatesCasesToMovePion(coordonnePiece).stream()
                        .map(arrive -> new Action(arrive, coordonnePiece, plateau)).collect(Collectors.toList())); //Récuperer l'ensemble des déplacement possible pour la balle
            }
        });

        return candidatesActions;
    }
}
