package model.zone;

import model.util.BuildableType;
import model.common.Coordinate;
import model.common.Date;
import model.util.Level;

public class CommercialZone extends Zone {


    public CommercialZone(Level level, int dayToBuild, ZoneStatistics statistics, Date birthday, Coordinate coordinate) {
        super(level, dayToBuild, statistics, birthday, coordinate);
    }

    @Override
    public BuildableType getBuildableType() {
        return BuildableType.COMMERCIAL;
    }

}
