package model.facility;

import model.common.SideEffect;
import model.common.Coordinate;
import model.common.Dimension;

public abstract class EffectualFacility extends Facility implements SideEffect {

    protected float influenceRadius;

    public EffectualFacility(int oneTimeCost, int maintenanceFee, Coordinate coordinate, Dimension dimension, float influenceRadius) {
        super(oneTimeCost, maintenanceFee, coordinate, dimension);
        this.influenceRadius = influenceRadius;
    }

    @Override
    public String toString() {
        return "EffectualFacility{" +
                "oneTimeCost=" + oneTimeCost +
                ", maintenanceFee=" + maintenanceFee +
                ", coordinate=" + coordinate.toString() +
                ", dimension=" + dimension.toString() +
                ", type=" + getBuildableType() +
                "influenceRadius=" + influenceRadius +
                '}';
    }
}
