package controller;

import controller.util.GameMode;
import controller.util.Property;
import model.common.Coordinate;
import model.exceptions.OperationException;
import model.facility.Facility;
import model.zone.Zone;

public class ZoneBuildingListener extends ServiceListener {


    public ZoneBuildingListener(Property property) {
        super(property);
    }

    @Override
    public void update(Coordinate coordinate) {
        // TODO create instance of zone according to game mode
        GameMode gmo = property.getGameMode();
        System.out.println(gmo);
        Zone zone = null;

        try {
            // add created zone to game model.
            property.getGameModel().addZone(zone);
            // TODO call back
            property.getCallBack().updateGridSystem(null);
        } catch (OperationException e) {
            System.out.println("ha ha ha ha");
        }
    }
}
