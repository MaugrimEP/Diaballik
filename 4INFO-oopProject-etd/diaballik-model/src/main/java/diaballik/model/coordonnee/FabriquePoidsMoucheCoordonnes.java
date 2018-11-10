package diaballik.model.coordonnee;

import java.util.List;

public class FabriquePoidsMoucheCoordonnes {

    public static FabriquePoidsMoucheCoordonnes INSTANCE = new FabriquePoidsMoucheCoordonnes();

    private List<List<Coordonnee>> coordonnee;

    public Coordonnee getCoordonnes(final int ligne, final int colonne) {
        return new Coordonnee(ligne, colonne);
    }

}
