package model.facility;

import model.common.Buildable;
import model.common.Coordinate;
import model.common.Dimension;
import model.util.BuildableType;
import model.util.PathFinder;
import model.zone.Zone;

public class Stadium extends EffectualFacility {

    public Stadium(int oneTimeCost, int maintenanceFee, Coordinate coordinate, Dimension dimension, float influenceRadius) {
        super(oneTimeCost, maintenanceFee, coordinate, dimension, influenceRadius);
    }

    @Override
    public BuildableType getBuildableType() {
        return BuildableType.STADIUM;
    }

    @Override
    public boolean isUnderConstruction() {
        return false;
    }


    @Override
    public void effect(Zone zone, Buildable[][] map) {
        zone.updateStadiumEffect(1);
    }

    @Override
    public void reverseEffect(Zone zone, Buildable[][] map) {
        zone.updateStadiumEffect(0);
    }

    @Override
    public boolean condition(Zone zone, Buildable[][] map) {
        return new PathFinder(map).manhattanDistance(zone, this) <= influenceRadius;
    }
}
