package model.zone;

import model.common.SideEffect;
import model.util.BuildableType;
import model.common.Coordinate;
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
    public void effect(Zone zone) {
        // TODO
    }

    @Override
    public void reverseEffect(Zone zone) {
        // TODO
    }

    @Override
    public boolean condition(Zone zone) {
        // TODO
        return false;
    }
}
