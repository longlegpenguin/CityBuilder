package controller;

import controller.util.GameMode;
import controller.util.TimeMode;
import model.GameModel;
import model.common.Coordinate;
import model.util.Date;

import static model.common.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    GameModel gm = new GameModel(5, 11);
    Controller controller = new Controller(gm);
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        gm.initialize();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void testMouseHandlerBuildZone() {
        controller.switchGameModeRequest(GameMode.RESIDENTIAL_MODE);
        controller.mouseClickRequest(new Coordinate(1, 1), null);
        System.out.println(gm.printMap());
        assertEquals(gm.getAllBuildable().size(), 2);
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
        assertEquals(gm.getAllBuildable().size(), 2);
        double afterBalance = gm.getCityStatistics().getBudget().getBalance();
        assertEquals(RESIDENTIAL_ONE_TIME_COST, beforeBalance - afterBalance);
    }
    @org.junit.jupiter.api.Test
    void testBuildResidentialZoneNonEmptyPlot() {
        double beforeBalance = gm.getCityStatistics().getBudget().getBalance();
        controller.switchGameModeRequest(GameMode.RESIDENTIAL_MODE);
        controller.mouseClickRequest(new Coordinate(1, 1), null);
        controller.mouseClickRequest(new Coordinate(1, 1), null);
        assertEquals(gm.getAllBuildable().size(), 2);
        double afterBalance = gm.getCityStatistics().getBudget().getBalance();
        assertEquals(RESIDENTIAL_ONE_TIME_COST, beforeBalance - afterBalance);
    }

    @org.junit.jupiter.api.Test
    void testDeZoneResidentialZoneUNDERCONSTRUCTED() {

        controller.switchGameModeRequest(GameMode.RESIDENTIAL_MODE);
        controller.mouseClickRequest(new Coordinate(1, 1), null);
        double beforeBalance = gm.getCityStatistics().getBudget().getBalance();

        controller.switchGameModeRequest(GameMode.DEMOLISH_MODE);
        controller.mouseClickRequest(new Coordinate(1, 1), null);
        assertEquals(1, gm.getAllBuildable().size());
        double afterBalance = gm.getCityStatistics().getBudget().getBalance();
        assertEquals(RETURN_RATE * RESIDENTIAL_ONE_TIME_COST, afterBalance - beforeBalance);
    }

    @org.junit.jupiter.api.Test
    void testDeZoneResidentialZoneHASaSSETS() {

        controller.switchGameModeRequest(GameMode.RESIDENTIAL_MODE);
        controller.mouseClickRequest(new Coordinate(1, 1), null);
        double beforeBalance = gm.getCityStatistics().getBudget().getBalance();

        controller.regularUpdateRequest(CONSTRUCTION_DAY + 1, null);

        controller.switchGameModeRequest(GameMode.DEMOLISH_MODE);
        controller.mouseClickRequest(new Coordinate(1, 1), null);
        assertEquals(2, gm.getAllBuildable().size());
        double afterBalance = gm.getCityStatistics().getBudget().getBalance();
        assertEquals(0, afterBalance - beforeBalance);
    }
}