package model.city;

import model.common.Citizen;
import model.facility.Facility;
import model.zone.Zone;

import java.util.ArrayList;

public class CityRegistry {

    private ArrayList<Facility> facilities;
    private ArrayList<Zone> zones;
    private CityStatistics cityStatistics;

    public CityRegistry(CityStatistics cityStatistics) {
        this.facilities = new ArrayList<>();
        this.zones = new ArrayList<>();
        this.cityStatistics = cityStatistics;
    }

    public ArrayList<Facility> getFacilities() {
        return facilities;
    }

    public ArrayList<Zone> getZones() {
        return zones;
    }

    public CityStatistics getCityStatistics() {
        return cityStatistics;
    }

    public void registerCitizenToZone(Citizen citizen, Zone zone) {
        zone.addCitizen(citizen);
    }

    public void deregisterCitizen(Citizen citizen, Zone zone) {
        zone.unregisterCitizen(citizen);
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

}
