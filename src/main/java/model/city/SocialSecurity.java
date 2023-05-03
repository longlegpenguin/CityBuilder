package model.city;

import model.GameModel;
import model.common.Citizen;
import model.common.Constants;
import model.common.HumanManufacture;
import model.util.ProbabilitySelector;

import java.util.LinkedList;
import java.util.List;

public class SocialSecurity {
    List<Citizen> retires;
    List<Citizen> workForces;
    CityStatistics cityStatistics;

    public SocialSecurity(List<Citizen> retires, CityStatistics cityStatistics) {
        this.retires = retires;
        this.cityStatistics = cityStatistics;
    }

    public SocialSecurity(CityStatistics cityStatistics) {
        this.retires = new LinkedList<>();
        this.cityStatistics = cityStatistics;
        this.workForces = new LinkedList<>();
    }

    /**
     * Clean up existing citizens and aging them.
     */
    public void census(GameModel gm) {
        List<Citizen> newWorkForces = new LinkedList<>();
        List<Citizen> newRetire = new LinkedList<>();
        for (Citizen retire : retires) {
            retire.incAge();
            if (ProbabilitySelector.decision(retire.getAge() / 100.0)) {
                try {
                    die(retire, gm);
                } catch (NullPointerException e) {}
                newWorkForces.add(HumanManufacture.createYoungCitizen(gm));
            } else {
                newRetire.add(retire);
            }
        }
        retires = newRetire;
        workForces = newWorkForces;
        for (Citizen worker : workForces) {
            worker.incAge();
            if (worker.getAge() >= 80) {
                addRetire(worker);
            }
        }
    }

    private void die(Citizen dead, GameModel gm) throws NullPointerException {
        dead.getWorkplace().removeCitizen(dead, gm);
        dead.getLivingplace().removeCitizen(dead, gm);
    }

    public void addRetire(Citizen citizen) {
        calculatePension(citizen);
        retires.add(citizen);
        workForces.remove(citizen);
    }

    public void addWorkForce(Citizen citizen) {
        workForces.add(citizen);
    }

    public void removeWorkForce(Citizen citizen) {
        workForces.remove(citizen);
    }

    private void calculatePension(Citizen citizen) {
        int pension = 0;
        for (double rate :
                cityStatistics.getBudget().getTaxRatePast20Years()) {
            pension += rate * Constants.BASE_TAX;
        }
        citizen.setPension(pension / 20);
    }

    public void removeRetire(Citizen citizen) {
        retires.remove(citizen);
    }

    /**
     * Calculates the spend of pension
     *
     * @return the total amount of pension to be paid by city.
     */
    public int payPension() {
        int total = 0;
        for (Citizen retire :
                retires) {
            total += retire.getPension();
        }
        return total;
    }

    /**
     * Records the tax rate of the year.
     */
    public void appendTaxRecord() {
        cityStatistics.getBudget().addTaxRate(cityStatistics.getBudget().getTaxRate());
    }
}
