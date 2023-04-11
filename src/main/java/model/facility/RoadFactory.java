package model.facility;

import model.GameModel;
import model.common.Coordinate;
import model.common.Dimension;

public class RoadFactory extends FacilityFactory {

    public RoadFactory(GameModel gm) {
        super(gm);
    }

    @Override
    public Facility createFacility(Coordinate coordinate) {
        return new Road(2000, 1000, coordinate, new Dimension(1, 1));
    }
}
