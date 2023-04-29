package controller.listeners;

import controller.util.Property;
import model.common.Coordinate;
import model.exceptions.OperationException;

public class DemolishListener extends ServiceListener {

    public DemolishListener(Property property) {
        super(property);
    }

    @Override
    public void update(Coordinate coordinate) {
        try {
            property.getGameModel().removeBuildable(coordinate);
            property.getCallBack().updateGridSystem(coordinate, null);
        } catch (OperationException e) {
            System.out.println(e.getMessage());
        }
    }
}
