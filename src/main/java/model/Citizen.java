package model;

import model.zone.Zone;

public class Citizen {
    private Zone workplace;
    private Zone livingplace;

    private float satisfaction;
    public int getDistance() {
        find(map, livingplace, workplace);
    }
    public int getSatisfaction() {
        return getDistance() > 5 ? 0 : 1 + (workplace.getSatisfaction() + livingplace.getSatisfaction()) / 2;
    }

}
