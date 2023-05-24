package model.zone;

import model.GameModel;
import model.common.Coordinate;
import model.common.SideEffect;
import model.util.BuildableType;
import model.util.Date;
import model.util.Level;
import model.util.PathFinder;

import static model.common.Constants.*;

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
            zone.getStatistics().getSatisfaction().setFreeWorkplaceEffect(zone.getStatistics().getSatisfaction().getFreeWorkplaceEffect() + FREE_WORKPLACE_BASE_EFFECT);
        }
        pollute(zone, gm);
    }

    @Override
    public void reverseEffect(Zone zone, GameModel gm) {
        if (new PathFinder(gm.getMap()).manhattanDistance(zone, gm.getMasterRoads().get(0)) != -1 && zone.getCitizens().size() < 10) {
            zone.getStatistics().getSatisfaction().setFreeWorkplaceEffect(zone.getStatistics().getSatisfaction().getFreeWorkplaceEffect() - FREE_WORKPLACE_BASE_EFFECT);
        }
        reversePollute(zone, gm);
    }

    @Override
    public boolean condition(Zone zone, GameModel gm) {
        double euclideanDist = new PathFinder(gm.getMap()).euclideanDistance(this, zone);
        return euclideanDist < effectRadius;
    }

    @Override
    public int getCapacity() {
        return INDUSTRY_BASE_CAPACITY;
    }

    /**
     * Pollution effects on the given zone
     * @param zone zone to be polluted.
     * @param gm game model
     */
    public void pollute(Zone zone, GameModel gm) {
        if (condition(zone, gm)) {
            zone.updateIndustrialEffect((int) zone.getIndustrialEffect() - INDUSTRIAL_ZONE_BASE_EFFECT);
        }
    }

    /**
     * Removes pollution effects on the given zone
     * @param zone zone to be polluted.
     * @param gm game model
     */
    public void reversePollute(Zone zone, GameModel gm) {
        if (condition(zone, gm)) {
            zone.updateIndustrialEffect((int) zone.getIndustrialEffect() + INDUSTRIAL_ZONE_BASE_EFFECT);
        }
    }
}