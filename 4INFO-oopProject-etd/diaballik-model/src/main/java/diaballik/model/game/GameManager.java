package diaballik.model.game;

import diaballik.model.commande.Action;
import diaballik.model.commande.Commande;
import diaballik.model.joueur.Joueur;
import diaballik.model.memento.MementoGameManager;
import diaballik.model.plateau.Plateau;
import diaballik.model.states.AutomateGameManager;

import java.util.Collection;
import java.util.List;

public class GameManager {

	private AutomateGameManager automate;

	private Plateau plateau;

	private Collection<Commande> commandes;

	private Joueur joueur1;

	private Joueur joueur2;

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

	public Joueur getJoueur1() {
		return null;
	}

	public Joueur getJoueur2() {
		return null;
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
}
