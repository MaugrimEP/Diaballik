package diaballik.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import diaballik.restWrapper.SmallerGameManager;
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
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.Color;
import java.net.URI;
import java.util.List;

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

        InitGameClasses initGameClasses = new InitGameClasses(j1, j2, new PlateauStandard(), TypePartie.TYPE_J_VS_J);
        final ObjectMapper mapper = new DiabalikJacksonProvider().getMapper();
        try {
            final String serializedObject = mapper.writeValueAsString(initGameClasses);
            System.out.println(serializedObject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        final Response res = client.
                target(baseUri).
                path("game/init").
                request(MediaType.APPLICATION_JSON_TYPE).
                put(Entity.json(initGameClasses));
        GameManager gmres = res.readEntity(GameManager.class);

        System.out.println(gmres.getPlateau());
        GameManager gameManager = new GameManagerBuilder().joueur1(j1).joueur2(j2).plateau(new PlateauStandard()).typePartie(TypePartie.TYPE_J_VS_J).build();

        System.out.println(gameManager.getPlateau());
        Assertions.assertEquals(gameManager, gmres);
    }

    @Test
    void testInitGameWithInconsistentsPlayers(final Client client, final URI baseUri) {
        client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class).register(RestController.class);
        j2 = new JoueurHumain(Color.RED, "start");
        InitGameClasses initGameClasses = new InitGameClasses(j1, j2, new PlateauStandard(), TypePartie.TYPE_J_VS_J);
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

    private void deleteAllGame() {
        RestController.careTakerGameManager.listMementos()
                .forEach(mementoGameManager -> RestController.careTakerGameManager.deleteGame(mementoGameManager.getDate()));
    }

    @Test
    void testLoadGame(final Client client, final URI baseUri) {
        client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class).register(RestController.class);

        deleteAllGame();

        InitGameClasses initGameClasses = new InitGameClasses(j1, j2, new PlateauStandard(), TypePartie.TYPE_J_VS_J);
        GameManager gm = client.target(baseUri).path("game/init").request().put(Entity.json(initGameClasses)).readEntity(GameManager.class);
        client.target(baseUri).path("game/save").request().get();

        InitGameClasses initGameClasses2 = new InitGameClasses(j1, j2, new PlateauEnemyAmongUs(), TypePartie.TYPE_J_VS_J);
        GameManager gm2 = client.target(baseUri).path("game/init").request().put(Entity.json(initGameClasses2)).readEntity(GameManager.class);
        client.target(baseUri).path("game/save").request().get();

        List<SmallerGameManager> r_gameList = client.target(baseUri).path("game/games").request().get(new GenericType<List<SmallerGameManager>>() {
        });

        for (SmallerGameManager sgm : r_gameList) {
            String path = baseUri + "game/" + sgm.getDate().getTime() + "/";
            System.out.println(path);
            GameManager gameManager = client.target(path)
                    .request().get()
                    .readEntity(GameManager.class);
            Assertions.assertTrue(gameManager.equals(gm) && !gameManager.equals(gm2) || gameManager.equals(gm2) && !gameManager.equals(gm));
        }
    }

    @Test
    void testSaveGame(final Client client, final URI baseUri) {
        client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class).register(RestController.class);

        InitGameClasses initGameClasses = new InitGameClasses(j1, j2, new PlateauStandard(), TypePartie.TYPE_J_VS_J);
        final Response res1 = client.
                target(baseUri).
                path("game/init").
                request().
                put(Entity.json(initGameClasses));
        GameManager gmres = res1.readEntity(GameManager.class);

        Response response = client.target(baseUri).path("game/save").request().get();
        //MementoGameManager mementoGameManager = response.readEntity(MementoGameManager.class);
        List<MementoGameManager> memos = RestController.careTakerGameManager.listMementos();

        final MementoGameManager mementoGameManager = memos.get(memos.size() - 1);
        GameManager gmRecu = mementoGameManager.getEtat();


        Assertions.assertEquals(gmres, gmRecu);
    }

    @Test
    void testGetListGames(final Client client, final URI baseUri) {
        client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class).register(RestController.class);

        List<MementoGameManager> mementoGameManagers = RestController.careTakerGameManager.listMementos();
        for (MementoGameManager mgm : mementoGameManagers) {
            RestController.careTakerGameManager.deleteGame(mgm.getDate());
        }

        InitGameClasses initGameClasses = new InitGameClasses(j1, j2, new PlateauStandard(), TypePartie.TYPE_J_VS_J);
        GameManager gm = client.
                target(baseUri).
                path("game/init").
                request().
                put(Entity.json(initGameClasses)).readEntity(GameManager.class);
        client.target(baseUri).path("game/save").request().get();
        client.target(baseUri).path("game/save").request().get();
        client.target(baseUri).path("game/save").request().get();
        client.target(baseUri).path("game/save").request().get();
        client.target(baseUri).path("game/save").request().get();

        List<SmallerGameManager> r_gameList = client.target(baseUri).path("game/games").request().get(new GenericType<List<SmallerGameManager>>() {
        });
        List<MementoGameManager> mementoGameManagers2 = RestController.careTakerGameManager.listMementos();

        Assertions.assertEquals(5, r_gameList.size());
        for (MementoGameManager mgm : mementoGameManagers2) {
            Assertions.assertTrue(r_gameList.contains(mgm.toSmall()));
        }

    }

    @Test
    void testDeleteGame(final Client client, final URI baseUri) {
        deleteAllGame();

        client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class).register(RestController.class);
        InitGameClasses initGameClasses = new InitGameClasses(j1, j2, new PlateauStandard(), TypePartie.TYPE_J_VS_IA);

        client.target(baseUri).path("game/init").request().put(Entity.json(initGameClasses)).readEntity(GameManager.class);

        client.target(baseUri).path("game/save").request().get();
        client.target(baseUri).path("game/save").request().get();
        client.target(baseUri).path("game/save").request().get();
        client.target(baseUri).path("game/save").request().get();

        client.target(baseUri).path("game/games").request().get(new GenericType<List<SmallerGameManager>>() {
        }).stream().forEach(smallerGameManager -> {
            Assertions.assertTrue(RestController.careTakerGameManager.listMementos().stream()
                    .anyMatch(mementoGameManager -> mementoGameManager.getDate().equals(smallerGameManager.getDate())));

            client.target(baseUri).path("game/" + smallerGameManager.getDate().getTime() + "/").request().delete();

            Assertions.assertTrue(RestController.careTakerGameManager.listMementos().stream().noneMatch(mementoGameManager -> mementoGameManager.getDate().equals(smallerGameManager.getDate())));
        });

    }

    static Coordonnee c(int c1, int c2) {
        return FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(c1, c2);
    }


}
