package diaballik.model.game;

import com.fasterxml.jackson.annotation.*;
import diaballik.model.commande.Action;
import diaballik.model.commande.Commande;
import diaballik.model.joueur.Joueur;
import diaballik.model.memento.MementoGameManager;
import diaballik.model.plateau.Plateau;
import diaballik.model.states.AutomateGameManager;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

// We want to add a JSON attribute to know the type of the object.
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")

// We add a unique identifier to the Json object
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameManager {

    @JsonManagedReference
    private AutomateGameManager automate;

    private Plateau plateau;

    private Collection<Commande> commandes;

    @JsonManagedReference
    private Joueur joueur1;

    @JsonManagedReference
    private Joueur joueur2;
    public GameManager() {

    }
    @JsonCreator
    public GameManager(@JsonProperty("automate") final AutomateGameManager automate, @JsonProperty("plateau") final Plateau plateau, @JsonProperty("commandes") final Collection<Commande> commandes, @JsonProperty("joueur1") final Joueur joueur1, @JsonProperty("joueur2") final Joueur joueur2) {
        this.automate = automate;
        this.plateau = plateau;
        this.commandes = commandes;
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
    }

    public List<Action> eventJouer() {
        return null;
    }

    public boolean coupBon(final Joueur joueur, final Action action) {
        return false;
    }

    public void ajouterAction(final Action action) {

    }

    public void performLastAction() {

    }

    public List<Action> getLastActions(final int nb) {
        return null;
    }

    public boolean gameFinished() {
        return false;
    }

    public Joueur getJoueurGagnant() {
        return null;
    }

    public MementoGameManager createMemento() {
        return null;
    }

    public void restoreMemento(final MementoGameManager memento) {

    }

    public Joueur getJoueurCourant() {
        return null;
    }

    public AutomateGameManager getAutomate() {
        return automate;
    }

    public Joueur getJoueur1() {
        return joueur1;
    }

    public Joueur getJoueur2() {
        return joueur2;
    }

    public void setJoueur1(final Joueur j) {
        this.joueur1 = j;
    }

    public void setJoueur2(final Joueur j) {
        this.joueur2 = j;
    }

    public void setPlateau(final Plateau p) {
        this.plateau = p;
    }

    public void setAutomate(final AutomateGameManager a) {
        this.automate = a;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final GameManager that = (GameManager) o;
        return Objects.equals(automate, that.automate) &&
                Objects.equals(plateau, that.plateau) &&
                Objects.equals(commandes, that.commandes) &&
                Objects.equals(joueur1, that.joueur1) &&
                Objects.equals(joueur2, that.joueur2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(automate, plateau, commandes, joueur1, joueur2);
    }
}
