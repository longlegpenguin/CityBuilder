package model.facility;

import model.common.Coordinate;
import model.common.Dimension;
import model.util.BuildableType;
import model.util.LevelOfEducation;

public class University extends Education {

    public University(int oneTimeCost, int maintenanceFee, Coordinate coordinate, Dimension dimension) {
        super(oneTimeCost, maintenanceFee, coordinate, dimension, 5, 4, LevelOfEducation.UNIVERSITY);
    }

    @Override
    public BuildableType getBuildableType() {
        return BuildableType.UNIVERSITY;
    }

    @Override
    public boolean isUnderConstruction() {
        return false;
    }
}
