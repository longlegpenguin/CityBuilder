package model.facility;

import model.common.Coordinate;
import model.common.Dimension;
import model.util.BuildableType;

public class School extends Education {

    public School(int oneTimeCost, int maintenanceFee, Coordinate coordinate, Dimension dimension) {
        super(oneTimeCost, maintenanceFee, coordinate, dimension, 8000, 12);
    }

    @Override
    public BuildableType getBuildableType() {
        return BuildableType.SCHOOL;
    }

    @Override
    public boolean isUnderConstruction() { return false; }
}