package model.city;

import model.GameModel;
import model.common.Citizen;
import model.common.Constants;
import model.common.HumanManufacture;
import model.util.ProbabilitySelector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SocialSecurity implements java.io.Serializable {

    CityRegistry cityRegistry;

    public SocialSecurity(CityRegistry cityRegistry) {
        this.cityRegistry = cityRegistry;
    }

    /**
     * Dies existing citizens and aging them.
     */
    public void census(GameModel gm) {

        for (Citizen c : cityRegistry.getAllCitizens()) {
            c.incAge();
        }

        for (Citizen retire : getListOfRetired()) {
            if (ProbabilitySelector.decision(retire.getAge() / 100.0)) {
                try {
                    System.out.println("One citizen died");
                    die(retire, gm);
                } catch (NullPointerException e) {}
                HumanManufacture.createYoungCitizen(gm);
            }
        }
        for (Citizen worker : getListOfWorkForce()) {
            if (worker.getSatisfaction(gm) < Constants.CITIZEN_LEAVING_SATISFACTION) {
                System.out.println("One citizen left");
                try {
                    die(worker, gm);
                } catch (NullPointerException e) {}
            }
        }
    }

    private List<Citizen> getListOfRetired() {
        List<Citizen> retired = new LinkedList<>();
        for (Citizen citizen : cityRegistry.getAllCitizens()) {
            if (citizen.getAge() >= 65) {
                retired.add(citizen);
            }
        }
        return retired;
    }

    private List<Citizen> getListOfWorkForce() {
        List<Citizen> workForce = new LinkedList<>();
        for (Citizen citizen : cityRegistry.getAllCitizens()) {
            if (citizen.getAge() < 65) {
                workForce.add(citizen);
            }
        }
        return workForce;
    }

    private void die(Citizen dead, GameModel gm) throws NullPointerException {
        dead.getLivingplace().removeCitizen(dead, gm);
        dead.getWorkplace().removeCitizen(dead, gm);
    }


    /**
     * Calculates the spend of pension
     *
     * @return the total amount of pension to be paid by city.
     */
    public int payPension() {
        int total = 0;
        for (Citizen retire : getListOfRetired()) {
            total += retire.getPension();
        }
        System.out.println("Social Security: Paid " + total + " pension to " + getListOfRetired().size() + " retires");
        return total;
    }

    /**
     * Calculate the total tax revenue from tax
     * @param taxRate current tax rate
     * @return total tax from work forces.
     */
    public double collectTax(double taxRate) {
        double total = 0;
        for (Citizen c : getListOfWorkForce()) {
            total += c.payTax(taxRate);
        }
        System.out.println("Social Security: Got tax " + total + " from" + getListOfWorkForce().size() + " workers");
        return total;
    }

    /**
     * Records the tax rate of the year for every citizen
     */
    public void appendTaxRecord() {
        for (Citizen c : getListOfWorkForce()) {
            c.addPaidTax(
                    Constants.BASE_TAX * cityRegistry.getCityStatistics().getBudget().getTaxRate() +
                    c.getLevelOfEducation().getAdditionalValue()
            );
        }
    }
}
