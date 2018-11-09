package diaballik.resource;

import diaballik.model.commande.Action;
import diaballik.model.game.GameManager;
import diaballik.model.memento.CareTakerGameManager;
import io.swagger.annotations.Api;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;

@Singleton
@Path("/game")
@Api(value = "Diaballik", description = "Operations Diaballik game")
public class RestController {
    private GameManager gm;

    private CareTakerGameManager careTakerGameManager;

    public RestController() {
        super();
        gm = null;
        careTakerGameManager = new CareTakerGameManager();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public String welcom() {
        System.out.println("salut les mescs");
        return "{ \"coucou\" : \"hhi\"}";
    }

	/*@PUT
	@Path("/init/")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces(MediaType.APPLICATION_JSON)
	public GameManager initGame(Joueur j1, Joueur j2, Plateau plateau, TypePartie typePartie) {
		System.out.println("salut les mescs");
		return null;
	}*/


    public void jouer(final Action action) {

    }

    public String deleteGame(final Date gameId) {
        return null;
    }

    public String loadGame(final Date gameId) {
        return null;
    }

    public String getListeOldParties() {
        return null;
    }

    public void setGameManager(final GameManager gm) {

    }

}
