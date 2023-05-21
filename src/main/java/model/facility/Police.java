package model.facility;

import model.GameModel;
import model.common.Coordinate;
import model.common.Dimension;
import model.util.BuildableType;
import model.util.PathFinder;
import model.zone.Zone;

import static model.common.Constants.POLICE_BASE_EFFECT;

public class Police extends EffectualFacility {

    public Police(int oneTimeCost, int maintenanceFee, Coordinate coordinate, Dimension dimension, float influenceRadius) {
        super(oneTimeCost, maintenanceFee, coordinate, dimension, influenceRadius);
    }

    @Override
    public BuildableType getBuildableType() {
        return BuildableType.POLICE;
    }

    @Override
    public boolean isUnderConstruction() {
        return false;
    }

    @Override
    public void effect(Zone zone, GameModel gm) {
        if (condition(zone, gm)) {
            zone.updatePoliceEffect(zone.getStatistics().getSatisfaction().getPoliceEffect() + POLICE_BASE_EFFECT);
            System.out.println("Police effect: " + zone.getStatistics().getSatisfaction().getPoliceEffect());
        }
    }

    @Override
    public void reverseEffect(Zone zone, GameModel gm) {
        if (condition(zone, gm)) {
            zone.updatePoliceEffect(zone.getStatistics().getSatisfaction().getPoliceEffect() - POLICE_BASE_EFFECT);
        }
    }

    @Override
    public boolean condition(Zone zone, GameModel gm) {
        double manhattanDist = new PathFinder(gm.getMap()).manhattanDistance(zone, this);
        return manhattanDist <= influenceRadius && manhattanDist != -1;
    }
}