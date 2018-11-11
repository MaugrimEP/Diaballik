package diaballik.model;

import diaballik.model.exceptions.InvalidCoordinateException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    @Test
    void fabriquePoidsMoucheGetCoordonneesOk() {
        Coordonnee coordonnees = FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(1, 2);
        assertEquals(2,coordonnees.colonne);
        assertEquals(1,coordonnees.ligne);
    }

    @Test
    void fabriquePoidsMoucheGetCoordonneesExceptionInvalidCoordinates() {
        assertThrows(InvalidCoordinateException.class,()->FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(8, 2));
        assertThrows(InvalidCoordinateException.class,()->FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(2, 9));
    }
}