package controller.util;

public enum TimeMode {
    PAUSE(0),
    DAILY(1),
    WEEKLY(7),
    MONTHLY(30);

    private final int multiplier;

    TimeMode(int multiplier) {
        this.multiplier = multiplier;
    }

    public int getMultiplier() {
        return multiplier;
    }
}
