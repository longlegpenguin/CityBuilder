package model;

public final class ZoneStatistics {
    private int population;
    private int capacity;
    private final Satisfaction satisfaction;

    public ZoneStatistics(int population, int capacity, Satisfaction satisfaction) {
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

    public Satisfaction getSatisfaction() {
        return satisfaction;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

}
