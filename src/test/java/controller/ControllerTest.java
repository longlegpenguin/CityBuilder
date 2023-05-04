package controller;

import controller.util.GameMode;
import controller.util.TimeMode;
import model.GameModel;
import model.common.Coordinate;
import model.util.Date;

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
    void testTimeModeDaily() {
        controller.switchTimeModeRequest(TimeMode.DAILY);
        Date before = gm.getCurrentDate();
        controller.regularUpdateRequest(1, null);
        Date after = gm.getCurrentDate();
        assertEquals(1, after.dateDifference(before).get("days"));
    }

    @org.junit.jupiter.api.Test
    void testTimeModeWeekly() {
        controller.switchTimeModeRequest(TimeMode.WEEKLY);
        Date before = gm.getCurrentDate();
        controller.regularUpdateRequest(1, null);
        Date after = gm.getCurrentDate();
        assertEquals(7, after.dateDifference(before).get("days"));
    }

    @org.junit.jupiter.api.Test
    void testTimeModeMonthly() {
        controller.switchTimeModeRequest(TimeMode.MONTHLY);
        Date before = gm.getCurrentDate();
        controller.regularUpdateRequest(1, null);
        Date after = gm.getCurrentDate();
        assertEquals(30, after.dateDifference(before).get("days"));
    }

    @org.junit.jupiter.api.Test
    void testBuildResidentialZone() {
        controller.switchGameModeRequest(GameMode.RESIDENTIAL_MODE);
        controller.mouseClickRequest(new Coordinate(1, 1), null);
        System.out.println(gm.printMap());
        assertEquals(gm.getAllBuildable().size(), 2);
    }
}