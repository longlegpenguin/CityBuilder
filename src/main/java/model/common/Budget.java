package model.common;

import java.util.LinkedList;

public class Budget implements java.io.Serializable {
    private double balance;
    private final double taxRate;
    private double totalMaintenanceFee;
    private LinkedList<Double> taxRatePast20Years;

    public Budget(double balance, double taxRate) {
        this.balance = balance;
        this.taxRate = taxRate;
        this.totalMaintenanceFee = 0;
        taxRatePast20Years = new LinkedList<>();
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

    public LinkedList<Double> getTaxRatePast20Years() {
        return taxRatePast20Years;
    }

    public void addBalance(double amount) {
        this.balance += amount;
    }

    public void deductBalance(double amount) {
        this.balance -= amount;
    }

    public void addTaxRate(double newTax) {
        if (taxRatePast20Years.size() == 20) {
            taxRatePast20Years.addLast(newTax);
            taxRatePast20Years.removeFirst();
        } else {
            taxRatePast20Years.addLast(newTax);
        }
    }

    public void addMaintenanceFee(double maintenanceFee) {
        totalMaintenanceFee += maintenanceFee;
    }

    public void deductMaintenanceFee(double maintenanceFee) {
        totalMaintenanceFee -= maintenanceFee;
    }

    /**
     * @return average income of the last 20 years.
     */
    public double getPast20AvgIncome() {
        double sumTax = 0;
        int cnt = 0;
        for (Double taxRatePast20Year : taxRatePast20Years) {
            sumTax += taxRatePast20Year;
            cnt += 1;
        }
        return sumTax / cnt;
    }

}
