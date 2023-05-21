package controller.util;

import controller.ICallBack;
import model.GameModel;
import model.city.CityStatistics;
import model.common.Budget;
import model.common.Buildable;
import model.common.Citizen;
import model.common.Coordinate;
import model.util.Date;
import model.zone.Zone;
import model.zone.ZoneStatistics;

import java.util.List;

public class Property {
    private GameMode gameMode;
    private final GameModel gameModel;
    private ICallBack iCallBack;
    private TimeMode timeMode;

    public Property(GameMode gameMode, GameModel gameModel, TimeMode timeMode) {
        this.gameMode = gameMode;
        this.gameModel = gameModel;
        this.timeMode = timeMode;
        iCallBack = new ICallBack() {
            @Override
            public void updateGridSystem(Coordinate coordinate, Buildable buildable) {
                System.out.println("Default Call back");
            }

            @Override
            public void updateBudgetPanel(Budget budget) {
                System.out.println("Default Call back");
                System.out.println("________Callback Inform Budget_________");
                System.out.println("Tax rate: " + budget.getTaxRate());
                System.out.println("Balance: " + budget.getBalance());
                System.out.println("---------------------------------------");
            }

            @Override
            public void updateStatisticPanel(Zone zone) {
                System.out.println("Default Call back");
                ZoneStatistics zoneStatistics = zone.getStatistics();
                System.out.println("________Callback Inform Zone Statistic_________");
                System.out.println("Selected Zone population: " + zoneStatistics.getPopulation());
                System.out.println("Selected Zone capacity: " + zone.getCapacity());
                System.out.println("Selected Zone satisfaction: " + zoneStatistics.getSatisfaction());
                System.out.println("Selected Zone citizens: ");
                List<Citizen> citizens = zone.getCitizens();
                for (Citizen c :
                        citizens) {
                    System.out.println(c);
                }
                System.out.println("-----------------------------------------------");
            }

            @Override
            public void updateDatePanel(Date date) {
                System.out.println("Default Call back");
                System.out.println("________Callback Inform City Date_________");
                System.out.println("City Date: " + date);
                System.out.println("------------------------------------------");
            }

            @Override
            public void updateCityStatisticPanel(CityStatistics cityStatistics) {
                System.out.println("Default Call back");
                System.out.println("________Callback Inform City Statistic_________");
                System.out.println("City population: " + cityStatistics.getPopulation(gameModel.getCityRegistry()));
                System.out.println("City satisfaction: " + cityStatistics.getCitySatisfaction());
                System.out.println("-----------------------------------------------");
            }

            @Override
            public void shoutLose(boolean isLost) {
            }
        };
    }

    public TimeMode getTimeMode() {
        return timeMode;
    }

    public void setTimeMode(TimeMode timeMode) {
        this.timeMode = timeMode;
    }

    public ICallBack getCallBack() {
        return iCallBack;
    }

    public void setCallBack(ICallBack iCallBack) {
        this.iCallBack = iCallBack;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public GameModel getGameModel() {
        return gameModel;
    }

}
