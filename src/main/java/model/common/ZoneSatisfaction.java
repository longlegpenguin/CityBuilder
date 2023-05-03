package model.common;

public class ZoneSatisfaction {
    private int policeEffect;
    private int freeWorkplaceEffect;
    private int stadiumEffect;
    private int noIndustrialEffect;
    private double forestEffect;

    public ZoneSatisfaction() {
        this.policeEffect = 0;
        this.freeWorkplaceEffect = 0;
        this.stadiumEffect = 0;
        this.noIndustrialEffect = 1;
        this.forestEffect = 0;
    }

    public int getPoliceEffect() {
        return policeEffect;
    }

    public int getFreeWorkplaceEffect() {
        return freeWorkplaceEffect;
    }

    public int getStadiumEffect() {
        return stadiumEffect;
    }

    public int getIndustrialEffect() {
        return noIndustrialEffect;
    }

    public double getForestEffect() {
        return forestEffect;
    }

    public void setPoliceEffect(int policeEffect) {
        this.policeEffect = policeEffect;
    }

    public void setFreeWorkplaceEffect(int freeWorkplaceEffect) {
        this.freeWorkplaceEffect = freeWorkplaceEffect;
    }

    public void setStadiumEffect(int stadiumEffect) {
        this.stadiumEffect = stadiumEffect;
    }

    public void setIndustrialEffect(int noIndustrialEffect) {
        this.noIndustrialEffect = noIndustrialEffect;
    }

    public void setForestEffect(double forestEffect) {
        this.forestEffect = forestEffect;
    }

    /**
     * @return total satisfaction for a zone
     */
    public double getTotalSatisfaction() {
        return policeEffect + freeWorkplaceEffect + stadiumEffect + noIndustrialEffect + forestEffect;
    }

    @Override
    public String toString() {
        return "Police effect: " + policeEffect +
                ", freeWorkplaceEffect: " + freeWorkplaceEffect +
                ", stadiumEffect: " + stadiumEffect +
                ", noIndustrialEffect: " + noIndustrialEffect;
    }
}
