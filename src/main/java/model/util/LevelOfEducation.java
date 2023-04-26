package model.util;

public enum LevelOfEducation {
    SCHOOL(300),
    UNIVERSITY(400);

    private final int additionalValue;

    public int getAdditionalValue() {
        return additionalValue;
    }

    private LevelOfEducation(int additionalValue) {
        this.additionalValue = additionalValue;
    }
}
