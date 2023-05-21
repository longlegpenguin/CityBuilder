package model.zone;

import model.common.ZoneSatisfaction;

public final class ZoneStatistics implements java.io.Serializable {
    private int population;
    private final ZoneSatisfaction satisfaction;

    public ZoneStatistics(int population, ZoneSatisfaction satisfaction) {
        this.population = population;
        this.satisfaction = satisfaction;
    }

    public int getPopulation() {
        return population;
    }

    public ZoneSatisfaction getSatisfaction() {
        return satisfaction;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

}
