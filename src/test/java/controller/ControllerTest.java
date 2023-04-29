package controller;

import controller.util.GameMode;
import model.GameModel;
import model.common.Coordinate;

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
}