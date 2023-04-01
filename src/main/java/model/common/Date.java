package model.common;

import model.util.Month;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

public class Date implements Comparable<Date> {
    private final int day;
    private final Month month;
    private final int year;
    private float partial;

    public Date(int day, Month month, int year) throws IllegalArgumentException {
        if (day > 31 || day < 0 || year < 0) throw new IllegalArgumentException("Invalid input");
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public Month getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    /**
     * Computes the difference between 2 dates.
     *
     * @param other other date
     * @return date difference
     */
    public HashMap<String, Integer> dateDifference(Date other) {
        if (other.day > 31 || other.day < 0 || other.year < 0) throw new IllegalArgumentException("Invalid input");

        HashMap<String, Integer> dateDiff = new LinkedHashMap<>();

        LocalDate startDate = LocalDate.of(this.year, this.month.getMonthOrder(), this.day);
        LocalDate endDate = LocalDate.of(other.year, other.month.getMonthOrder(), other.day);

        Period diff = Period.between(startDate, endDate);

        dateDiff.put("days", Math.abs(diff.getDays()));
        dateDiff.put("months", Math.abs(diff.getMonths()));
        dateDiff.put("years", Math.abs(diff.getYears()));

        return dateDiff;
    }

    @Override
    public String toString() {
        String strDay = "";
        String strMonth = "";
        if ((int) (Math.log10(this.day) + 1) < 2) strDay += "0" + this.day;
        if ((int) (Math.log10(this.month.getMonthOrder()) + 1) < 2) strMonth += "0" + this.month.getMonthOrder();
        return "" + this.year + "/" + strMonth + "/" + strDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Date that = (Date) o;
        return year == that.year && month == that.month && day == that.day;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, day);
    }

    /**
     * Compares 2 dates
     *
     * @param d the object to be compared.
     * @return -1 -> passed date is bigger, 1 -> passed date smaller, 0 -> dates are equal
     */
    @Override
    public int compareTo(Date d) {
        if (d.day > 31 || d.day < 0 || d.year < 0) throw new IllegalArgumentException("Invalid input");

        if (this.year < d.year ||
                this.year == d.year && this.month.getMonthOrder() < d.month.getMonthOrder() ||
                this.year == d.year && this.month.getMonthOrder() == d.month.getMonthOrder() && this.day < d.day)
            return -1;
        else if (this.year > d.year || this.month.getMonthOrder() > d.month.getMonthOrder() || this.day > d.day)
            return 1;
        return 0;
    }
}