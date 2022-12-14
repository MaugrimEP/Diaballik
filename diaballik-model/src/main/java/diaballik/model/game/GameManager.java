package diaballik.model.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import diaballik.model.commande.Action;
import diaballik.model.commande.Commande;
import diaballik.model.joueur.Joueur;
import diaballik.model.memento.MementoGameManager;
import diaballik.model.plateau.Plateau;
import diaballik.model.states.AutomateGameManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

// We want to add a JSON attribute to know the type of the object.
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")

// We add a unique identifier to the Json object
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"joueur1", "joueur2", "automate", "plateau", "commandes"})
public class GameManager {

    //@JsonManagedReference(value = "gameManager->joueur")
    private Joueur joueur1;
    //@JsonManagedReference(value = "gameManager->joueur")
    private Joueur joueur2;
    //@JsonManagedReference(value = "gameManager->automateGameManager")
    private AutomateGameManager automate;

    private Plateau plateau;

    private List<Commande> commandes;


    public GameManager() {
        commandes = new ArrayList<>();
    }

    @JsonCreator
    public GameManager(@JsonProperty("automate") final AutomateGameManager automate, @JsonProperty("plateau") final Plateau plateau, @JsonProperty("commandes") final List<Commande> commandes, @JsonProperty("joueur1") final Joueur joueur1, @JsonProperty("joueur2") final Joueur joueur2) {
        this.automate = automate;
        this.plateau = plateau;
        this.commandes = commandes;
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
    }

    public Joueur getJoueur1() {
        return joueur1;
    }

    public Joueur getJoueur2() {
        return joueur2;
    }


    /**
     * @return la liste des commandes jou??es dans ce coup
     */
    public List<Commande> eventJouer() {
        final int nbCoupsJoue = automate.eventJouer();
        return getLastActions(nbCoupsJoue);
    }

    public boolean coupBon(final Joueur joueur, final Action action) {
        return plateau.coupBon(joueur, action);
    }

    public void ajouterAction(final Action action) {
        action.setPlateau(plateau);
        commandes.add(new Commande(action));
    }

    public void performLastAction() {
        commandes.get(commandes.size() - 1).doAction();
    }

    /**
     * Pr??condition : il y a au moins nb ??l??ments dans la liste
     *
     * @param nb
     * @return la liste des nb derni??res actions
     */
    public List<Commande> getLastActions(final int nb) {
        return commandes.subList(commandes.size() - nb, commandes.size());
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public boolean gameFinished() {
        return plateau.isGameFinished(joueur1, joueur2);
    }

    @JsonIgnore
    public Joueur getJoueurGagnant() {
        return plateau.getWinner(joueur1, joueur2);
    }

    public MementoGameManager createMemento() {
        final MementoGameManager mgm = new MementoGameManager(new Date(), this);
        mgm.saveFile();
        return mgm;
    }

    @JsonIgnore
    public Joueur getJoueurCourant() {
        return automate.getJoueurCourant();
    }

    public AutomateGameManager getAutomate() {
        return automate;
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
