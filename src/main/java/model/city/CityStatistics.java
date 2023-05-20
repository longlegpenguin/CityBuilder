package model.city;

import model.GameModel;
import model.common.Budget;
import model.common.Citizen;
import model.util.Date;
import model.util.LevelOfEducation;
import model.zone.CommercialZone;
import model.zone.IndustrialZone;
import model.zone.ResidentialZone;
import model.zone.Zone;

public class CityStatistics implements java.io.Serializable {
    private int nrCommercialZones;
    private int nrIndustrialZones;
    private final Budget budget;
    private double citySatisfaction;

    public CityStatistics(Budget budget) {
        this.budget = budget;
        this.nrCommercialZones = 0;
        this.nrIndustrialZones = 0;
        this.citySatisfaction = 0;
    }

    public double getCityRelatedSatisfaction(Date now) {
        return getTaxEffect()
                + getIndComZoneBalance()
                + getBudgetEffect(now);
    }

    public Budget getBudget() {
        return budget;
    }

    /**
     * Get city population.
     *
     * @param cityRegistry used to get the zones
     * @return number of citizens.
     */
    public int getPopulation(CityRegistry cityRegistry) {
        return cityRegistry.getAllCitizens().size();
    }

    public double getCitySatisfaction() {
        if (citySatisfaction > 100) {
            return 100;
        }
        return citySatisfaction < 0 ? 0 : citySatisfaction;
    }

    public double getTaxEffect() {
        return 1 / budget.getTaxRate();
    }

    public double getIndComZoneBalance() {
        int diff = Math.abs(nrIndustrialZones - nrCommercialZones);
        return 1.0 / (diff == 0 ? 1.0 : diff);
    }

    public double getBudgetEffect(Date now) {
        int negYears = budget.getNegativeYears(now);
        if (negYears != 0) {
            double sizeOfLoan = getBudget().getBalance();
            return - (Math.log10(-sizeOfLoan) + 1) - negYears;
        }
        return 0;
    }

    /**
     * Setting city average satisfaction being equal to the sum of the average satisfaction of zones
     * and of the average of some additional effects which are related to the whole city (avgCommonZoneSatisfaction).
     *
     * @param gm used to get the cityRegistry
     */
    public void setCitySatisfaction(GameModel gm) {
        double sumZoneSatisfaction = 0;
        for (Zone zone : gm.getCityRegistry().getZones()) {
            sumZoneSatisfaction += zone.getZoneSatisfaction(gm);
        }
        int zoneCount =  (gm.getCityRegistry().getZones().size());
        if (zoneCount == 0) {
            this.citySatisfaction = 60 + getCityRelatedSatisfaction(gm.getCurrentDate());
        } else {
            this.citySatisfaction = ((float)sumZoneSatisfaction /(float) zoneCount);
        }
    }

    /**
     * Update the number of zones of each type.
     *
     * @param cityRegistry to get the zones
     */
    public void updateNrZones(CityRegistry cityRegistry) {
        this.nrCommercialZones = (int) cityRegistry.getZones().stream().filter(z -> z.getClass()
                .equals(CommercialZone.class)).count();
        this.nrIndustrialZones = (int) cityRegistry.getZones().stream().filter(z -> z.getClass()
                .equals(IndustrialZone.class)).count();
    }

    /**
     * Get the number of citizens who have a secondary level of education (school).
     *
     * @param cityRegistry to get the collection of citizens.
     * @return nr of citizens who have a secondary level of education (school).
     */
    public int getNrCitizenSecondaryEducation(CityRegistry cityRegistry) {
        int nrCitizenSecondaryEducation = 0;
        for (Citizen citizen : cityRegistry.getAllCitizens()) {
            if (citizen.getLevelOfEducation() == LevelOfEducation.SCHOOL) nrCitizenSecondaryEducation += 1;
        }
        return nrCitizenSecondaryEducation;
    }

    /**
     * Get the number of citizens who have a higher level of education (university).
     *
     * @param cityRegistry to get the collection of citizens.
     * @return nr of citizens who have a higher level of education (university).
     */
    public int getNrCitizenHigherEducation(CityRegistry cityRegistry) {
        int nrCitizenHigherEducation = 0;
        for (Citizen citizen : cityRegistry.getAllCitizens()) {
            if (citizen.getLevelOfEducation() == LevelOfEducation.UNIVERSITY) nrCitizenHigherEducation += 1;
        }
        return nrCitizenHigherEducation;
    }

}
