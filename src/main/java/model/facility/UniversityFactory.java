package model.facility;

import model.GameModel;
import model.common.Coordinate;
import model.common.Dimension;

public class UniversityFactory extends FacilityFactory{

    public UniversityFactory(GameModel gm) { super(gm); }

    @Override
    public Facility createFacility(Coordinate coordinate) {
        return new University(50000, 10000, coordinate, new Dimension(2, 2));
    }
}
