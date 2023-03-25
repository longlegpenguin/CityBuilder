package controller.util;

import model.GameModel;

public class Property {
    private GameMode gameMode;
    private GameModel gameModel;

    public Property(GameMode gameMode, GameModel gameModel) {
        this.gameMode = gameMode;
        this.gameModel = gameModel;
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
