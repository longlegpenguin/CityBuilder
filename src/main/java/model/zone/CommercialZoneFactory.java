package model.zone;

import model.GameModel;
import model.common.Coordinate;
import model.common.ZoneSatisfaction;
import model.util.Level;

import static model.common.Constants.*;

public class CommercialZoneFactory extends ZoneFactory {

    public CommercialZoneFactory(GameModel gm) {
        super(gm);
    }

    @Override
    public Zone createZone(Coordinate coordinate) {
        return new CommercialZone(
                Level.CONSTRUCTING,
                DAYS_FOR_CONSTRUCTION,
                new ZoneStatistics(0, new ZoneSatisfaction()),
                gm.getCurrentDate(),
                coordinate,
                COMMERCIAL_EFFECT_RADIUS
        );
    }
}
