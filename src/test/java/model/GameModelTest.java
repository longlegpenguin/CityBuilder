package model;

import static model.common.Constants.*;

import controller.util.GameMode;
import model.common.Coordinate;
import model.exceptions.OperationException;
import model.facility.Road;
import model.facility.RoadFactory;
import model.facility.Stadium;
import model.facility.StadiumFactory;
import model.zone.ResidentialZoneFactory;
import model.zone.Zone;
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
    @Test
    void addUniqueZone() {
        try {
            gm.addZone(new ResidentialZoneFactory(gm).createZone(new Coordinate(3, 3)));
        } catch (OperationException e) {
            throw new RuntimeException(e);
        }
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
    void TestBuildRoadOnEmptyPlot() {
        double beforeBalance = gm.getCityStatistics().getBudget().getBalance();
        Road r = (Road) new RoadFactory(gm).createFacility(new Coordinate(3, 3));
        try {
            gm.addFacility(r);
        } catch (OperationException e) {
            throw new RuntimeException(e);
        }
        double afterBalance = gm.getCityStatistics().getBudget().getBalance();
        assertEquals(ROAD_ONE_TIME_COST, beforeBalance - afterBalance);
        assertEquals(gm.calculateSpend(), ROAD_MAINTENANCE_FEE);
    }

    @Test
    void TestBuildRoadOnNonEmptyPlot() throws OperationException {
        gm.addFacility(new RoadFactory(gm).createFacility(new Coordinate(3, 3)));
        double beforeBalance = gm.getCityStatistics().getBudget().getBalance();
        Assertions.assertThrows(OperationException.class, ()-> gm.addFacility(new RoadFactory(gm).createFacility(new Coordinate(3, 3))));
        double afterBalance = gm.getCityStatistics().getBudget().getBalance();
        assertEquals(1, gm.getAllBuildable().size());
        assertEquals(0, beforeBalance - afterBalance);
        assertEquals(ROAD_MAINTENANCE_FEE, gm.calculateSpend());
    }

    @Test
    void TestBuildStadiumOnEmptyPlot() {
        double beforeBalance = gm.getCityStatistics().getBudget().getBalance();
        Stadium r = (Stadium) new StadiumFactory(gm).createFacility(new Coordinate(3, 3));
        try {
            gm.addFacility(r);
        } catch (OperationException e) {
            throw new RuntimeException(e);
        }
        double afterBalance = gm.getCityStatistics().getBudget().getBalance();
        assertEquals(STADIUM_ONE_TIME_COST, beforeBalance - afterBalance);
        assertEquals(STADIUM_MAINTENANCE_FEE, gm.calculateSpend());
    }

    @Test
    void TestBuildStadiumOnNonEmptyPlot() throws OperationException {
        gm.addFacility(new RoadFactory(gm).createFacility(new Coordinate(3, 3)));
        double beforeBalance = gm.getCityStatistics().getBudget().getBalance();
        // 1,1
        Assertions.assertThrows(OperationException.class, ()-> gm.addFacility(new StadiumFactory(gm).createFacility(new Coordinate(4, 4))));
        // 0,0
        Assertions.assertThrows(OperationException.class, ()-> gm.addFacility(new StadiumFactory(gm).createFacility(new Coordinate(3, 2))));
        // 0,1
        Assertions.assertThrows(OperationException.class, ()-> gm.addFacility(new StadiumFactory(gm).createFacility(new Coordinate(3, 3))));
        // 1,0
        Assertions.assertThrows(OperationException.class, ()-> gm.addFacility(new StadiumFactory(gm).createFacility(new Coordinate(2, 3))));
        double afterBalance = gm.getCityStatistics().getBudget().getBalance();
        assertEquals(1, gm.getAllBuildable().size());
        assertEquals(0, beforeBalance - afterBalance);
        assertEquals(ROAD_MAINTENANCE_FEE, gm.calculateSpend());
    }

    @Test
    void TestBuildStadiumEffect() throws OperationException {
        Zone z = new ResidentialZoneFactory(gm).createZone(new Coordinate(2, 2));
        gm.addZone(z);
        double beforeSatis = z.getZoneSatisfaction(gm);
        gm.addFacility(new RoadFactory(gm).createFacility(new Coordinate(2, 3)));
        Stadium r = (Stadium) new StadiumFactory(gm).createFacility(new Coordinate(3, 3));
        gm.addFacility(r);
        double afterSatis = z.getZoneSatisfaction(gm);
        assertEquals(STADIUM_BASE_EFFECT, afterSatis - beforeSatis);
    }

    @Test
    void TestBuildTwoDifferentRoadOnNonEmptyPlot() throws OperationException {
        double beforeBalance = gm.getCityStatistics().getBudget().getBalance();
        gm.addFacility(new RoadFactory(gm).createFacility(new Coordinate(3, 3)));
        gm.addFacility(new RoadFactory(gm).createFacility(new Coordinate(3, 2)));
        double afterBalance = gm.getCityStatistics().getBudget().getBalance();
        assertEquals(2, gm.getAllBuildable().size());
        assertEquals(ROAD_ONE_TIME_COST * 2, beforeBalance - afterBalance);
        assertEquals(ROAD_MAINTENANCE_FEE * 2, gm.calculateSpend());
    }


    @Test
    void addFacility() {
    }

    @Test
    void updateTaxRate() {
    }

    @Test
    void queryCityBudget() {
    }

    @Test
    void getCurrentDate() {
    }
}