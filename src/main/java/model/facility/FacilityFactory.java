package model.facility;

import model.GameModel;
import model.common.Coordinate;
import model.common.Dimension;

public abstract class FacilityFactory {
    protected GameModel gm;

    public FacilityFactory(GameModel gm) {
        this.gm = gm;
    }

    public abstract Facility createFacility(int oneTimeCost, int maintenanceFee,
                                            Coordinate coordinate, Dimension dimension);
}