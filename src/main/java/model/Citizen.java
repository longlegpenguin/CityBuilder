package model;

import model.zone.Zone;

public class Citizen {
    private Zone workplace;
    private Zone livingplace;

    private float satisfaction;
    public int getDistance() {
        //find(map, livingplace, workplace);
        return 0;
    }
    public float getSatisfaction() {
        return getDistance() > 5 ? 0 : 1 + (workplace.getSatisfaction().getTotalSatisfaction()
                                         + livingplace.getSatisfaction().getTotalSatisfaction()) / 2;
    }

}
