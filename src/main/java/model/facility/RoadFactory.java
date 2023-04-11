package model.facility;

import model.GameModel;
import model.common.Coordinate;
import model.common.Dimension;

public class RoadFactory extends FacilityFactory {

    public RoadFactory(GameModel gm) {
        super(gm);
    }

    @Override
    public Facility createFacility(int oneTimeCost, int maintenanceFee, Coordinate coordinate, Dimension dimension) {
        return new Road(oneTimeCost, maintenanceFee, coordinate, dimension);
    }
}
