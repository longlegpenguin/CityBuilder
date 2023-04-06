package controller.listeners;

import controller.util.GameMode;
import controller.util.Property;
import model.GameModel;
import model.common.Coordinate;
import model.common.Dimension;
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
        GameModel gm = property.getGameModel();
        System.out.println(gmo);
        Facility facility = null;
        switch (gmo) {
            // TODO replace by factory
            case ROAD_MODE -> facility = new Road(0,0,coordinate, new Dimension(1,1));
        }
        try {
            gm.addFacility(facility);
            property.getCallBack().updateGridSystem(facility.getCoordinate(), facility);
            System.out.println("Created" + facility);
        } catch (OperationException e) {
            System.out.println("ha ha ha ha");
        }
    }
}
