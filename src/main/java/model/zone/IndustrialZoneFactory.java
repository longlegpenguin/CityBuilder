package model.zone;

import model.GameModel;
import model.common.Coordinate;
import model.common.ZoneSatisfaction;
import model.util.Level;

import static model.common.Constants.*;

public class IndustrialZoneFactory extends ZoneFactory {

    public IndustrialZoneFactory(GameModel gm) {
        super(gm);
    }

    @Override
    public Zone createZone(Coordinate coordinate) {
        return new IndustrialZone(
                Level.CONSTRUCTING,
                DAYS_FOR_CONSTRUCTION,
                new ZoneStatistics(0, new ZoneSatisfaction()),
                gm.getCurrentDate(),
                coordinate,
                INDUSTRIAL_EFFECT_RADIUS
        );
    }
}
