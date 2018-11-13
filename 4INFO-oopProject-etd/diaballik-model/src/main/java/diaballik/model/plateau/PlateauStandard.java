package diaballik.model.plateau;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import diaballik.model.coordonnee.Coordonnee;
import diaballik.model.coordonnee.FabriquePoidsMoucheCoordonnees;
import diaballik.model.joueur.Joueur;

import java.util.Map;

public class PlateauStandard extends Plateau {
    @JsonCreator
    protected PlateauStandard(@JsonProperty("lescases") final Map<Coordonnee, Case> lescases) {
        super(lescases);
    }

    public PlateauStandard() {
    }

    @Override
    public void init(final Joueur j1, final Joueur j2) {
        super.init(j1, j2);

        final int milieu = Plateau.SIZE / 2 + 1;
        final Coordonnee milieuJ1 = FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(Plateau.SIZE - 1, milieu);
        final Coordonnee milieuJ2 = FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(0, milieu);
        lesCases.get(milieuJ1).setBalle(new Balle(j1));
        lesCases.get(milieuJ2).setBalle(new Balle(j2));
    }


}
