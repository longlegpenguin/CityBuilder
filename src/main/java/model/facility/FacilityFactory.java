package model.facility;

import model.GameModel;
import model.common.Coordinate;

public abstract class FacilityFactory {
    protected GameModel gm;

    public FacilityFactory(GameModel gm) {
        this.gm = gm;
    }

    public abstract Facility createFacility(Coordinate coordinate);
}