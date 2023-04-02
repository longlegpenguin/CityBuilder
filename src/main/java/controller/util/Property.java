package controller.util;

import controller.ICallBack;
import model.GameModel;
import model.common.Budget;
import model.common.Buildable;

public class Property {
    private GameMode gameMode;
    private GameModel gameModel;
    private ICallBack iCallBack;

    public Property(GameMode gameMode, GameModel gameModel) {
        this.gameMode = gameMode;
        this.gameModel = gameModel;
        iCallBack = new ICallBack() {
            @Override
            public void updateGridSystem(Buildable buildable) {
                System.out.println("Default Call back");
            }

            @Override
            public void updateBudgetPanel(Budget budget) {
                System.out.println("Default Call back");
            }
        };
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

    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
    }
}
