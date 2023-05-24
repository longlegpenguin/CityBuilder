package engine.guis;

/**
 * Button enumerator which stores the type of the button for rendering purposes.
 */
public enum ButtonEnum {
    RESIDENTIAL_ZONE("Residential Zoning Mode"),
    COMMERICAL_ZONE("Commercial Zoning Mode"),
    INDUSTRIAL_ZONE("Industrial Zoning Mode"),
    DE_ZONE("De-zoning Mode"),
    ROAD("Road Mode"),
    FOREST("Forest Mode"),
    POLICE("Police Mode"),
    STADIUM("Stadium Mode"),
    SCHOOL("School Mode"),
    UNIVERSITY("University Mode"),
    SELECT("Selection Mode"),
    SPEED_PAUSE("Time Paused"),
    SPEED_ONE("1x Game Speed"),
    SPEED_TWO("2x Game Speed"),
    SPEED_THREE("3x Game Speed"),
    MONEY("Money Mode"),
    INCREASE_TAX("Increase Tax"),
    DECREASE_TAX("Decrease Tax"),
    RESUME_GAME("Resuming a Game"),
    SAVE_GAME("Saving a game"),
    EXIT_GAME("Exiting a game");

    private String buttonStr;
    ButtonEnum(String buttonStr) {
        this.buttonStr = buttonStr;
    }

    @Override
    public String toString() {
        return buttonStr;
    }
}
