package diaballik.model.joueur;

import diaballik.model.commande.Action;
import diaballik.model.coordonnee.Coordonnee;
import diaballik.model.coordonnee.FabriquePoidsMoucheCoordonnees;
import diaballik.model.game.GameManager;
import diaballik.model.game.GameManagerBuilder;
import diaballik.model.game.TypePartie;
import diaballik.model.plateau.PlateauStandard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

class StrsTest {

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
    void reflechir() {

        StrNoob strNoob = new StrNoob();
        Action reflechir = strNoob.reflechir(j1);
        Action reflechir1 = strNoob.reflechir(j2);
        System.out.println(reflechir);
        System.out.println(reflechir1);
        System.out.println(gameManager.getPlateau().toString());
    }

    @Test
    void testCoupsRandom() {
        StrNoob strNoob = new StrNoob();
        Collection<Action> candidateActions = strNoob.getCandidateActions(j1);
        System.out.println(candidateActions);
        assertEquals(6, candidateActions.size());

    }

    @Test
    void testCoupsRandomAvecDiago() {
        StrNoob strNoob = new StrNoob();
        Coordonnee c60 = FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(6, 0);
        gameManager.getPlateau().move(c60,
                FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(6, 1));
        gameManager.getPlateau().move(FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(6, 1),
                FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(6, 2));
        gameManager.getPlateau().move(FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(6, 2),
                FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(6, 4));
        gameManager.getPlateau().move(FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(6, 4),
                FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(6, 5));
        gameManager.getPlateau().move(FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(6, 5),
                FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(6, 6));
        gameManager.getPlateau().move(FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(6, 6),
                FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(3, 0));

        Collection<Action> candidateActions = strNoob.getCandidateActions(j1);
        System.out.println(candidateActions);
        System.out.println(gameManager.getPlateau());
        assertEquals(9, candidateActions.size());

    }
}