package model;

public interface SideEffect {

    /**
     * Applies special effect of the facility on a given zone.
     * if condition is satisfied.
     * @param zone the zone to effect.
     */
    public void effect(Zone zone);

    /**
     * Evaluates the condition for the facility to have effect on a given zone
     * @param zone the zone to check
     * @return  true if condition is satisfied, otherwise, false
     */
    public boolean condition(Zone zone);
}
