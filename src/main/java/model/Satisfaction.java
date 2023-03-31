package model;

public class Satisfaction {
    private int policeEffect;
    private int taxEffect;
    private float indComZoneBalance;
    private float budgetEffect;
    private int freeWorkplaceEffect;
    private int stadiumEffect;
    private int industrialEffect;
    private int citizenAvgEffect;

    public Satisfaction() {
        this.policeEffect = 0;
        this.taxEffect = 0;
        this.indComZoneBalance = 0;
        this.budgetEffect = 0;
        this.freeWorkplaceEffect = 0;
        this.stadiumEffect = 0;
        this.industrialEffect = 0;
        this.citizenAvgEffect = 0;
    }

    public int getPoliceEffect() {
        return policeEffect;
    }

    public int getTaxEffect() {
        return taxEffect;
    }

    public float getZoneBalanceEffect() {
        return indComZoneBalance;
    }

    public float getBudgetEffect() {
        return budgetEffect;
    }

    public int getFreeWorkplaceEffect() {
        return freeWorkplaceEffect;
    }

    public int getStadiumEffect() {
        return stadiumEffect;
    }

    public int getIndustrialEffect() {
        return industrialEffect;
    }

    public int getCitizenAvgEffect() {
        return citizenAvgEffect;
    }

    public void setPoliceEffect(int policeEffect) {
        this.policeEffect = policeEffect;
    }

    public void setTaxEffect(int taxEffect) {
        this.taxEffect = taxEffect;
    }

    public void setZoneBalanceEffect(int indComZoneBalance) {
        this.indComZoneBalance = indComZoneBalance;
    }

    public void setBudgetEffect(int budgetEffect) {
        this.budgetEffect = budgetEffect;
    }

    public void setFreeWorkplaceEffect(int freeWorkplaceEffect) {
        this.freeWorkplaceEffect = freeWorkplaceEffect;
    }

    public void setStadiumEffect(int stadiumEffect) {
        this.stadiumEffect = stadiumEffect;
    }

    public void setIndustrialEffect(int industrialEffect) {
        this.industrialEffect = industrialEffect;
    }

    public void setCitizenAvgEffect(int citizenAvgEffect) {
        this.citizenAvgEffect = citizenAvgEffect;
    }

    /**
     *
     * @return total satisfaction
     */
    public float getSatisfaction() {
        return policeEffect + taxEffect + indComZoneBalance + budgetEffect + freeWorkplaceEffect + stadiumEffect + industrialEffect + citizenAvgEffect;
    }

}
