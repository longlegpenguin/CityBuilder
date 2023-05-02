package model.facility;

import model.GameModel;
import model.common.Constants;
import model.common.Coordinate;
import model.common.Dimension;

public class PoliceFactory extends FacilityFactory {

    public PoliceFactory(GameModel gm) {
        super(gm);
    }

    @Override
    public Facility createFacility(Coordinate coordinate) {
        return new Police(
                Constants.POLICE_ONE_TIME_COST,
                Constants.POLICE_MAINTENANCE_FEE,
                coordinate,
                new Dimension(1, 1),
                Constants.POLICE_EFFECT_RADIUS

        );
    }
}