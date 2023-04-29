package engine.engineMain;

import engine.display.DisplayManager;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class MainApp {

    public static void main(String[] args) {
        DisplayManager.createDisplay();
        Handler handler = new Handler("Savefile");

        /** --------------------------------------------------------------------
         * Try these methods !
         *         controller.switchModeRequest(GameMode.COMMERCIAL_MODE); // fot the type of new things
         *         controller.mouseClickRequest(new Coordinate(3, 3), null); // for new things onto map
         *         gm.getAllBuildable(); // for a list of buildables
         *         gm.getCurrentDate();
         *         gm.queryCityStatistics();
         *         gm.queryCityBudget();
         *         gm.queryZoneStatistics(new Coordinate(3,3));
         *
         * Try to implement ICallBack interface and pass to mouseClickRequest !
         * It is an option to update a single cell on grid system, when things happen.
         */
        /*GameModel gm = new GameModel(1000, 1000);
        gm.initialize();
        Controller controller = new Controller(gm);
        gm.getAllBuildable();
        System.out.println(gm.getAllBuildable());
        System.out.println(gm.getCurrentDate());
        //System.out.println(gm.queryCityStatistics());
        System.out.println(gm.queryCityBudget());
        //System.out.println(gm.queryZoneStatistics(new Coordinate(3,3)));
        /**
         * ----------------------------------------------------------------------
         */

        while(!glfwWindowShouldClose(DisplayManager.window)) {

            handler.render();
            DisplayManager.updateDisplay();

        }


        handler.cleanUp();
        DisplayManager.closeDisplay();
    }


}
