package model.zone;

import model.common.Buildable;
import model.common.Coordinate;
import model.common.SideEffect;
import model.util.BuildableType;
import model.util.Date;
import model.util.Level;

public class CommercialZone extends Zone implements SideEffect {


    public CommercialZone(Level level, int dayToBuild, ZoneStatistics statistics, Date birthday, Coordinate coordinate) {
        super(level, dayToBuild, statistics, birthday, coordinate);
    }

    @Override
    public BuildableType getBuildableType() {
        return BuildableType.COMMERCIAL;
    }


    @Override
    public void effect(Zone zone, Buildable[][] map) {
        zone.getStatistics().getSatisfaction().setFreeWorkplaceEffect(1);
    }

    @Override
    public void reverseEffect(Zone zone, Buildable[][] map) {
        zone.getStatistics().getSatisfaction().setFreeWorkplaceEffect(0);
    }

    @Override
    public boolean condition(Zone zone, Buildable[][] map) {
        // TODO
        //return (new PathFinder(map).manhattanDistance(this, zone) < 5);
        return false;
    }
}
