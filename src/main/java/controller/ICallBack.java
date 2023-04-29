package controller;

import model.city.CityStatistics;
import model.common.Budget;
import model.common.Buildable;
import model.common.Coordinate;
import model.util.Date;
import model.zone.ZoneStatistics;

public interface ICallBack {
    public void updateGridSystem(Coordinate coordinate, Buildable buildable);
    public void updateBudgetPanel(Budget budget);
    public void updateStatisticPanel(ZoneStatistics zoneStatistics);
    public void updateDatePanel(Date date);

    public void updateCityStatisticPanel(CityStatistics cityStatistics);
}
