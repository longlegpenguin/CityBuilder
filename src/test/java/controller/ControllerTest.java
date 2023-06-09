package controller;

import controller.util.GameMode;
import controller.util.TimeMode;
import model.GameModel;
import model.common.Coordinate;
import model.common.Dimension;
import model.facility.Road;
import model.util.Date;

import static model.common.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    GameModel gm = new GameModel(5, 11);
    Controller controller = new Controller(gm);
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        for (int i = 0; i < 10; i++) {
            Road road = new Road(ROAD_ONE_TIME_COST, ROAD_MAINTENANCE_FEE, new Coordinate(5 - 1, i), new Dimension(1, 1));
            gm.getMasterRoads().add(road);
            gm.addToMap(road);
        }
    }

    @org.junit.jupiter.api.Test
    void testMouseHandlerBuildZone() {
        controller.switchGameModeRequest(GameMode.RESIDENTIAL_MODE);
        controller.mouseClickRequest(new Coordinate(1, 1), null);
        assertEquals(gm.getZoneBuildable().size(), 1);
    }

    @org.junit.jupiter.api.Test
    void testTimeModePause() {
        controller.switchTimeModeRequest(TimeMode.PAUSE);
        Date before = gm.getCurrentDate();
        controller.regularUpdateRequest(1, null);
        Date after = gm.getCurrentDate();
        assertEquals(0, after.dateDifference(before).get("days"));
        assertEquals(0, after.dateDifference(before).get("years"));
        assertEquals(0, after.dateDifference(before).get("months"));
    }

    @org.junit.jupiter.api.Test
    void testTimeModeDaily() {
        controller.switchTimeModeRequest(TimeMode.DAILY);
        Date before = gm.getCurrentDate();
        controller.regularUpdateRequest(1, null);
        Date after = gm.getCurrentDate();
        assertEquals(1, after.dateDifference(before).get("days"));
        assertEquals(0, after.dateDifference(before).get("years"));
        assertEquals(0, after.dateDifference(before).get("months"));
    }

    @org.junit.jupiter.api.Test
    void testTimeModeWeekly() {
        controller.switchTimeModeRequest(TimeMode.WEEKLY);
        Date before = gm.getCurrentDate();
        controller.regularUpdateRequest(1, null);
        Date after = gm.getCurrentDate();
        assertEquals(7, after.dateDifference(before).get("days"));
        assertEquals(0, after.dateDifference(before).get("years"));
        assertEquals(0, after.dateDifference(before).get("months"));
    }

    @org.junit.jupiter.api.Test
    void testTimeModeMonthly() {
        controller.switchTimeModeRequest(TimeMode.MONTHLY);
        Date before = gm.getCurrentDate();
        controller.regularUpdateRequest(1, null);
        Date after = gm.getCurrentDate();
        assertEquals(30, after.dateDifference(before).get("days"));
        assertEquals(0, after.dateDifference(before).get("years"));
        assertEquals(0, after.dateDifference(before).get("months"));
    }

    @org.junit.jupiter.api.Test
    void testBuildResidentialZone() {
        double beforeBalance = gm.getCityStatistics().getBudget().getBalance();
        controller.switchGameModeRequest(GameMode.RESIDENTIAL_MODE);
        controller.mouseClickRequest(new Coordinate(1, 1), null);
        System.out.println(gm.printMap());
        assertEquals(gm.getZoneBuildable().size(), 1);
        double afterBalance = gm.getCityStatistics().getBudget().getBalance();
        assertEquals(ZONE_ONE_TIME_COST, beforeBalance - afterBalance);
    }
    @org.junit.jupiter.api.Test
    void testBuildResidentialZoneNonEmptyPlot() {
        double beforeBalance = gm.getCityStatistics().getBudget().getBalance();
        controller.switchGameModeRequest(GameMode.RESIDENTIAL_MODE);
        controller.mouseClickRequest(new Coordinate(1, 1), null);
        controller.mouseClickRequest(new Coordinate(1, 1), null);
        assertEquals(gm.getZoneBuildable().size(), 1);
        double afterBalance = gm.getCityStatistics().getBudget().getBalance();
        assertEquals(ZONE_ONE_TIME_COST, beforeBalance - afterBalance);
    }

    @org.junit.jupiter.api.Test
    void testDeZoneResidentialZoneUNDERCONSTRUCTED() {

        controller.switchGameModeRequest(GameMode.RESIDENTIAL_MODE);
        controller.mouseClickRequest(new Coordinate(1, 1), null);
        double beforeBalance = gm.getCityStatistics().getBudget().getBalance();

        controller.switchGameModeRequest(GameMode.DEMOLISH_MODE);
        controller.mouseClickRequest(new Coordinate(1, 1), null);
        assertEquals(0, gm.getZoneBuildable().size());
        double afterBalance = gm.getCityStatistics().getBudget().getBalance();
        assertEquals(RETURN_RATE * ZONE_ONE_TIME_COST, afterBalance - beforeBalance);
    }

    @org.junit.jupiter.api.Test
    void testDeZoneResidentialZoneHASaSSETS() {

        controller.switchGameModeRequest(GameMode.RESIDENTIAL_MODE);
        controller.mouseClickRequest(new Coordinate(1, 1), null);
        double beforeBalance = gm.getCityStatistics().getBudget().getBalance();

        controller.regularUpdateRequest(DAYS_FOR_CONSTRUCTION + 1, null);

        controller.switchGameModeRequest(GameMode.DEMOLISH_MODE);
        controller.mouseClickRequest(new Coordinate(1, 1), null);
        assertEquals(1, gm.getZoneBuildable().size());
        double afterBalance = gm.getCityStatistics().getBudget().getBalance();
        assertEquals(0, afterBalance - beforeBalance);
    }

    @org.junit.jupiter.api.Test
    void testSetTaxRate() {
        controller.updateTaxRate(0.4, null);
        assertEquals(0.4, gm.getCityStatistics().getBudget().getTaxRate());
    }
    @org.junit.jupiter.api.Test
    void testSetBadTaxRate() {
        controller.updateTaxRate(4, null);
        assertEquals(0.3, gm.getCityStatistics().getBudget().getTaxRate());
    }
}