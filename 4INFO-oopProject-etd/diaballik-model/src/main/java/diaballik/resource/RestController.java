package diaballik.resource;

import diaballik.model.commande.Action;
import diaballik.model.commande.Commande;
import diaballik.model.game.GameManager;
import diaballik.model.game.GameManagerBuilder;
import diaballik.model.game.TypePartie;
import diaballik.model.joueur.Joueur;
import diaballik.model.memento.CareTakerGameManager;
import diaballik.model.plateau.Plateau;
import diaballik.restWrapper.InitGameClasses;
import diaballik.restWrapper.ResultOfAPlay;
import diaballik.restWrapper.SmallerGameManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
@Path("/game")
@Api(value = "Diaballik", description = "Operations Diaballik game")
public class RestController {
    private GameManager gm;

    public static final CareTakerGameManager careTakerGameManager = new CareTakerGameManager();

    public RestController() {
        super();
        gm = null;
        //careTakerGameManager = new CareTakerGameManager();
    }

    @PUT
    @Path("/init")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Initialize a game",
            response = GameManager.class)
    public GameManager initGame(final InitGameClasses initGameClasses) {
        final Joueur j1 = initGameClasses.getJ1();
        final Joueur j2 = initGameClasses.getJ2();
        if (Joueur.checkPlayerConsistency(j1, j2)) {
            final Plateau plateau = initGameClasses.getPlateau();
            final TypePartie typePartie = initGameClasses.getTypePartie();
            setGameManager(new GameManagerBuilder().joueur1(j1).joueur2(j2).plateau(plateau).typePartie(typePartie).build());
            return gm;
        } else {
            return null; //comment retourner une erreur
        }
    }

    private void setGameManager(final GameManager gameManager) {
        gm = gameManager;
        careTakerGameManager.setCurrentGameManager(gm);
    }

    @PUT
    @Path("/play")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Jouer un coup",
            response = ResultOfAPlay.class)
    public ResultOfAPlay jouer(final Action action) {
        final Joueur joueurCourant = gm.getJoueurCourant();

        List<Commande> commandes = new ArrayList<>();
        boolean gameFinished = false;
        Joueur winner = null;
        if (gm.coupBon(joueurCourant, action)) {
            gm.ajouterAction(action);
            commandes = gm.eventJouer();
            if (gm.gameFinished()) {
                gameFinished = true;
                winner = gm.getJoueurGagnant();
            }
        }
        return new ResultOfAPlay(
                commandes.stream().map(c -> c.action).collect(Collectors.toList()),
                gameFinished,
                winner);
    }

    @DELETE
    @Path("/{gameId}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Supprime une partie enregistrée")
    public void deleteGame(@ApiParam(value = "identifiant de la partie", required = true) @PathParam("gameId") final Date gameId) {
        careTakerGameManager.deleteGame(gameId);
    }

    @GET
    @Path("/{gameId}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Charge une partie enregistrée",
            response = GameManager.class)
    public GameManager loadGame(@ApiParam(value = "identifiant de la partie") @PathParam("gameId") final Date gameId) {
        final GameManager gameManager = careTakerGameManager.loadGame(gameId);
        gm = gameManager;
        return gameManager;
    }

    @GET
    @Path("/save")
    @ApiOperation(value = "Sauvegarde la partie actuelle")
    public void saveGame() {
        careTakerGameManager.saveCurrentGame();
    }

    @GET
    @Path("/games")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Donne la liste des parties enregistrées")
    public List<SmallerGameManager> getListeOldParties() {
        return careTakerGameManager.listMementos().stream()
                .map(mem -> new SmallerGameManager(mem.getEtat().getJoueur1(), mem.getEtat().getJoueur2(), mem.getDate()))
                .collect(Collectors.toList());
    }

}
