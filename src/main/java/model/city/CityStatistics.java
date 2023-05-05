package model.city;

import model.GameModel;
import model.common.Budget;
import model.common.Citizen;
import model.util.LevelOfEducation;
import model.zone.CommercialZone;
import model.zone.IndustrialZone;
import model.zone.ResidentialZone;
import model.zone.Zone;

public class CityStatistics implements java.io.Serializable {
    private int nrCommercialZones;
    private int nrIndustrialZones;
    private int nrResidentialZones;
    private final Budget budget;
    private float citySatisfaction;
    private final int taxEffect;
    private double indComZoneBalance;
    private final float budgetEffect;

    public CityStatistics(Budget budget) {
        this.budget = budget;
        this.nrCommercialZones = 0;
        this.nrIndustrialZones = 0;
        this.nrResidentialZones = 0;
        this.taxEffect = 0;
        this.indComZoneBalance = 0;
        this.budgetEffect = 0;
        this.citySatisfaction = 0;
    }

    public int getNrCommercialZones() {
        return nrCommercialZones;
    }

    public int getNrIndustrialZones() {
        return nrIndustrialZones;
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

    public float getCitySatisfaction() {
        return citySatisfaction + 60;
    }

    public int getTaxEffect() {
        return taxEffect;
    }

    public double getIndComZoneBalance() {
        return indComZoneBalance;
    }

    public float getBudgetEffect() {
        return budgetEffect;
    }

    /**
     * Setting the indComZoneBalance to represent the balance between industrial and commercial buildings.
     *
     * @param indComZoneBalance balance between industrial and commercial buildings
     */
    public void setZoneBalanceEffect(double indComZoneBalance) {
        this.indComZoneBalance = indComZoneBalance;
    }

    /**
     * Setting city average satisfaction being equal to the sum of the average satisfaction of zones
     * and of the average of some additional effects which are related to the whole city (avgCommonZoneSatisfaction).
     *
     * @param gm used to get the cityRegistry
     */
    public void setCitySatisfaction(GameModel gm) {
        int sumZoneSatisfaction = 0;
        for (Zone zone : gm.getCityRegistry().getZones()) {
            sumZoneSatisfaction += zone.getZoneSatisfaction(gm);
        }
        float avgZonesSatisfaction = (float) sumZoneSatisfaction / (gm.getCityRegistry().getZones().size() == 0 ? 1 : gm.getCityRegistry().getZones().size());
        this.citySatisfaction = avgZonesSatisfaction;

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
        this.nrResidentialZones = (int) cityRegistry.getZones().stream().filter(z -> z.getClass()
                .equals(ResidentialZone.class)).count();
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
