package model.facility;

import model.GameModel;
import model.common.Constants;
import model.common.Coordinate;
import model.common.Dimension;

public class UniversityFactory extends FacilityFactory {

    public UniversityFactory(GameModel gm) {
        super(gm);
    }

    @Override
    public Facility createFacility(Coordinate coordinate) {
        return new University(
                Constants.UNIVERSITY_ONE_TIME_COST,
                Constants.UNIVERSITY_MAINTENANCE_FEE,
                coordinate,
                new Dimension(2,2));
    }
}
