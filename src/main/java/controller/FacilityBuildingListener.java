package controller;

import controller.util.GameMode;
import controller.util.Property;
import model.common.Coordinate;
import model.exceptions.OperationException;
import model.facility.Facility;
import model.facility.Road;

public class FacilityBuildingListener extends ServiceListener {

    public FacilityBuildingListener(Property property) {
        super(property);
    }

    @Override
    public void update(Coordinate coordinate) {
        // TODO create instance of facility according to game mode
        GameMode gmo = property.getGameMode();
        System.out.println(gmo);
        Facility facility = null;
        try {
            // add created facility to game model.
            property.getGameModel().addFacility(facility);
            // TODO call back
            property.getCallBack().updateGridSystem(null);
        } catch (OperationException e) {
            System.out.println("ha ha ha ha");
        }
    }
}
