package model.zone;

import model.common.SideEffect;
import model.util.BuildableType;
import model.common.Coordinate;
import model.common.Date;
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
    public void effect(Zone zone) {
        zone.getStatistics().getSatisfaction().setIndustrialEffect(0);
    }

    @Override
    public boolean condition(Zone zone) {
        //return find(map, this, zone) < 5;
        return true;
    }
}