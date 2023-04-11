package model.facility;

import model.util.BuildableType;
import model.common.Coordinate;
import model.common.Dimension;

public class Road extends Facility {

    public Road(int oneTimeCost, int maintenanceFee, Coordinate coordinate, Dimension dimension) {
        super(oneTimeCost, maintenanceFee, coordinate, dimension);
    }

    @Override
    public BuildableType getBuildableType() {
        return BuildableType.ROAD;
    }
}
