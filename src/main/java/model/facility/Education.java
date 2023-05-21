package model.facility;

import model.common.Coordinate;
import model.common.Dimension;
import model.util.LevelOfEducation;

public abstract class Education extends Facility {

    protected int capacity;
    protected int yearsToGraduate;
    protected LevelOfEducation levelOfEducation;

    public Education(int oneTimeCost, int maintenanceFee, Coordinate coordinate, Dimension dimension, int capacity,
                     int yearsToGraduate, LevelOfEducation levelOfEducation) {
        super(oneTimeCost, maintenanceFee, coordinate, dimension);
        this.capacity = capacity;
        this.yearsToGraduate = yearsToGraduate;
        this.levelOfEducation = levelOfEducation;
    }

    public int getCapacity() {
        return capacity;
    }
}
