package model.common;

import model.GameModel;
import model.util.LevelOfEducation;
import model.util.PathFinder;
import model.zone.Zone;

public class Citizen {
    private Zone workplace;
    private Zone livingplace;
    private float satisfaction;
    private LevelOfEducation levelOfEducation;

    public Citizen(Zone workplace, Zone livingplace, LevelOfEducation levelOfEducation) {
        this.workplace = workplace;
        this.livingplace = livingplace;
        this.levelOfEducation = levelOfEducation;
    }

    public int getDistanceLiveWork() {
        return new PathFinder(GameModel.map).manhattanDistance(livingplace, workplace);
    }

    public double getSatisfaction() {
        return getDistanceLiveWork() > 5 ? 0 : 1 + (workplace.getSatisfaction()
                + livingplace.getSatisfaction()) / 2;
    }

}
