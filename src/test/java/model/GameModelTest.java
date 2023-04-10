package model;

import model.common.Coordinate;
import model.exceptions.OperationException;
import model.zone.ResidentialZoneFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {

    GameModel gm = new GameModel(5, 10);
    @BeforeEach
    void setUp() {
//        gm.initialize();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllBuildables() {
    }

    @Test
    void timePassUpdate() {
    }

    @Test
    void addUniqueZone() {
        try {
            gm.addZone(new ResidentialZoneFactory(gm).createZone(new Coordinate(3, 3)));
        } catch (OperationException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Hello");
        System.out.println(gm.printMap());
        assertEquals(1, gm.getAllBuildable().size());
    }
    @Test
    void addTwiceZone() throws OperationException {
        gm.addZone(new ResidentialZoneFactory(gm).createZone(new Coordinate(3, 3)));
        Assertions.assertThrows(OperationException.class, ()-> gm.addZone(new ResidentialZoneFactory(gm).createZone(new Coordinate(3, 3))));
        System.out.println(gm.printMap());
        assertEquals(1, gm.getAllBuildable().size());
    }
    @Test
    void addFacility() {
    }

    @Test
    void updateTaxRate() {
    }

    @Test
    void queryCityStatistics() {
    }

    @Test
    void queryZoneStatistics() {
    }

    @Test
    void queryCityBudget() {
    }

    @Test
    void getCurrentDate() {
    }
}