package model;

import model.facility.Facility;
import model.zone.Zone;

import java.util.ArrayList;
import java.util.LinkedList;

public class CityRegistry {

    private ArrayList<Facility> facilities;
    private ArrayList<Zone> zones;
    private LinkedList<Double> taxRatePast20Years;

    public CityRegistry() {
        this.facilities = new ArrayList<>();
        this.zones = new ArrayList<>();
        this.taxRatePast20Years = new LinkedList<>();
    }

    public double getPast20AvgIncome() {
        double sumTax = 0;
        int cnt = 0;
        for (Double taxRatePast20Year : taxRatePast20Years) {
            sumTax += taxRatePast20Year;
            cnt += 1;
        }
        return sumTax / cnt;
    }

    public void registerCitizenToZone(Citizen citizen, Zone zone) {
        zone.addCitizen(citizen);
    }

    public void deregisterCitizen(Citizen citizen, Zone zone) {
        zone.unregisterCitizen(citizen);
    }

    public void updateOnConstruction() {
    }

    public void addTaxRate(double newTax) {
        if (taxRatePast20Years.size() == 20) {
            taxRatePast20Years.addLast(newTax);
            taxRatePast20Years.removeFirst();
        } else {
            taxRatePast20Years.addLast(newTax);
        }
    }

    public void addZone(Zone zone) {
        zones.add(zone);
    }

    public void addFacility(Facility facility) {
        facilities.add(facility);
    }
}
