package model;

import model.facility.Facility;
import model.util.BuildableType;
import model.util.Coordinate;
import model.util.Dimension;

public class Road extends Facility {

    public Road(int oneTimeCost, int maintenanceFee, Coordinate coordinate, Dimension dimension) {
        super(oneTimeCost, maintenanceFee, coordinate, dimension);
    }

    @Override
    public BuildableType getBuildableType() {
        return BuildableType.ROAD;
    }
}
