package model.zone;

import model.GameModel;
import model.common.Coordinate;
import model.common.SideEffect;
import model.util.BuildableType;
import model.util.Date;
import model.util.Level;
import model.util.PathFinder;

public class CommercialZone extends Zone implements SideEffect {


    public CommercialZone(Level level, int dayToBuild, ZoneStatistics statistics, Date birthday, Coordinate coordinate) {
        super(level, dayToBuild, statistics, birthday, coordinate);
    }

    @Override
    public BuildableType getBuildableType() {
        return BuildableType.COMMERCIAL;
    }


    @Override
    public void effect(Zone zone, GameModel gm) {
        zone.getStatistics().getSatisfaction().setFreeWorkplaceEffect(
                zone.getStatistics().getSatisfaction().getFreeWorkplaceEffect() + 1);
    }

    @Override
    public void reverseEffect(Zone zone, GameModel gm) {
        zone.getStatistics().getSatisfaction().setFreeWorkplaceEffect(
                zone.getStatistics().getSatisfaction().getFreeWorkplaceEffect() - 1);
    }

    @Override
    public boolean condition(Zone zone, GameModel gm) {
        return (new PathFinder(gm.getMap()).manhattanDistance(this, zone) < 5);
    }
}
