package model.zone;

import model.GameModel;
import model.common.Coordinate;
import model.common.ZoneSatisfaction;
import model.util.Level;

import static model.common.Constants.DAYS_FOR_CONSTRUCTION;
import static model.common.Constants.RESIDENTIAL_EFFECT_RADIUS;

public class ResidentialZoneFactory extends ZoneFactory {

    public ResidentialZoneFactory(GameModel gm) {
        super(gm);
    }

    @Override
    public Zone createZone(Coordinate coordinate) {
        return new ResidentialZone(
                Level.CONSTRUCTING,
                DAYS_FOR_CONSTRUCTION,
                new ZoneStatistics(0, new ZoneSatisfaction()),
                gm.getCurrentDate(),
                coordinate,
                RESIDENTIAL_EFFECT_RADIUS
        );
    }
}
