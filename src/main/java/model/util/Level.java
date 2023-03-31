package model.util;

public enum Level {
    CONSTRUCTING(0),
    ONE(10),
    TWO(20),
    THREE(30);

    final int capacity;

    private Level(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}
