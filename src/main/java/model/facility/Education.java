package model.facility;

import model.common.Citizen;
import model.common.Coordinate;
import model.common.Dimension;
import model.util.LevelOfEducation;

import java.util.ArrayList;
import java.util.List;

public abstract class Education extends Facility{

    protected int capacity;
    protected int yearsToGraduate;

    protected LevelOfEducation levelOfEducation;
    protected List<Citizen> students;

    public Education(int oneTimeCost, int maintenanceFee, Coordinate coordinate, Dimension dimension, int capacity,
                     int yearsToGraduate, LevelOfEducation levelOfEducation) {
        super(oneTimeCost, maintenanceFee, coordinate, dimension);
        this.capacity = capacity;
        this.yearsToGraduate = yearsToGraduate;
        this.levelOfEducation = levelOfEducation;
        students = new ArrayList<>();
    }

    public int getCapacity() {
        return capacity;
    }

    public int getYearsToGraduate() {
        return yearsToGraduate;
    }

    public int getAdditionalValue() {
        return students.size() * levelOfEducation.getAdditionalValue();
    }
}