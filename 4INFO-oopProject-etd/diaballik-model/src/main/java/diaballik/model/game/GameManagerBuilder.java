package diaballik.model.game;

import diaballik.model.joueur.Joueur;
import diaballik.model.plateau.Plateau;
import diaballik.model.states.AutomateGameManager;

public class GameManagerBuilder {

	GameManager gm;

	public GameManagerBuilder() {
		gm = new GameManager();
	}

	public GameManagerBuilder joueur1(final Joueur j) {
		gm.setJoueur1(j);
		return this;
	}

	public GameManagerBuilder joueur2(final Joueur j) {
		gm.setJoueur2(j);
		return this;
	}

	public GameManagerBuilder plateau(final Plateau plateau) {
		gm.setPlateau(plateau);
		return this;
	}

	public GameManagerBuilder typePartie(final TypePartie type) {
		gm.setAutomate(new AutomateGameManager(gm, type.getEtatInitial()));
		return this;
	}

	public GameManager build() {
        gm.getJoueur1().setGameManager(gm);
        gm.getJoueur2().setGameManager(gm);
        gm.getAutomate().setGameManager(gm);
		return gm;
	}
}
