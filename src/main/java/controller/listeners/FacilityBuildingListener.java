package controller.listeners;

import controller.util.GameMode;
import controller.util.Property;
import model.GameModel;
import model.common.Coordinate;
import model.exceptions.OperationException;
import model.facility.*;

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
            case STADIUM_MODE -> facility = new StadiumFactory(gm).createFacility(coordinate);
            case FOREST_MODE -> facility = new ForestFactory(gm).createFacility(coordinate);
            case SCHOOL_MODE -> facility = new SchoolFactory(gm).createFacility(coordinate);
            case UNIVERSITY_MODE -> facility = new UniversityFactory(gm).createFacility(coordinate);
            case POLICE_MODE -> facility = new PoliceFactory(gm).createFacility(coordinate);
        }
        try {
            gm.addFacility(facility);
            assert facility != null;
            property.getCallBack().updateGridSystem(facility.getCoordinate(), facility);
            property.getCallBack().updateCityStatisticPanel(gm.getCityStatistics());
            property.getCallBack().updateBudgetPanel(gm.queryCityBudget());
            System.out.println("Created" + facility);
        } catch (OperationException e) {
            System.out.println(e.getMessage());
        }
    }
}
