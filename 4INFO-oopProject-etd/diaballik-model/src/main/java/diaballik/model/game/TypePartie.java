package diaballik.model.game;

import diaballik.model.states.EtatJ1vsIA;
import diaballik.model.states.EtatJ1vsJ2;
import diaballik.model.states.EtatTour;

public enum TypePartie {
    TYPE_J_VS_J,
    TYPE_J_VS_IA;

    private EtatTour etatInitial;

    static {
        TYPE_J_VS_J.etatInitial = new EtatJ1vsJ2();
        TYPE_J_VS_J.etatInitial = new EtatJ1vsIA();
    }

    public EtatTour getEtatInitial() {
        return etatInitial;
    }
}
