package model;

import model.util.BuildableType;
import model.util.Level;
import model.zone.Zone;

public class ResidentialZone extends Zone{

    public ResidentialZone(Level level, int dayToBuild, Statistics statistics, Date birthday) {
        super(level, dayToBuild, statistics, birthday);
    }

    @Override
    public BuildableType getBuildableType(){
        return BuildableType.RESIDENTIAL;
    }

}