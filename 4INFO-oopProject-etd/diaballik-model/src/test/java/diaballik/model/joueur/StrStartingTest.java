package diaballik.model.joueur;

import diaballik.model.commande.Action;
import diaballik.model.coordonnee.Coordonnee;
import diaballik.model.coordonnee.FabriquePoidsMoucheCoordonnees;
import diaballik.model.game.GameManager;
import diaballik.model.game.GameManagerBuilder;
import diaballik.model.game.TypePartie;
import diaballik.model.plateau.Plateau;
import diaballik.model.plateau.PlateauStandard;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;

class StrStartingTest {

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
        //(Integer c1, Integer c2) -> FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(c1, c2);
    }

    @Test
    void distanceBalleToBordAdverse() {
        Assert.assertEquals(Plateau.SIZE - 1, gameManager.getPlateau().distanceBalleToBordAdverse(j1));
    }

    @Test
    void distancePionsBordOppose() {
        Assert.assertEquals(6 * (Plateau.SIZE - 1), gameManager.getPlateau().distancePionsBordOppose(j1));

    }

    @Test
    void quantiteDInterceptionBalle() {
        System.out.println(gameManager.getPlateau());
        Assert.assertEquals(0, gameManager.getPlateau().quantiteDInterceptionBalle(j1));

        gameManager.getPlateau().move(c(6, 0), c(3, 0));
        gameManager.getPlateau().move(c(6, 1), c(2, 1));
        System.out.println(gameManager.getPlateau());
        Assert.assertEquals(2, gameManager.getPlateau().quantiteDInterceptionBalle(j1));
    }

    private Coordonnee c(final int li, final int co) {
        return FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(li, co);
    }


    @Test
    void reflechir() {

        System.out.println(gameManager.getPlateau());
        StrStarting strStarting = new StrStarting();
        for (int i = 0; i < 60; i++) {
            Action reflechir = strStarting.reflechir(j1);
            Action reflechir2 = strStarting.reflechir(j2);
            reflechir.doAction();
            reflechir2.doAction();
            System.out.println(gameManager.getPlateau().toString());
        }

    }
}