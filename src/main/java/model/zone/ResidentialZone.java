package model.zone;

import model.common.Coordinate;
import model.util.BuildableType;
import model.util.Date;
import model.util.Level;

public class ResidentialZone extends Zone {

    public ResidentialZone(Level level, int dayToBuild, ZoneStatistics statistics, Date birthday, Coordinate coordinate) {
        super(level, dayToBuild, statistics, birthday, coordinate);
    }

    @Override
    public BuildableType getBuildableType() {
        return BuildableType.RESIDENTIAL;
    }

}