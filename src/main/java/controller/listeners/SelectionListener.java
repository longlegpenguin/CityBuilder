package controller.listeners;

import controller.util.Property;
import model.common.Coordinate;
import model.exceptions.OperationException;
import model.zone.ZoneStatistics;

public class SelectionListener extends ServiceListener {


    public SelectionListener(Property property) {
        super(property);
    }

    @Override
    public void update(Coordinate coordinate) {
        try {
            ZoneStatistics zoneStatistics = property.getGameModel().queryZoneStatistics(coordinate);
            property.getCallBack().updateStatisticPanel(zoneStatistics);
        } catch (OperationException e) {
            System.out.println(e.getMessage());
        }
    }
}
