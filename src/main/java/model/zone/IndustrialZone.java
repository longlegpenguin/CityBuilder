package model.zone;

import model.common.Buildable;
import model.common.Coordinate;
import model.common.SideEffect;
import model.util.BuildableType;
import model.util.Date;
import model.util.Level;

public class IndustrialZone extends Zone implements SideEffect {


    public IndustrialZone(Level level, int dayToBuild, ZoneStatistics statistics, Date birthday, Coordinate coordinate) {
        super(level, dayToBuild, statistics, birthday, coordinate);
    }

    @Override
    public BuildableType getBuildableType() {
        return BuildableType.INDUSTRIAL;
    }


    @Override
    public void effect(Zone zone, Buildable[][] map) {
        zone.getEffectedBy().add(this);
        zone.getStatistics().getSatisfaction().setFreeWorkplaceEffect(1);
        zone.getStatistics().getSatisfaction().setIndustrialEffect(0);
    }

    @Override
    public void reverseEffect(Zone zone, Buildable[][] map) {
        zone.getEffectedBy().remove(this);
        zone.getStatistics().getSatisfaction().setFreeWorkplaceEffect(0);
        zone.getStatistics().getSatisfaction().setIndustrialEffect(1);
    }

    @Override
    public boolean condition(Zone zone, Buildable[][] map) {
        //TODO
        return false;
//        return new PathFinder(map).euclideanDistance(this, zone) < 5;
    }
}