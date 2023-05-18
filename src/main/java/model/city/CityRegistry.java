package model.city;

import model.common.Citizen;
import model.facility.Facility;
import model.util.BuildableType;
import model.zone.Zone;

import java.util.ArrayList;

public class CityRegistry implements java.io.Serializable {

    private final ArrayList<Facility> facilities;
    private final ArrayList<Zone> zones;
    private final CityStatistics cityStatistics;

    public CityRegistry(CityStatistics cityStatistics) {
        this.facilities = new ArrayList<>();
        this.zones = new ArrayList<>();
        this.cityStatistics = cityStatistics;
    }

    public CityStatistics getCityStatistics() {
        return cityStatistics;
    }

    public ArrayList<Facility> getFacilities() {
        return facilities;
    }

    public ArrayList<Zone> getZones() {
        return zones;
    }

    /**
     * @return list of all citizens from the city.
     */
    public ArrayList<Citizen> getAllCitizens() {
        ArrayList<Citizen> citizens = new ArrayList<>();
        for (Zone zone : getZones()) {
            if (zone.getBuildableType() == BuildableType.RESIDENTIAL) {
                citizens.addAll(zone.getCitizens());
            }
        }
        return citizens;
    }

    /**
     * Adding a zone to the collection and updating the nr of zones of this type in cityStatistics.
     *
     * @param zone to be added
     */
    public void addZone(Zone zone) {
        zones.add(zone);
        cityStatistics.updateNrZones(this);
    }

    /**
     * Removing a zone from the collection and updating the nr of zones of this type in cityStatistics.
     *
     * @param zone to be removed
     */
    public void removeZone(Zone zone) {
        zones.remove(zone);
        cityStatistics.updateNrZones(this);
    }

    /**
     * Adding a facility to the collection.
     *
     * @param facility to be added
     */
    public void addFacility(Facility facility) {
        facilities.add(facility);
    }

    /**
     * Removing facility from the collection.
     *
     * @param facility to be removed
     */
    public void removeFacility(Facility facility) {
        facilities.remove(facility);
    }

    /**
     * Add given amount to budget
     *
     * @param amount int
     */
    public void updateBalance(double amount) {
        cityStatistics.getBudget().addBalance(amount);
    }

    /**
     * Set new tax rate
     *
     * @param newTaxRate double
     */
    public void updateTaxRate(double newTaxRate) {
        cityStatistics.getBudget().setTaxRate(newTaxRate);
    }

}
