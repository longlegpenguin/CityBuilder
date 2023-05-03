package controller;

import model.city.CityStatistics;
import model.common.Budget;
import model.common.Buildable;
import model.common.Coordinate;
import model.util.Date;
import model.zone.ZoneStatistics;

public interface ICallBack {
    void updateGridSystem(Coordinate coordinate, Buildable buildable);

    void updateBudgetPanel(Budget budget);

    void updateStatisticPanel(ZoneStatistics zoneStatistics);

    void updateDatePanel(Date date);

    void updateCityStatisticPanel(CityStatistics cityStatistics);
}
