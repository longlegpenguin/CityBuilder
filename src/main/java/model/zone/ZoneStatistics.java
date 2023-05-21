package model.zone;

import model.common.ZoneSatisfaction;

public final class ZoneStatistics implements java.io.Serializable {
    private int population;
    private int capacity;
    private final ZoneSatisfaction satisfaction;

    public ZoneStatistics(int population, int capacity, ZoneSatisfaction satisfaction) {
        this.population = population;
        this.capacity = capacity;
        this.satisfaction = satisfaction;
    }

    public int getPopulation() {
        return population;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public ZoneSatisfaction getSatisfaction() {
        return satisfaction;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

}
