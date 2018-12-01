package diaballik.model.plateau;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import diaballik.model.coordonnee.Coordonnee;
import diaballik.model.coordonnee.FabriquePoidsMoucheCoordonnees;
import diaballik.model.joueur.Joueur;

import java.util.Map;
import java.util.Random;

public class PlateauRandomBall extends Plateau {
    @JsonCreator
    protected PlateauRandomBall(@JsonProperty("lescases") final Map<Coordonnee, Case> lescases) {
        super(lescases);
    }

    public PlateauRandomBall() {
    }

    @Override
    public void init(final Joueur j1, final Joueur j2) {
        super.init(j1, j2);
        final int milieu = Plateau.SIZE / 2;
        final Coordonnee milieuJ1 = FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(Plateau.SIZE - 1, milieu);
        final Coordonnee milieuJ2 = FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(0, milieu);

        final Random random = new Random();
        final Coordonnee ballIndexJ1 = FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(Plateau.SIZE - 1, random.nextInt(Plateau.SIZE));
        swapCase(milieuJ1, ballIndexJ1);

        final Coordonnee ballIndexJ2 = FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(0, random.nextInt(Plateau.SIZE));
        swapCase(milieuJ2, ballIndexJ2);
    }
}
