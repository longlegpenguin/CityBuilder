package model.zone;

import model.GameModel;
import model.common.Coordinate;
import model.common.ZoneSatisfaction;
import model.util.Level;

import static model.common.Constants.DAYS_FOR_CONSTRUCTION;

public class ResidentialZoneFactory extends ZoneFactory {

    public ResidentialZoneFactory(GameModel gm) {
        super(gm);
    }

    @Override
    public Zone createZone(Coordinate coordinate) {
        return new ResidentialZone(
                Level.CONSTRUCTING,
                DAYS_FOR_CONSTRUCTION,
                new ZoneStatistics(0, Level.CONSTRUCTING.getCapacity(), new ZoneSatisfaction()),
                gm.getCurrentDate(),
                coordinate,
                0
        );
    }
}
