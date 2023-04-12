package model.city;

import model.common.Budget;
import model.common.Citizen;
import model.facility.Facility;
import model.zone.Zone;

import java.util.ArrayList;
import java.util.LinkedList;

public class CityRegistry {

    private ArrayList<Facility> facilities;
    private ArrayList<Zone> zones;
    private LinkedList<Double> taxRatePast20Years;
    private CityStatistics cityStatistics; // TODO (I need it to be here for game model)

    public CityRegistry() {
        this.facilities = new ArrayList<>();
        this.zones = new ArrayList<>();
        this.taxRatePast20Years = new LinkedList<>();
        this.cityStatistics = new CityStatistics((float) 0.3); // TODO ??
    }

    public CityStatistics getCityStatistics() {
        return cityStatistics;
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

    public ArrayList<Facility> getFacilities() {
        return facilities;
    }

    public ArrayList<Zone> getZones() {
        return zones;
    }

    public void registerCitizenToZone(Citizen citizen, Zone zone) {
        zone.addCitizen(citizen);
    }

    public void deregisterCitizen(Citizen citizen, Zone zone) {
        zone.unregisterCitizen(citizen);
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
        cityStatistics.updateNrZones(this);
    }

    public void removeZone(Zone zone) {
        zones.remove(zone);
        cityStatistics.updateNrZones(this);
    }

    public void addFacility(Facility facility) {
        facilities.add(facility);
    }

    public void removeFacility(Facility facility) {
        facilities.remove(facility);
    }

    // TODO
    public void deductBalance(int amount) {
    }

    // TODO
    public void addMaintenanceFee(int amount) {
    }

    // TODO
    public void addBalance(double v) {
    }

    //TODO
    public Budget getBudget() {
        return null;
    }
}
