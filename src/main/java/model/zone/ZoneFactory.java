package model.zone;

import model.GameModel;
import model.util.Coordinate;

public abstract class ZoneFactory {
    protected GameModel gm;

    public ZoneFactory(GameModel gm) {
        this.gm = gm;
    }

    public abstract Zone createZone(Coordinate coordinate);
}
