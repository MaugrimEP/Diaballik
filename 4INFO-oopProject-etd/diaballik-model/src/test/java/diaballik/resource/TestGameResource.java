package diaballik.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hanleyt.JerseyExtension;
import diaballik.model.game.GameManager;
import diaballik.model.game.TypePartie;
import diaballik.model.joueur.JoueurHumain;
import diaballik.model.plateau.PlateauEnemyAmongUs;
import diaballik.model.states.AutomateGameManager;
import diaballik.model.states.EtatJ1vsJ2;
import diaballik.restWrapper.InitGameClasses;
import diaballik.serialization.DiabalikJacksonProvider;

import java.awt.*;
import java.net.URI;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class TestGameResource {
	@SuppressWarnings("unused") @RegisterExtension JerseyExtension jerseyExtension = new JerseyExtension(this::configureJersey);

	private Application configureJersey() {
		return new ResourceConfig(RestController.class).//. TODO add your resource(s) here
				register(MyExceptionMapper.class).
				register(JacksonFeature.class).
				register(DiabalikJacksonProvider.class).
				property("jersey.config.server.tracing.type", "ALL");
	}

	@BeforeEach
	void setUp() {
	}

	@Test
	void testTemplate(final Client client, final URI baseUri) {
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);

		final Response res = client.
			target(baseUri).
			path("foo/bar"). //TODO
			request().
			put(Entity.text(""));

	}

	@Test
	void testInitGame(final Client client, final URI baseUri) {
		/*Matiere mat = target("calendar/mat").request().post(Entity.xml(new Matiere("DDR",1900))).readEntity(Matiere.class);
		Matiere matWithNewName = target("calendar/mat/"+mat.getId()+"/DDR ACE").request().put(Entity.text("")).readEntity(Matiere.class);
		assertEquals("DDR ACE", matWithNewName.getName());
		assertEquals(mat.getId(), matWithNewName.getId());*/
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class).register(RestController.class);
		GameManager gm = new GameManager();
		JoueurHumain j1 = new JoueurHumain(Color.RED,"ringo",gm);
		JoueurHumain j2 = new JoueurHumain(Color.BLACK,"start",gm);
		InitGameClasses initGameClasses = new InitGameClasses(j1,j2,new PlateauEnemyAmongUs(), TypePartie.TYPE_J_VS_J);
		final Response res = client.
				target(baseUri).
				path("game/init").
				request().
				put(Entity.json(initGameClasses));
		GameManager gmres  = res.readEntity(GameManager.class);

        GameManager gameManager = new GameManager(new AutomateGameManager(gm, new EtatJ1vsJ2()), new PlateauEnemyAmongUs(), null, j1, j2);
        Assertions.assertEquals(gameManager, gmres);
    }
}
