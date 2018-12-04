package diaballik.resource;

import com.github.hanleyt.JerseyExtension;
import diaballik.model.commande.Action;
import diaballik.model.coordonnee.Coordonnee;
import diaballik.model.coordonnee.FabriquePoidsMoucheCoordonnees;
import diaballik.model.game.GameManager;
import diaballik.model.game.GameManagerBuilder;
import diaballik.model.game.TypePartie;
import diaballik.model.joueur.IA;
import diaballik.model.joueur.Joueur;
import diaballik.model.joueur.JoueurHumain;
import diaballik.model.joueur.StrStarting;
import diaballik.model.memento.MementoGameManager;
import diaballik.model.plateau.PlateauEnemyAmongUs;
import diaballik.model.plateau.PlateauStandard;
import diaballik.restWrapper.InitGameClasses;
import diaballik.restWrapper.ResultOfAPlay;
import diaballik.serialization.DiabalikJacksonProvider;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.awt.Color;
import java.net.URI;

public class TestGameResource {
    @SuppressWarnings("unused")
    @RegisterExtension
    JerseyExtension jerseyExtension = new JerseyExtension(this::configureJersey);

    Joueur j1;
    Joueur j2;

    private Application configureJersey() {
        return new ResourceConfig(RestController.class).
                register(MyExceptionMapper.class).
                register(JacksonFeature.class).
                register(DiabalikJacksonProvider.class).
                property("jersey.config.server.tracing.type", "ALL");
    }

    @BeforeEach
    void setUp() {
        j1 = new JoueurHumain(Color.RED, "ringo");
        j2 = new JoueurHumain(Color.BLACK, "start");
    }

    @Test
    void testTemplate(final Client client, final URI baseUri) {
        client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);

        final Response res = client.
                target(baseUri).
                path("foo/bar").
                request().
                put(Entity.text(""));
    }

    @Test
    void testInitGame(final Client client, final URI baseUri) {
        client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class).register(RestController.class);

        InitGameClasses initGameClasses = new InitGameClasses(j1, j2, new PlateauEnemyAmongUs(), TypePartie.TYPE_J_VS_J);
        final Response res = client.
                target(baseUri).
                path("game/init").
                request().
                put(Entity.json(initGameClasses));
        GameManager gmres = res.readEntity(GameManager.class);

