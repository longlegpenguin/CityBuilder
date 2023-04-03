package controller;

import controller.util.GameMode;
import model.GameModel;
import model.common.Coordinate;
import org.junit.jupiter.api.Test;

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

    @Test
    void testMouseHandlerBuildZone() {
        controller.switchModeRequest(GameMode.RESIDENTIAL_MODE);
        controller.mouseClickRequest(new Coordinate(1, 1), null);
        System.out.println(gm.printMap());
        assertEquals(gm.getAllBuildable().size(), 2);
    }
}