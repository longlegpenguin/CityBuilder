package model;

import model.facility.Facility;
import model.zone.Zone;

import java.util.ArrayList;
import java.util.LinkedList;

public class CityRegistry {

    private ArrayList<Facility> facilities;
    private ArrayList<Zone> zones;
    private LinkedList<Double> taxRatePast20Years;
    public CityRegistry(){
        this.facilities = new ArrayList<>();
        this.zones = new ArrayList<>();
        this.taxRatePast20Years = new LinkedList<>();
    }
    public double getPast20AvgIncome(){
        double sumTax = 0;
        int cnt = 0;
        for (int i = 0; i < taxRatePast20Years.size(); i++) {
            sumTax += taxRatePast20Years.get(i);
            cnt += 1;
        }
        return sumTax / cnt;
    }
    public void registerCitizenToZone(Citizen c){

    }
    public void deregisterCitizen(Citizen c){}
    public void updateOnConstruction(){}
    public void addZone(Zone zone){}
    public void addFacility(Facility facility){}
}
