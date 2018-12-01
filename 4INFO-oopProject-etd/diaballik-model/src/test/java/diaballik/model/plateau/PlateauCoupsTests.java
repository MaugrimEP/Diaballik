package diaballik.model.plateau;

import diaballik.model.commande.Action;
import diaballik.model.coordonnee.Coordonnee;
import diaballik.model.coordonnee.FabriquePoidsMoucheCoordonnees;
import diaballik.model.game.GameManager;
import diaballik.model.game.GameManagerBuilder;
import diaballik.model.game.TypePartie;
import diaballik.model.joueur.JoueurHumain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.Color;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

class PlateauCoupsTests {
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
    void coupBonCoordonneeDiago() {
        Coordonnee depart = FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(6, 3);
        Coordonnee arrive = FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(3, 0);

        gameManager.getPlateau().move(FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(6, 0), arrive);
        System.out.println(gameManager.getPlateau());

        assertTrue(gameManager.getPlateau().coupBon(j1, new Action(depart, arrive, gameManager.getPlateau())));

    }


    static Stream<Object> getParametersCoupBon() {
        return Stream.of(
                Arguments.of(c(6, 3), c(6, 2), true),
                Arguments.of(c(6, 3), c(6, 4), true),
                Arguments.of(c(6, 2), c(5, 2), true),
                Arguments.of(c(6, 6), c(4, 6), false),
                Arguments.of(c(6, 3), c(6, 0), false)

        ); //p1); // Add other marshallable elements
    }

    static Coordonnee c(int c1, int c2) {
        return FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(c1, c2);
    }

    @ParameterizedTest
    @MethodSource("getParametersCoupBon")
    void testCoupBon(final Coordonnee depart, final Coordonnee arrive, final Boolean guard) {
        assertEquals(guard, gameManager.getPlateau().coupBon(j1, new Action(depart, arrive, gameManager.getPlateau())));
    }

    @Test
    void testStratPlateau() {
        gameManager = new GameManagerBuilder()
                .joueur1(j1)
                .joueur2(j2)
                .plateau(new PlateauStandard())
                .typePartie(TypePartie.TYPE_J_VS_J).build();
        System.out.println(gameManager.getPlateau());

        gameManager = new GameManagerBuilder()
                .joueur1(j1)
                .joueur2(j2)
                .plateau(new PlateauEnemyAmongUs())
                .typePartie(TypePartie.TYPE_J_VS_J).build();
        System.out.println(gameManager.getPlateau());

        gameManager = new GameManagerBuilder()
                .joueur1(j1)
                .joueur2(j2)
                .plateau(new PlateauRandomBall())
                .typePartie(TypePartie.TYPE_J_VS_J).build();
        System.out.println(gameManager.getPlateau());
    }

}