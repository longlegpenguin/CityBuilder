import model.GameModel;
import model.common.Coordinate;
import model.exceptions.OperationException;
import model.zone.ResidentialZoneFactory;
import persistence.Database;

public class Main {
    public static void main(String[] args) throws OperationException {
        GameModel gm = new GameModel(10,10);
        gm.addZone(new ResidentialZoneFactory(gm).createZone(new Coordinate(1,1)));
        gm.regularUpdate(2, null);
        Database.save(gm);
        gm = Database.read();
        System.out.println(gm.printMap());
        System.out.println(gm.getCurrentDate());
    }
}
