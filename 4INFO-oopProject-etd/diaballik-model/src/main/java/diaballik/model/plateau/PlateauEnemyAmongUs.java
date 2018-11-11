package diaballik.model.plateau;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import diaballik.model.Coordonnee;
import diaballik.model.FabriquePoidsMoucheCoordonnees;
import diaballik.model.joueur.Joueur;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

public class PlateauEnemyAmongUs extends Plateau {
    @JsonCreator
    protected PlateauEnemyAmongUs(@JsonProperty("lesCases") final Map<Coordonnee, Case> lesCases) {
        super(lesCases);
    }

    public PlateauEnemyAmongUs() {

    }

    @Override
    public void init(final Joueur j1, final Joueur j2) {

        final List<Coordonnee> ligneJ1 = getLigneCoordonnee(Plateau.SIZE - 1, Plateau.SIZE);
        final List<Coordonnee> ligneJ2 = getLigneCoordonnee(0, Plateau.SIZE);

        final int milieu = Plateau.SIZE / 2 + 1;
        ligneJ1.remove(FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(0, milieu)); //on retire le milieu pour ne pas l'intervertir
        ligneJ2.remove(FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(0, milieu));

        final Random random = new Random();

        Coordonnee c1 = ligneJ1.get(random.nextInt(ligneJ1.size()));
        Coordonnee c2 = ligneJ2.get(random.nextInt(ligneJ2.size()));
        swapCase(c1, c2);
        ligneJ1.remove(c1); //pour ne pas inverse deux fois la même
        ligneJ2.remove(c2);

        c1 = ligneJ1.get(random.nextInt(ligneJ1.size()));
        c2 = ligneJ2.get(random.nextInt(ligneJ2.size()));
        swapCase(c1, c2);
    }
}
