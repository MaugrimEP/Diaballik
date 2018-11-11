package diaballik.model;

import diaballik.model.exceptions.InvalidCoordinateException;
import diaballik.model.plateau.Plateau;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    @Test
    void fabriquePoidsMoucheGetCoordonneesOk() {
        Coordonnee coordonnees = FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(1, 2);
        assertEquals(2, coordonnees.colonne);
        assertEquals(1, coordonnees.ligne);
    }

    @Test
    void fabriquePoidsMoucheGetCoordonneesExceptionInvalidCoordinates() {
        assertThrows(InvalidCoordinateException.class, () -> FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(8, 2));
        assertThrows(InvalidCoordinateException.class, () -> FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(2, 9));
    }

    @Test
    void getLigneCoordonneePlateau() {
        List<Coordonnee> ligneCoordonnee = Plateau.getLigneCoordonnee(0, Plateau.SIZE);
        assertEquals(7, ligneCoordonnee.size());
        for (int i = 0; i < Plateau.SIZE; i++) {
          assertTrue(ligneCoordonnee.contains(FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(0,i)));
        }
    }
}