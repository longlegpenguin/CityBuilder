package controller.listeners;

import controller.util.GameMode;
import controller.util.Property;
import model.GameModel;
import model.common.Coordinate;
import model.exceptions.OperationException;
import model.zone.*;

public class ZoneBuildingListener extends ServiceListener {


    public ZoneBuildingListener(Property property) {
        super(property);
    }

    @Override
    public void update(Coordinate coordinate) {

        GameMode gmo = property.getGameMode();
        GameModel gm = property.getGameModel();
        System.out.println(gmo);
        Zone zone = null;
        switch (gmo) {
            case COMMERCIAL_MODE -> zone = new CommercialZoneFactory(gm).createZone(coordinate);
            case INDUSTRIAL_MODE -> zone = new IndustrialZoneFactory(gm).createZone(coordinate);
            case RESIDENTIAL_MODE -> zone = new ResidentialZoneFactory(gm).createZone(coordinate);
        }
        System.out.println("Created" + zone);
        try {
            gm.addZone(zone);
            assert zone != null;
            property.getCallBack().updateGridSystem(zone.getCoordinate(), zone);
//            property.getCallBack().updateCityStatisticPanel(gm.getCityStatistics());
//            property.getCallBack().updateBudgetPanel(gm.queryCityBudget());
        } catch (OperationException e) {
            System.out.println(e.getMessage());
        }
    }
}
