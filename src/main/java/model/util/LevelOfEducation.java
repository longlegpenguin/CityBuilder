package model.util;

public enum LevelOfEducation {
    PRIMARY(0),
    SCHOOL(300),
    UNIVERSITY(400);

    private final int additionalValue;

    public int getAdditionalValue() {
        return additionalValue;
    }

    LevelOfEducation(int additionalValue) {
        this.additionalValue = additionalValue;
    }
}
