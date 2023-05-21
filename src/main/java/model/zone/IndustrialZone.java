package model.zone;

import model.GameModel;
import model.common.Coordinate;
import model.common.SideEffect;
import model.util.BuildableType;
import model.util.Date;
import model.util.Level;
import model.util.PathFinder;

import static model.common.Constants.INDUSTRIAL_ZONE_BASE_EFFECT;

public class IndustrialZone extends Zone implements SideEffect {


    public IndustrialZone(Level level, int dayToBuild, ZoneStatistics statistics, Date birthday, Coordinate coordinate, float effectRadius) {
        super(level, dayToBuild, statistics, birthday, coordinate, effectRadius);
    }

    @Override
    public BuildableType getBuildableType() {
        return BuildableType.INDUSTRIAL;
    }

    @Override
    public void effect(Zone zone, GameModel gm) {
        if (new PathFinder(gm.getMap()).manhattanDistance(zone, gm.getMasterRoads().get(0)) != -1 && zone.getCitizens().size() < 10) {
            zone.getStatistics().getSatisfaction().setFreeWorkplaceEffect(zone.getStatistics().getSatisfaction().getFreeWorkplaceEffect() + INDUSTRIAL_ZONE_BASE_EFFECT);
        }
        if (condition(zone, gm)) {
            zone.updateIndustrialEffect((int) zone.getIndustrialEffect() - INDUSTRIAL_ZONE_BASE_EFFECT);
        }
    }

    @Override
    public void reverseEffect(Zone zone, GameModel gm) {
        if (new PathFinder(gm.getMap()).manhattanDistance(zone, gm.getMasterRoads().get(0)) != -1 && zone.getCitizens().size() < 10) {
            zone.getStatistics().getSatisfaction().setFreeWorkplaceEffect(zone.getStatistics().getSatisfaction().getFreeWorkplaceEffect() - INDUSTRIAL_ZONE_BASE_EFFECT);
        }
        if (condition(zone, gm)) {
            zone.updateIndustrialEffect((int) zone.getIndustrialEffect() + INDUSTRIAL_ZONE_BASE_EFFECT);
        }
    }

    @Override
    public boolean condition(Zone zone, GameModel gm) {
        double euclideanDist = new PathFinder(gm.getMap()).euclideanDistance(this, zone);
        return euclideanDist < effectRadius;
    }

    @Override
    public int getCapacity() {
        return super.getCapacity() - 5;
    }
}