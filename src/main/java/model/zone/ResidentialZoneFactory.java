package model.zone;

import model.GameModel;
import model.common.Coordinate;
import model.common.Satisfaction;
import model.util.Level;

public class ResidentialZoneFactory extends ZoneFactory {

    public ResidentialZoneFactory(GameModel gm) {
        super(gm);
    }

    @Override
    public Zone createZone(Coordinate coordinate) {
        return new ResidentialZone(
                Level.CONSTRUCTING,
                1,
                new ZoneStatistics(0, Level.CONSTRUCTING.getCapacity(), new Satisfaction()),
                gm.getCurrentDate(),
                coordinate
        );
    }
}
