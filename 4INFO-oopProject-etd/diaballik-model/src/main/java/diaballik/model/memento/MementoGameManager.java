package diaballik.model.memento;


import diaballik.model.game.GameManager;

import java.util.Date;

public class MementoGameManager {

    private Date date;

    private GameManager etat;

    public GameManager getEtat() {
        return etat;
    }

    public Date getDate() {
        return date;
    }
}
