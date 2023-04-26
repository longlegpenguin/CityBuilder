package model.facility;

import model.GameModel;
import model.common.Constants;
import model.common.Coordinate;
import model.common.Dimension;

public class StadiumFactory extends FacilityFactory {

    public StadiumFactory(GameModel gm) {
        super(gm);
    }

    @Override
    public Facility createFacility(Coordinate coordinate) {
        return new Stadium(
                Constants.STADIUM_ONE_TIME_COST,
                Constants.STADIUM_MAINTENANCE_FEE,
                coordinate,
                new Dimension(2, 2),
                5
        );
    }
}
