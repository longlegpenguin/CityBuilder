package model.zone;

import model.GameModel;
import model.common.Coordinate;
import model.common.SideEffect;
import model.util.BuildableType;
import model.util.Date;
import model.util.Level;
import model.util.PathFinder;

public class IndustrialZone extends Zone implements SideEffect {


    public IndustrialZone(Level level, int dayToBuild, ZoneStatistics statistics, Date birthday, Coordinate coordinate) {
        super(level, dayToBuild, statistics, birthday, coordinate);
    }

    @Override
    public BuildableType getBuildableType() {
        return BuildableType.INDUSTRIAL;
    }


    @Override
    public void effect(Zone zone, GameModel gm) {
        if (new PathFinder(gm.getMap()).manhattanDistance(zone, gm.getMasterRoads().get(0)) != -1 && zone.getCitizens().size() < 10) {
            zone.getStatistics().getSatisfaction().setFreeWorkplaceEffect(zone.getStatistics().getSatisfaction().getFreeWorkplaceEffect() + 1);
        }
        zone.getStatistics().getSatisfaction().setIndustrialEffect(zone.getStatistics().getSatisfaction().getIndustrialEffect() - 1);
    }

    @Override
    public void reverseEffect(Zone zone, GameModel gm) {
        if (new PathFinder(gm.getMap()).manhattanDistance(zone, gm.getMasterRoads().get(0)) != -1 && zone.getCitizens().size() < 10) {
            zone.getStatistics().getSatisfaction().setFreeWorkplaceEffect(zone.getStatistics().getSatisfaction().getFreeWorkplaceEffect() - 1);
        }
        zone.getStatistics().getSatisfaction().setIndustrialEffect(zone.getStatistics().getSatisfaction().getIndustrialEffect() + 1);
    }

    @Override
    public boolean condition(Zone zone, GameModel gm) {
        return new PathFinder(gm.getMap()).euclideanDistance(this, zone) < 5;
    }
}