package model.facility;

import model.GameModel;
import model.common.Constants;
import model.common.Coordinate;
import model.common.Dimension;

public class RoadFactory extends FacilityFactory {

    public RoadFactory(GameModel gm) {
        super(gm);
    }

    @Override
    public Facility createFacility(Coordinate coordinate) {
        return new Road(Constants.ROAD_ONE_TIME_COST, Constants.ROAD_MAINTENANCE_FEE, coordinate, new Dimension(1, 1));
    }
}
