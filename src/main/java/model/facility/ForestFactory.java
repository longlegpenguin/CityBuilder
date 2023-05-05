package model.facility;

import model.GameModel;
import model.common.Constants;
import model.common.Coordinate;
import model.common.Dimension;

import static model.common.Constants.FOREST_EFFECT_RADIUS;

public class ForestFactory extends FacilityFactory {

    public ForestFactory(GameModel gm) {
        super(gm);
    }

    @Override
    public Facility createFacility(Coordinate coordinate) {
        return new Forest(
                Constants.FOREST_ONE_TIME_COST,
                Constants.FOREST_MAINTENANCE_FEE,
                coordinate,
                new Dimension(1, 1),
                FOREST_EFFECT_RADIUS,
                gm.getCurrentDate());
    }
}
