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
    private int age;


    private int pension;

    public Citizen(Zone workplace, Zone livingplace, LevelOfEducation levelOfEducation) {
        this.workplace = workplace;
        this.livingplace = livingplace;
        this.levelOfEducation = levelOfEducation;
        pension = 0;
        age = 0;
    }

    public int getDistanceLiveWork() {
        return new PathFinder(GameModel.map).manhattanDistance(livingplace, workplace);
    }

    public double getSatisfaction() {
        return getDistanceLiveWork() > 5 ? 0 : 1 + (workplace.getSatisfaction()
                + livingplace.getSatisfaction()) / 2;
    }

    public Zone getWorkplace() {
        return workplace;
    }

    public Zone getLivingplace() {
        return livingplace;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void incAge() {
        age++;
    }
    public void setPension(int pension) {
        this.pension = pension;
    }

    public int getPension() {
        return pension;
    }

}
