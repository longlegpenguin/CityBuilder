package model.common;

import model.GameModel;
import model.zone.Zone;

public interface SideEffect {

    /**
     * Applies special effect of the facility on a given zone.
     * if condition is satisfied.
     *
     * @param zone the zone to effect.
     */
    void effect(Zone zone, GameModel gm);

    /**
     * Reverse special effect of the facility on a given zone.
     * if condition is satisfied.
     *
     * @param zone the zone to effect.
     */
    void reverseEffect(Zone zone, GameModel gm);

    /**
     * Evaluates the condition for the facility to have effect on a given zone
     *
     * @param zone the zone to check
     * @return true if condition is satisfied, otherwise, false
     */
    boolean condition(Zone zone, GameModel gm);
}
