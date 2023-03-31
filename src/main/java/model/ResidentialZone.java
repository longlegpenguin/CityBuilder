package model;

import model.util.BuildableType;
import model.util.Coordinate;
import model.util.Level;
import model.zone.Zone;

import java.util.Collections;

public class ResidentialZone extends Zone {

    public ResidentialZone(Level level, int dayToBuild, ZoneStatistics statistics, Date birthday, Coordinate coordinate) {
        super(level, dayToBuild, statistics, birthday, coordinate);
    }

    @Override
    public BuildableType getBuildableType() {
        return BuildableType.RESIDENTIAL;
    }

}