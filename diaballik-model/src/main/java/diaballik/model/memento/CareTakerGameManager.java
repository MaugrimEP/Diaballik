package diaballik.model.memento;

import diaballik.model.game.GameManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class CareTakerGameManager {

    private GameManager gameManager;

    private Collection<MementoGameManager> mementos;

    public CareTakerGameManager() {
        mementos = new ArrayList<>();
        loadMementosFiles();
    }

    public void saveCurrentGame() {
        final MementoGameManager memento = gameManager.createMemento();
        this.mementos.add(memento);
    }

    public List<MementoGameManager> listMementos() {
        return new ArrayList<>(mementos);
    }

    /**
     * delete the game from mementos and file if it's exist, nothing otherwise.
     *
     * @param gameId
     */
    public void deleteGame(final Date gameId) {
        final Optional<MementoGameManager> toDelete = mementos.stream().filter(mementos -> mementos.getDate().equals(gameId)).findFirst();
        if (toDelete.isPresent()) {
            toDelete.get().deleteFile();
            this.mementos.remove(toDelete.get());
        }
    }

    /**
     * crée la liste de mementos a partir des fichiers
     * écrase la liste si elle n'était pas vide
     */
    public void loadMementosFiles() {
        final File repertoire = new File(MementoGameManager.DIRECTORY);
        this.mementos.clear();
        if (repertoire.exists()) {
            final File[] rep = repertoire.listFiles();
            if (rep != null) {
                Arrays.stream(rep).forEach(file -> mementos.add(MementoGameManager.fromFile(file)));
            }
        }
    }

    public GameManager loadGame(final Date gameId) {
        this.gameManager = mementos.stream().filter(mementos -> mementos.getDate().equals(gameId)).findFirst().get().getEtat();
        return this.gameManager;
    }

    public void setCurrentGameManager(final GameManager gm) {
        this.gameManager = gm;
    }

}
