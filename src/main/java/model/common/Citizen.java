package model.common;

import model.GameModel;
import model.util.LevelOfEducation;
import model.util.PathFinder;
import model.zone.Zone;

public class Citizen {
    private Zone workplace;
    private final Zone livingPlace;
    private final LevelOfEducation levelOfEducation;
    private int pension;
    private int age;
    private boolean isUnemployed;

    public Citizen(Zone workplace, Zone livingPlace, LevelOfEducation levelOfEducation) {
        this.workplace = workplace;
        this.livingPlace = livingPlace;
        this.levelOfEducation = levelOfEducation;
        pension = 0;
        age = 18;
        isUnemployed = workplace == null;
    }

    /**
     * Getting distance between living place and working place.
     *
     * @param gm
     * @param livingPlace
     * @param workplace
     * @return
     */
    public static int getDistanceLiveWork(GameModel gm, Zone livingPlace, Zone workplace) {
        return new PathFinder(gm.getMap()).manhattanDistance(livingPlace, workplace);
    }

    public Zone getWorkplace() {
        return workplace;
    }

    public Zone getLivingplace() {
        return livingPlace;
    }

    public LevelOfEducation getLevelOfEducation() {
        return levelOfEducation;
    }

    public int getPension() {
        return pension;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setWorkplace(Zone workplace) {
        this.workplace = workplace;
    }

    public void setIsUnemployed(boolean isUnemployed) {
        this.isUnemployed = isUnemployed;
    }

    public void incAge() {
        age++;
    }

    public void setPension(int pension) {
        this.pension = pension;
    }

    public boolean isUnemployed() {
        return this.isUnemployed;
    }
}
