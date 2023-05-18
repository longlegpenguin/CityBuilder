package model.zone;

import model.GameModel;
import model.common.Coordinate;
import model.common.SideEffect;
import model.util.BuildableType;
import model.util.Date;
import model.util.Level;
import model.util.PathFinder;

import static model.common.Constants.COMMERCIAL_ZONE_BASE_EFFECT;

public class CommercialZone extends Zone implements SideEffect {


    public CommercialZone(Level level, int dayToBuild, ZoneStatistics statistics, Date birthday, Coordinate coordinate, float effectRadius) {
        super(level, dayToBuild, statistics, birthday, coordinate, effectRadius);
    }

    @Override
    public BuildableType getBuildableType() {
        return BuildableType.COMMERCIAL;
    }


    @Override
    public void effect(Zone zone, GameModel gm) {
        if (condition(zone, gm)) {
            zone.getStatistics().getSatisfaction().setFreeWorkplaceEffect(
                    zone.getStatistics().getSatisfaction().getFreeWorkplaceEffect() + COMMERCIAL_ZONE_BASE_EFFECT);
        }
    }

    @Override
    public void reverseEffect(Zone zone, GameModel gm) {
        if (condition(zone, gm)) {
            zone.getStatistics().getSatisfaction().setFreeWorkplaceEffect(
                    zone.getStatistics().getSatisfaction().getFreeWorkplaceEffect() - COMMERCIAL_ZONE_BASE_EFFECT);
        }
    }

    @Override
    public boolean condition(Zone zone, GameModel gm) {
        double manhattanDist = new PathFinder(gm.getMap()).manhattanDistance(zone, gm.getMasterRoads().get(0));
        return manhattanDist != -1 && zone.getCitizens().size() < 10 && manhattanDist < effectRadius;
    }
}
