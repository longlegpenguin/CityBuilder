package model.facility;

import model.GameModel;
import model.common.Constants;
import model.common.Coordinate;
import model.common.Dimension;

public class SchoolFactory extends FacilityFactory {

    public SchoolFactory(GameModel gm) {
        super(gm);
    }

    @Override
    public Facility createFacility(Coordinate coordinate) {
        return new School(Constants.SCHOOL_ONE_TIME_COST, Constants.SCHOOL_MAINTENANCE_FEE, coordinate, new Dimension(1, 2));
    }
}
