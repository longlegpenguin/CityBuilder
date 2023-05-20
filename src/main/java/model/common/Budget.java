package model.common;

import model.GameModel;
import model.util.Date;
import model.util.Month;
import persistence.Database;

import java.util.LinkedList;

public class Budget implements java.io.Serializable {
    private double balance;
    private double taxRate;
    private double totalMaintenanceFee;
    private Date lastPositiveBudgetDay;

    public Budget(double balance, double taxRate) {
        this.balance = balance;
        this.taxRate = taxRate;
        this.totalMaintenanceFee = 0;
        this.lastPositiveBudgetDay = new Date(1, Month.JANUARY, 2020);
    }

    public double getBalance() {
        return balance;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public double getTotalMaintenanceFee() {
        return totalMaintenanceFee;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public void addBalance(double amount, Date now) {
        this.balance += amount;
        if (this.balance >= 0) {
            lastPositiveBudgetDay = now;
        }
    }

    /**
     * Gets the number of years since the last day the budget is positive.
     * @param now current date
     * @return number of years
     */
    public int getNegativeYears(Date now) {
        return now.dateDifference(lastPositiveBudgetDay).get("years");
    }

    public void addMaintenanceFee(double maintenanceFee) {
        totalMaintenanceFee += maintenanceFee;
    }

    public void deductMaintenanceFee(double maintenanceFee) {
        totalMaintenanceFee -= maintenanceFee;
    }

    /**
     * Gets the revenue of the city
     * @param gm game model
     * @return amount
     */
    public double getRevenue(GameModel gm) {
        return gm.calculateRevenue();
    }

    /**
     * Gets the spend of the city
     * @param gm game model
     * @return amount
     */
    public double getSpend(GameModel gm) {
        return gm.calculateSpend();
    }
}
