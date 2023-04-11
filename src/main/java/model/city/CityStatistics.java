package model.city;

import model.zone.CommercialZone;
import model.zone.IndustrialZone;
import model.zone.ResidentialZone;
import model.zone.Zone;

public class CityStatistics {
    private float tax_rate;
    private int nrCommercialZones;
    private int nrIndustrialZones;
    private int nrResidentialZones;
    //    private Budget budget;
    private int population;
    private float citizenSatisfaction;

    public CityStatistics(float tax_rate) { // Budget budget as param here
        this.tax_rate = tax_rate;
//        this.budget = budget;
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

//    public Budget getBudget() {
//        return budget;
//    }

    public float getTaxRate() {
        return tax_rate;
    }

    public int getPopulation(CityRegistry cityRegistry) {
        int populationCnt = 0;
        for (Zone z : cityRegistry.getZones()) {
            populationCnt += z.getStatistics().getPopulation();
        }
        return populationCnt;
    }

    public void setCitizenSatisfaction(CityRegistry cityRegistry) {
        float totalCitizenSatisf = 0;
        int nrZones = 0;
        for (Zone z : cityRegistry.getZones()) {
            totalCitizenSatisf += z.getStatistics().getSatisfaction().getTotalSatisfaction();
            nrZones += 1;
        }

        this.citizenSatisfaction = totalCitizenSatisf / nrZones;
    }

//    public void updateBudget(double amount) {
//        this.budget = budget.balance + amount;
//    }

    public void setTaxRate(float tax_rate) {
        this.tax_rate = tax_rate;
    }

    public void updateNrZones(CityRegistry cityRegistry) {
        this.nrCommercialZones = (int) cityRegistry.getZones().stream().filter(z -> z.getClass()
                .equals(CommercialZone.class)).count();
        this.nrIndustrialZones = (int) cityRegistry.getZones().stream().filter(z -> z.getClass()
                .equals(IndustrialZone.class)).count();
        this.nrResidentialZones = (int) cityRegistry.getZones().stream().filter(z -> z.getClass()
                .equals(ResidentialZone.class)).count();
    }

    public void setNrCommercialZones(int nr) {
        this.nrCommercialZones = nr;
    }

    public void setNrIndustrialZonesZones(int nr) {
        this.nrIndustrialZones = nr;
    }

    public void setNrResidentialZones(int nr) {
        this.nrResidentialZones = nr;
    }

}
