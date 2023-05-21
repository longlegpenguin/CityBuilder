package model.common;

public class Constants implements java.io.Serializable {
    public static final int BASE_TAX = 100;
    public static final int INITIAL_CITY_BALANCE = 1000000;
    public static final double RETURN_RATE = 0.9;
    public static final int DAYS_FOR_CONSTRUCTION = 3;

    // ONE TIME COSTS
    public static final int ZONE_ONE_TIME_COST = 1000;
    public static final int STADIUM_ONE_TIME_COST = 10000;
    public static final int FOREST_ONE_TIME_COST = 10000;
    public static final int POLICE_ONE_TIME_COST = 10000;
    public static final int ROAD_ONE_TIME_COST = 2000;
    public static final int SCHOOL_ONE_TIME_COST = 3000;
    public static final int UNIVERSITY_ONE_TIME_COST = 5000;

    // MAINTENANCE FEES
    public static final int STADIUM_MAINTENANCE_FEE = 1000;
    public static final int FOREST_MAINTENANCE_FEE = 1000;
    public static final int POLICE_MAINTENANCE_FEE = 1000;
    public static final int ROAD_MAINTENANCE_FEE = 1000;
    public static final int SCHOOL_MAINTENANCE_FEE = 1500;
    public static final int UNIVERSITY_MAINTENANCE_FEE = 2500;

    // BASE EFFECTS
    public static final int INDUSTRIAL_ZONE_BASE_EFFECT = 1;
    public static final int COMMERCIAL_ZONE_BASE_EFFECT = 1;
    public static final int STADIUM_BASE_EFFECT = 1;
    public static final double FOREST_BASE_EFFECT = 1;
    public static final int POLICE_BASE_EFFECT = 1;

    // RADIUS EFFECT
    public static final int INDUSTRIAL_EFFECT_RADIUS = 5;
    public static final int COMMERCIAL_EFFECT_RADIUS = 5;
    public static final int RESIDENTIAL_EFFECT_RADIUS = 0;
    public static final int STADIUM_EFFECT_RADIUS = 5;
    public static final int FOREST_EFFECT_RADIUS = 3;
    public static final int POLICE_EFFECT_RADIUS = 5;
    public static final int CITIZEN_LEAVING_SATISFACTION = 50;
    public static final int GAME_LOST_SATISFACTION = 40;

}
