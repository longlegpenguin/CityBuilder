package model.zone;

import model.common.Coordinate;
import model.util.BuildableType;
import model.util.Date;
import model.util.Level;

import static model.common.Constants.RESIDENTIAL_BASE_CAPACITY;

public class ResidentialZone extends Zone {

    public ResidentialZone(Level level, int dayToBuild, ZoneStatistics statistics, Date birthday, Coordinate coordinate, float effectRadius) {
        super(level, dayToBuild, statistics, birthday, coordinate, effectRadius);
    }

    @Override
    public BuildableType getBuildableType() {
        return BuildableType.RESIDENTIAL;
    }

    @Override
    public int getCapacity() {
        return RESIDENTIAL_BASE_CAPACITY;
    }

}