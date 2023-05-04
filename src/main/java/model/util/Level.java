package model.util;

import model.common.Constants;

public enum Level {
    CONSTRUCTING(0, Constants.RESIDENTIAL_ONE_TIME_COST),
    ONE(10, 500),
    TWO(20, 1000),
    THREE(30, 2000);

    final int capacity;
    final int cost;

    Level(int capacity, int cost) {
        this.capacity = capacity;
        this.cost = cost;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCost() {
        return cost;
    }
}
