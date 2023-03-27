package model.util;

public enum Month {
    JANUARY(1), FEBRUARY(2), MARCH(3), APRIL(4), MAY(5), JUNE(6), JULY(7), AUGUST(8), SEPTEMBER(9), OCTOBER(10), NOVEMBER(11), DECEMBER(12);

    private final int monthOrder;

    Month(int monthOrder) {
        this.monthOrder = monthOrder;
    }

    public int getMonthOrder() {
        return this.monthOrder;
    }

    /**
     * Getting the month based on the month order.
     *
     * @param  monthOrder month order
     * @return month
     * @throws IllegalArgumentException "There is no month with such month order"
     */
    public static Month getMonthFromMonthOrder(int monthOrder) throws IllegalArgumentException {
        for (Month month : Month.values()) {
            if (month.getMonthOrder() == monthOrder) return month;
        }
        throw new IllegalArgumentException("There is no month with such month order!");
    }

}
