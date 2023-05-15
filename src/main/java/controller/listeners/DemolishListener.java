package controller.listeners;

import controller.util.Property;
import model.GameModel;
import model.common.Coordinate;
import model.exceptions.OperationException;

public class DemolishListener extends ServiceListener {

    public DemolishListener(Property property) {
        super(property);
    }

    @Override
    public void update(Coordinate coordinate) {
        try {
            GameModel gm = property.getGameModel();
            gm.removeBuildable(coordinate);
            property.getCallBack().updateGridSystem(coordinate, null);
            property.getCallBack().updateCityStatisticPanel(gm.getCityStatistics());
            property.getCallBack().updateBudgetPanel(gm.queryCityBudget());
        } catch (OperationException e) {
            System.out.println(e.getMessage());
        }
    }
}
