package model.facility;

import model.common.Coordinate;
import model.common.Dimension;

abstract class Education extends Facility{

    protected int capacity;
    protected int yearsToGraduate;

    public Education(int oneTimeCost, int maintenanceFee, Coordinate coordinate, Dimension dimension, int capacity, int yearsToGraduate) {
        super(oneTimeCost, maintenanceFee, coordinate, dimension);
        this.capacity = capacity;
        this.yearsToGraduate = yearsToGraduate;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getYearsToGraduate() {
        return yearsToGraduate;
    }
}
