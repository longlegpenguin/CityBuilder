package model.zone;

import model.*;
import model.common.Coordinate;
import model.util.Level;
import model.common.Satisfaction;

public class ResidentialZoneFactory extends ZoneFactory {
    public ResidentialZoneFactory(GameModel gm) {
        super(gm);
    }

    @Override
    public Zone createZone(Coordinate coordinate) {
        return new ResidentialZone(
                Level.CONSTRUCTING,
                1,
                new ZoneStatistics(0,Level.CONSTRUCTING.getCapacity(), new Satisfaction()),
                gm.getCurrentDate(),
                coordinate
        );
    }
}
