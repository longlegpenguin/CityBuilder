package persistence;

import model.GameModel;
import org.junit.jupiter.api.Test;

public class DatabaseTest {

    @Test
    public void runDatabaseMethods() {
        GameModel gm = new GameModel(10, 10);
        gm.initialize();
        gm.regularUpdate(2, null);
        Database.save(gm);
        Database.save(gm, "hello.ser");
        gm = Database.read();
        assert gm != null;
        System.out.println(gm.printMap());
        gm = Database.read("hello.ser");
        assert gm != null;
        System.out.println(gm.printMap());
        System.out.println(gm.getCurrentDate());
    }
}
