package diaballik.model.memento;

import diaballik.model.game.GameManager;
import diaballik.model.game.GameManagerBuilder;
import diaballik.model.game.TypePartie;
import diaballik.model.joueur.IA;
import diaballik.model.joueur.Joueur;
import diaballik.model.joueur.JoueurHumain;
import diaballik.model.joueur.StrProgressive;
import diaballik.model.plateau.PlateauStandard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class MementoGameManagerTest {

    private GameManager gameManager;
    private Joueur j2;
    private Joueur j1;


    @BeforeEach
    void init() {
        j1 = new JoueurHumain(Color.BLACK, "ringo");
        j2 = new IA(Color.BLUE, "star", null, new StrProgressive());
        gameManager = new GameManagerBuilder()
                .joueur1(j1)
                .joueur2(j2)
                .plateau(new PlateauStandard())
                .typePartie(TypePartie.TYPE_J_VS_J).build();
        //(Integer c1, Integer c2) -> FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(c1, c2);
        j2.jouer();
        j2.jouer();
        j2.jouer();
        j2.jouer();
    }

    @Test
    void fromFile() {
        MementoGameManager reference = gameManager.createMemento();
        MementoGameManager loaded = MementoGameManager.fromFile(new File(reference.getFileName()));

        assertEquals(reference.getEtat(), loaded.getEtat());
    }

    @Test
    void saveFile() {
        gameManager.createMemento();
    }

    @Test
    void deleteFile() {
        MementoGameManager reference = gameManager.createMemento();
        reference.deleteFile();

        final File f = new File(reference.getFileName());
        assertFalse(f.exists());
    }
}