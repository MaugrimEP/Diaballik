package diaballik.model.joueur;

import diaballik.model.game.GameManager;
import diaballik.model.game.GameManagerBuilder;
import diaballik.model.game.TypePartie;
import diaballik.model.plateau.PlateauStandard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

class StrProgressiveTest {
    private GameManager gameManager;
    private JoueurHumain j2;
    private JoueurHumain j1;

    @BeforeEach
    void init() {
        j1 = new JoueurHumain(Color.BLACK, "ringo", null);
        j2 = new JoueurHumain(Color.BLUE, "star", null);
        gameManager = new GameManagerBuilder()
                .joueur1(j1)
                .joueur2(j2)
                .plateau(new PlateauStandard())
                .typePartie(TypePartie.TYPE_J_VS_J).build();
    }

    @Test
    void reflechirProg() {
        StrProgressive strProgressive = new StrProgressive();
        assertTrue(strProgressive.getCurrentStrategie() instanceof StrNoob);
        for (int i = 0; i < 15; i++) {
            assertFalse(strProgressive.getCurrentStrategie() instanceof StrStarting);
            strProgressive.reflechir(j1);

        }
        assertTrue(strProgressive.getCurrentStrategie() instanceof StrStarting);
    }

}