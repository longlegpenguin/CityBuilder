package model;

import model.util.BuildableType;
import model.util.Level;
import model.zone.Zone;

public class IndustrialZone extends Zone implements SideEffect {

    public IndustrialZone(Level level, int dayToBuild, ZoneStatistics statistics, Date birthday) {
        super(level, dayToBuild, statistics, birthday);
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