package model.facility;

import model.common.Coordinate;
import model.common.Dimension;
import model.util.BuildableType;

public class Road extends Facility {

    public Road(int oneTimeCost, int maintenanceFee, Coordinate coordinate, Dimension dimension) {
        super(oneTimeCost, maintenanceFee, coordinate, dimension);
    }

    @Override
    public BuildableType getBuildableType() {
        return BuildableType.ROAD;
    }

    @Override
    public boolean isUnderConstruction() {
        return false;
    }
}