//        System.out.println(gmres.getPlateau());
        GameManager gameManager = new GameManagerBuilder().joueur1(j1).joueur2(j2).plateau(new PlateauEnemyAmongUs()).typePartie(TypePartie.TYPE_J_VS_J).build();

        System.out.println(gameManager.getPlateau());
        Assertions.assertEquals(gameManager, gmres);
    }

    @Test
    void testInitGameWithInconsistentsPlayers(final Client client, final URI baseUri) {
        client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class).register(RestController.class);
        j2 = new JoueurHumain(Color.RED, "start");
        InitGameClasses initGameClasses = new InitGameClasses(j1, j2, new PlateauEnemyAmongUs(), TypePartie.TYPE_J_VS_J);
        final Response res = client.
                target(baseUri).
                path("game/init").
                request().
                put(Entity.json(initGameClasses));
        GameManager gmres = res.readEntity(GameManager.class);
        Assertions.assertNull(gmres);
    }

    @Test
    void testPlayWithAutomatePlayerVSPlayer(final Client client, final URI baseUri) {
        client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class).register(RestController.class);
        InitGameClasses initGameClasses = new InitGameClasses(j1, j2, new PlateauStandard(), TypePartie.TYPE_J_VS_J);
        Response res = client.
                target(baseUri).
                path("game/init").
                request().
                put(Entity.json(initGameClasses));
        GameManager gmres = res.readEntity(GameManager.class);
        Assertions.assertNotNull(gmres);

        Action action = new Action(c(6, 0), c(5, 0));
        res = client.
                target(baseUri).
                path("game/play").
                request().
                put(Entity.json(action));
        ResultOfAPlay resultOfAPlay = res.readEntity(ResultOfAPlay.class);
        Assert.assertEquals(1, resultOfAPlay.getActions().size());

        action = new Action(c(5, 0), c(4, 0));

        res = client.
                target(baseUri).
                path("game/play").
                request().
                put(Entity.json(action));
        resultOfAPlay = res.readEntity(ResultOfAPlay.class);
        Assert.assertEquals(1, resultOfAPlay.getActions().size());

        action = new Action(c(4, 0), c(3, 0));

        res = client.
                target(baseUri).
                path("game/play").
                request().
                put(Entity.json(action));
        resultOfAPlay = res.readEntity(ResultOfAPlay.class);
        Assert.assertEquals(1, resultOfAPlay.getActions().size());

    }

    @Test
    void testPlayWithAutomatePlayerVSIA(final Client client, final URI baseUri) {
        client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class).register(RestController.class);
        j2 = new IA(Color.BLUE, "ia", null, new StrStarting());
        InitGameClasses initGameClasses = new InitGameClasses(j1, j2, new PlateauStandard(), TypePartie.TYPE_J_VS_IA);
        Response res = client.
                target(baseUri).
                path("game/init").
                request().
                put(Entity.json(initGameClasses));
        GameManager gmres = res.readEntity(GameManager.class);
        Assertions.assertNotNull(gmres);

        Action action = new Action(c(6, 0), c(5, 0));
        res = client.
                target(baseUri).
                path("game/play").
                request().
                put(Entity.json(action));
        ResultOfAPlay resultOfAPlay = res.readEntity(ResultOfAPlay.class);
        Assert.assertEquals(1, resultOfAPlay.getActions().size());

        action = new Action(c(5, 0), c(4, 0));

        res = client.
                target(baseUri).
                path("game/play").
                request().
                put(Entity.json(action));
        resultOfAPlay = res.readEntity(ResultOfAPlay.class);
        Assert.assertEquals(1, resultOfAPlay.getActions().size());

        action = new Action(c(4, 0), c(3, 0));

        res = client.
                target(baseUri).
                path("game/play").
                request().
                put(Entity.json(action));
        resultOfAPlay = res.readEntity(ResultOfAPlay.class);
        Assert.assertEquals(4, resultOfAPlay.getActions().size());

    }

    @Test
    void testLoadGame(final Client client, final URI baseUri) {
        client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class).register(RestController.class);
        InitGameClasses initGameClasses = new InitGameClasses(j1, j2, new PlateauStandard(), TypePartie.TYPE_J_VS_IA);
        Response res = client.
                target(baseUri).
                path("game/init").//TODO a changer et tester load
                request().
                put(Entity.json(initGameClasses));
        GameManager gmres = res.readEntity(GameManager.class);

    }

    @Test
    void testSaveGame(final Client client, final URI baseUri) {
        client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class).register(RestController.class);

        InitGameClasses initGameClasses = new InitGameClasses(j1, j2, new PlateauEnemyAmongUs(), TypePartie.TYPE_J_VS_J);
        final Response res1 = client.
                target(baseUri).
                path("game/init").
                request().
                put(Entity.json(initGameClasses));
        GameManager gmres = res1.readEntity(GameManager.class);

        GameManager gameManager = new GameManagerBuilder().joueur1(j1).joueur2(j2).plateau(new PlateauEnemyAmongUs()).typePartie(TypePartie.TYPE_J_VS_J).build();
        Assertions.assertEquals(gameManager, gmres);

        System.out.println("resquete 1");

        Response response = client.target(baseUri).path("game/save").request().get();
        //MementoGameManager mementoGameManager = response.readEntity(MementoGameManager.class);
        MementoGameManager mementoGameManager = RestController.careTakerGameManager.listMementos().get(0);
        GameManager gmRecu = mementoGameManager.getEtat();

        //GameManager gm = res1.readEntity(GameManager.class);


        Assertions.assertEquals(gmres, gmRecu);
    }

    @Test
    void testGetListGames(final Client client, final URI baseUri) {
        client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class).register(RestController.class);
        InitGameClasses initGameClasses = new InitGameClasses(j1, j2, new PlateauStandard(), TypePartie.TYPE_J_VS_IA);
        Response res = client.
                target(baseUri).
                path("game/init").//TODO a changer et tester games
                request().
                put(Entity.json(initGameClasses));
        GameManager gmres = res.readEntity(GameManager.class);

    }

    @Test
    void testDeleteGame(final Client client, final URI baseUri) {
        client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class).register(RestController.class);
        InitGameClasses initGameClasses = new InitGameClasses(j1, j2, new PlateauStandard(), TypePartie.TYPE_J_VS_IA);
        Response res = client.
                target(baseUri).
                path("game/init").//TODO a changer et tester delete
                request()
                .delete();
        GameManager gmres = res.readEntity(GameManager.class);
    }

    static Coordonnee c(int c1, int c2) {
        return FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(c1, c2);
    }


}
