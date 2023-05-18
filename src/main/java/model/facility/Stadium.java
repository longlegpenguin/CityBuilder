package model.facility;

import model.GameModel;
import model.common.Coordinate;
import model.common.Dimension;
import model.util.BuildableType;
import model.util.PathFinder;
import model.zone.Zone;

import static model.common.Constants.STADIUM_BASE_EFFECT;

public class Stadium extends EffectualFacility {

    public Stadium(int oneTimeCost, int maintenanceFee, Coordinate coordinate, Dimension dimension, float influenceRadius) {
        super(oneTimeCost, maintenanceFee, coordinate, dimension, influenceRadius);
    }

    @Override
    public BuildableType getBuildableType() {
        return BuildableType.STADIUM;
    }

    @Override
    public boolean isUnderConstruction() {
        return false;
    }

    @Override
    public void effect(Zone zone, GameModel gm) {
        System.out.println(condition(zone, gm));
        if (condition(zone, gm)) {
            zone.updateStadiumEffect(zone.getStatistics().getSatisfaction().getStadiumEffect() + STADIUM_BASE_EFFECT);
        }
    }

    @Override
    public void reverseEffect(Zone zone, GameModel gm) {
        if (condition(zone, gm)) {
            zone.updateStadiumEffect(zone.getStatistics().getSatisfaction().getStadiumEffect() - STADIUM_BASE_EFFECT);
        }
    }

    @Override
    public boolean condition(Zone zone, GameModel gm) {
        double manhattanDist = new PathFinder(gm.getMap()).manhattanDistance(zone, this);
        return manhattanDist <= influenceRadius && manhattanDist != -1;
    }
}
