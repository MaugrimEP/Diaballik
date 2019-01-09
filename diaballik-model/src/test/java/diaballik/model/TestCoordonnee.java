package diaballik.model;

import diaballik.model.coordonnee.Coordonnee;
import diaballik.model.coordonnee.FabriquePoidsMoucheCoordonnees;
import diaballik.model.exceptions.InvalidCoordinateException;
import diaballik.model.plateau.Plateau;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestCoordonnee {

    @Test
    void getLigneCoordonneePlateau() {
        List<Coordonnee> ligneCoordonnee = Plateau.getLigneCoordonnee(0, Plateau.SIZE);
        assertEquals(7, ligneCoordonnee.size());
        for (int i = 0; i < Plateau.SIZE; i++) {
            assertTrue(ligneCoordonnee.contains(FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(0, i)));
        }
    }

    @Test
    void fabriquePoidsMoucheGetCoordonneesOk() {
        Coordonnee coordonnees = FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(1, 2);
        assertEquals(2, coordonnees.colonne);
        assertEquals(1, coordonnees.ligne);
    }

    @Test
    void fabriquePoidsMoucheGetCoordonneesExceptionInvalidCoordinates() {
        assertThrows(InvalidCoordinateException.class, () -> FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(2, 9));
        assertThrows(InvalidCoordinateException.class, () -> FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(8, 2));
    }


    @Test
    void distanceCoordonnee() {
        Coordonnee c1 = FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(1, 1);
        Coordonnee c2 = FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(2, 2);
        assertEquals(2, c1.distanceTo(c2));
    }

    @Test
    void pathToDiago() {
        Coordonnee c1 = FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(1, 1);
        Coordonnee c2 = FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(5, 5);
        Collection<Coordonnee> coordonnees = c1.pathTo(c2);
        //coordonnees.stream().forEach((c)-> System.out.println(c));
        assertEquals(3, coordonnees.size());
        assertTrue(coordonnees.contains(FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(2, 2)));
        assertTrue(coordonnees.contains(FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(3, 3)));
        assertTrue(coordonnees.contains(FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(4, 4)));
    }

    @Test
    void pathToColonne() {
        Coordonnee c1 = FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(5, 5);
        Coordonnee c2 = FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(1, 5);
        Collection<Coordonnee> coordonnees = c1.pathTo(c2);
        coordonnees.stream().forEach((c) -> System.out.println(c));
        assertEquals(3, coordonnees.size());
        assertTrue(coordonnees.contains(FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(2, 5)));
        assertTrue(coordonnees.contains(FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(3, 5)));
        assertTrue(coordonnees.contains(FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(4, 5)));
    }

    @Test
    void pathToLigne() {
        Coordonnee c1 = FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(5, 1);
        Coordonnee c2 = FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(5, 5);
        Collection<Coordonnee> coordonnees = c1.pathTo(c2);
        coordonnees.stream().forEach((c) -> System.out.println(c));
        assertEquals(3, coordonnees.size());
        assertTrue(coordonnees.contains(FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(5, 2)));
        assertTrue(coordonnees.contains(FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(5, 3)));
        assertTrue(coordonnees.contains(FabriquePoidsMoucheCoordonnees.INSTANCE.getCoordonnees(5, 4)));
    }
}