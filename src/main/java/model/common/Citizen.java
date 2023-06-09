package model.common;

import model.GameModel;
import model.util.LevelOfEducation;
import model.util.PathFinder;
import model.zone.Zone;

import java.util.LinkedList;
import java.util.Random;

public class Citizen implements java.io.Serializable {
    private Zone workplace;
    private final Zone livingPlace;
    private LevelOfEducation levelOfEducation;
    private int pension;
    private int age;
    private boolean isUnemployed;

    private final LinkedList<Double> taxPaidPast20Years;

    public Citizen(Zone workplace, Zone livingPlace, LevelOfEducation levelOfEducation) {
        this.workplace = workplace;
        this.livingPlace = livingPlace;
        this.levelOfEducation = levelOfEducation;
        pension = 0;
        age = new Random().nextInt(42) + 18;
        isUnemployed = workplace == null;
        taxPaidPast20Years = new LinkedList<>();
    }

    public double getSatisfaction(GameModel gm) {
        if (workplace == null) {
            return livingPlace.getZoneSatisfaction(gm);
        }
        double distanceEffect = new PathFinder(gm.getMap()).manhattanDistance(workplace, livingPlace);
        return (workplace.getZoneSatisfaction(gm) +
                livingPlace.getZoneSatisfaction(gm)) / 2.0 -
                distanceEffect;
    }

    public double getTax(double taxRate) {
        return Constants.BASE_TAX * taxRate + levelOfEducation.getAdditionalValue();
    }

    /**
     * Pays the tax and records the pay
     *
     * @param taxRate the current tax rate
     * @return the tax to be paid by the person
     */
    public double payTax(double taxRate) {
        double tax = Constants.BASE_TAX * taxRate + levelOfEducation.getAdditionalValue();
        addPaidTax(tax);
        return tax;
    }

    /**
     * Getting distance between living place and working place.
     *
     * @param gm          gameModel
     * @param livingPlace living place of the citizen
     * @param workplace   work place of the citizen
     * @return distance from living place to working place
     */
    public static int getDistanceLiveWork(GameModel gm, Zone livingPlace, Zone workplace) {
        return new PathFinder(gm.getMap()).manhattanDistance(livingPlace, workplace);
    }

    public Zone getWorkplace() {
        return workplace;
    }

    public Zone getLivingPlace() {
        return livingPlace;
    }

    public LevelOfEducation getLevelOfEducation() {
        return levelOfEducation;
    }

    public int getPension() {
        retire();
        return pension;
    }

    public int getAge() {
        return age;
    }

    public void setWorkplace(GameModel gm, Zone workplace) {
        this.workplace = workplace;
        setIsUnemployed(false);
        workplace.addCitizen(this, gm);
    }

    public void setLevelOfEducation(LevelOfEducation levelOfEducation) {
        this.levelOfEducation = levelOfEducation;
    }

    public void setIsUnemployed(boolean isUnemployed) {
        this.isUnemployed = isUnemployed;
    }

    public void incAge() {
        age++;
    }

    public boolean isUnemployed() {
        return this.isUnemployed;
    }

    public void retire() {
        pension = (int) getPast20AvgIncome();
    }

    public void addPaidTax(double newTax) {
        if (taxPaidPast20Years.size() == 20) {
            taxPaidPast20Years.addLast(newTax);
            taxPaidPast20Years.removeFirst();
        } else {
            taxPaidPast20Years.addLast(newTax);
        }
    }

    /**
     * @return average income of the last 20 years.
     */
    public double getPast20AvgIncome() {
        double sumTax = 0;
        int cnt = 0;
        for (Double taxRatePast20Year : taxPaidPast20Years) {
            sumTax += taxRatePast20Year;
            cnt += 1;
        }
        return sumTax / cnt;
    }

    @Override
    public String toString() {
        return "Citizen{" +
                "workplace=" + workplace +
                ", livingPlace=" + livingPlace +
                ", levelOfEducation=" + levelOfEducation +
                ", pension=" + pension +
                ", age=" + age +
                ", isUnemployed=" + isUnemployed +
                ", taxPaidPast20Years=" + getPast20AvgIncome() +
                '}';
    }
}
