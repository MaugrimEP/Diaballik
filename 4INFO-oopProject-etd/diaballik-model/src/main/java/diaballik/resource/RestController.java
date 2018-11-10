package diaballik.resource;

import diaballik.model.commande.Action;
import diaballik.model.game.GameManager;
import diaballik.model.game.GameManagerBuilder;
import diaballik.model.game.TypePartie;
import diaballik.model.joueur.Joueur;
import diaballik.model.memento.CareTakerGameManager;
import diaballik.model.plateau.Plateau;
import diaballik.restWrapper.InitGameClasses;
import io.swagger.annotations.Api;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
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
        return "{ \"coucou\" : \"hhi\"}";
    }

    @PUT
    @Path("/init/")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.APPLICATION_JSON)
    public GameManager initGame(final InitGameClasses initGameClasses) {
        final Joueur j1 = initGameClasses.getJ1();
        final Joueur j2 = initGameClasses.getJ2();
        if (Joueur.checkPlayerConsistency(j1, j2)) {
            final Plateau plateau = initGameClasses.getPlateau();
            final TypePartie typePartie =  initGameClasses.getTypePartie();
            return new GameManagerBuilder().joueur1(j1).joueur2(j2).plateau(plateau).typePartie(typePartie).build();
        } else {
            return null; //comment retourner une erreur
        }
    }


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
