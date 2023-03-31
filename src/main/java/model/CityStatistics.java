package model;

import model.zone.Zone;

public class CityStatistics {
    private float tax_rate;
    private int nrCommercialZones;
    private int nrIndustrialZones;
    private int nrResidentialZones;
    private Budget budget;
    private float citizenSatisfaction;

    public CityStatistics(float tax_rate, Budget budget){
        this.tax_rate = tax_rate;
        this.budget = budget;
        this.nrCommercialZones = 0;
        this.nrIndustrialZones = 0;
        this.nrResidentialZones = 0;
        this.citizenSatisfaction = 0;
    }

    public int getNrCommercialZones() {
        return nrCommercialZones;
    }

    public int getNrIndustrialZones() {
        return nrIndustrialZones;
    }

    public int getNrResidentialZones() {
        return nrResidentialZones;
    }

    public Budget getBudget() {
        return budget;
    }

    public float getTaxRate() {
        return tax_rate;
    }

    public void setCitizenSatisfaction(CityRegistry cityRegistry){
        float totalCitizenSatisf = 0;
        int nrZones = 0;
        for (Zone z : cityRegistry.getZones()){
            totalCitizenSatisf += z.getSatisfaction();
            nrZones += 1;
        }

        this.citizenSatisfaction =  totalCitizenSatisf / nrZones;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public void setTax_rate(float tax_rate) {
        this.tax_rate = tax_rate;
    }

    public void setNrCommercialZones(CityRegistry cityRegistry) {
        this.nrCommercialZones = cityRegistry.getCommercialZones().size();
    }

    public void setNrIndustrialZones(CityRegistry cityRegistry) {
        this.nrIndustrialZones = cityRegistry.getIndustrialZones().size();
    }
}
