package controller.listeners;

import controller.util.GameMode;
import controller.util.Property;
import model.GameModel;
import model.common.Coordinate;
import model.common.Dimension;
import model.exceptions.OperationException;
import model.facility.Facility;
import model.facility.Road;
import model.facility.RoadFactory;

public class FacilityBuildingListener extends ServiceListener {

    public FacilityBuildingListener(Property property) {
        super(property);
    }

    @Override
    public void update(Coordinate coordinate) {
        GameMode gmo = property.getGameMode();
        GameModel gm = property.getGameModel();
        System.out.println(gmo);
        Facility facility = null;
        switch (gmo) {
            case ROAD_MODE -> facility = new RoadFactory(gm).createFacility(coordinate);
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
