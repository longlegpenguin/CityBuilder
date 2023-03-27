package model;

public final class Statistics {
    private static Statistics INSTANCE = null;
    private int population;
    private int capacity;
    private float satisfaction;

    private Statistics(int population, int capacity, float satisfaction) {

        this.population = population;
        this.capacity = capacity;
        this.satisfaction = satisfaction;
    }

    /**
     * Created due to Statistics singleton design pattern.
     *
     * @param population current population
     * @param capacity capacity
     * @param satisfaction level of satisfaction
     * @return instance of Statistics
     */
    public static Statistics getInstance(int population, int capacity, float satisfaction) {
        if (population < 0 && capacity < 0) throw new IllegalArgumentException("Invalid input");
        if (INSTANCE == null) {
            INSTANCE = new Statistics(population, capacity, satisfaction);
        }
        return INSTANCE;
    }

    public int getPopulation() {
        return population;
    }

    public int getCapacity() {
        return capacity;
    }

    public float getSatisfaction() {
        return satisfaction;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setSatisfaction(float satisfaction) {
        this.satisfaction = satisfaction;
    }
}
