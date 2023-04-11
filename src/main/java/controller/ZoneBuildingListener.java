package controller;

import controller.util.GameMode;
import controller.util.Property;
import model.common.Coordinate;

public class ZoneBuildingListener extends ServiceListener{


    public ZoneBuildingListener(Property property) {
        super(property);
    }

    @Override
    public void update(Coordinate coordinate) {
        // TODO create instance of zone according to game mode
        GameMode gmo = property.getGameMode();
        System.out.println(gmo);
        // TODO add zone to game model.
    }
}
