package model;

import model.util.BuildableType;
import model.util.Coordinate;
import model.util.Level;
import model.zone.Zone;

public class CommercialZone extends Zone {


    public CommercialZone(Level level, int dayToBuild, ZoneStatistics statistics, Date birthday, Coordinate coordinate) {
        super(level, dayToBuild, statistics, birthday, coordinate);
    }

    @Override
    public BuildableType getBuildableType() {
        return BuildableType.COMMERCIAL;
    }

}
