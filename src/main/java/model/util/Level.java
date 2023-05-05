package model.util;

import model.common.Constants;

import static model.common.Constants.ZONE_ONE_TIME_COST;

public enum Level {
    CONSTRUCTING(0, ZONE_ONE_TIME_COST),
    ONE(10, 0);

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
