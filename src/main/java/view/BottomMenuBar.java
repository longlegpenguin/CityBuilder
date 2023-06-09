package view;

import controller.Controller;
import controller.util.GameMode;
import engine.fontMeshCreator.GUIText;
import engine.fontRendering.TextMaster;
import engine.guis.ButtonEnum;
import engine.guis.UiButton;
import engine.guis.UiTab;
import engine.renderEngine.Loader;
import model.GameModel;
import org.joml.Vector2f;

public class BottomMenuBar extends Menu{

    private Loader loader = new Loader();
    private UiTab bottomTab;
    private UiTab dateTab;
    private UiButton resZoneButton;
    private UiButton comZoneButton;
    private UiButton indZoneButton;
    private UiButton deZoneButton;
    private UiButton roadButton;
    private UiButton forestButton;
    private UiButton policeButton;
    private UiButton stadiumButton;
    private UiButton schoolButton;
    private UiButton universityButton;
    private UiButton selectZoneButton;
    private UiButton speedPauseButton;
    private UiButton speedOneButton;
    private UiButton speedTwoButton;
    private UiButton speedThreeButton;
    private UiButton moneyButton;
    private GUIText dateText;
    private GUIText gameModeText;
    private String buttonTexture = "Button";

    public BottomMenuBar(Controller controller, GameModel gameModel) {
        super(controller, gameModel);
        loadComponents();
    }

    @Override
    protected void loadComponents() {

        bottomTab = new UiTab(loader.loadTexture("Test"),new Vector2f(0,-0.87f),new Vector2f(1f,0.13f));
        super.tabs.add(bottomTab);
        dateTab = new UiTab(loader.loadTexture(buttonTexture),new Vector2f(-0.87f, -0.8f), new Vector2f(0.08f, 0.03f));
        super.tabs.add(dateTab);

        resZoneButton = new UiButton(loader.loadTexture("button/residential"), new Vector2f(-0.9f, -0.92f), new Vector2f(0.05f, 0.05f), ButtonEnum.RESIDENTIAL_ZONE);
        super.buttons.add(resZoneButton);
        comZoneButton = new UiButton(loader.loadTexture("button/commercial"), new Vector2f(-0.75f, -0.92f), new Vector2f(0.05f, 0.05f), ButtonEnum.COMMERICAL_ZONE);
        super.buttons.add(comZoneButton);
        indZoneButton = new UiButton(loader.loadTexture("button/industrial"), new Vector2f(-0.60f, -0.92f), new Vector2f(0.05f, 0.05f), ButtonEnum.INDUSTRIAL_ZONE);
        super.buttons.add(indZoneButton);
        deZoneButton = new UiButton(loader.loadTexture("button/dezone"), new Vector2f(-0.45f, -0.92f), new Vector2f(0.05f, 0.05f), ButtonEnum.DE_ZONE);
        super.buttons.add(deZoneButton);

        roadButton = new UiButton(loader.loadTexture("button/road"), new Vector2f(-0.2f, -0.92f), new Vector2f(0.05f, 0.05f), ButtonEnum.ROAD);
        super.buttons.add(roadButton);
        forestButton = new UiButton(loader.loadTexture("button/forest"), new Vector2f(-0.05f, -0.92f), new Vector2f(0.05f, 0.05f), ButtonEnum.FOREST);
        super.buttons.add(forestButton);
        policeButton = new UiButton(loader.loadTexture("button/police"), new Vector2f(0.1f, -0.92f), new Vector2f(0.05f, 0.05f), ButtonEnum.POLICE);
        super.buttons.add(policeButton);
        stadiumButton = new UiButton(loader.loadTexture("button/stadium"), new Vector2f(0.25f, -0.92f), new Vector2f(0.05f, 0.05f), ButtonEnum.STADIUM);
        super.buttons.add(stadiumButton);
        schoolButton = new UiButton(loader.loadTexture("button/school"), new Vector2f(0.4f, -0.92f), new Vector2f(0.05f, 0.05f), ButtonEnum.SCHOOL);
        super.buttons.add(schoolButton);
        universityButton = new UiButton(loader.loadTexture("button/university"), new Vector2f(0.55f, -0.92f), new Vector2f(0.05f, 0.05f), ButtonEnum.UNIVERSITY);
        super.buttons.add(universityButton);

        selectZoneButton = new UiButton(loader.loadTexture("button/select"), new Vector2f(0.9f, -0.92f), new Vector2f(0.05f, 0.05f), ButtonEnum.SELECT);
        super.buttons.add(selectZoneButton);

        speedPauseButton = new UiButton(loader.loadTexture("button/Pause"), new Vector2f(-0.74f, -0.8f), new Vector2f(0.03f, 0.03f), ButtonEnum.SPEED_PAUSE);
        super.buttons.add(speedPauseButton);
        speedOneButton = new UiButton(loader.loadTexture("button/OneArrow"), new Vector2f(-0.66f, -0.8f), new Vector2f(0.03f, 0.03f), ButtonEnum.SPEED_ONE);
        super.buttons.add(speedOneButton);
        speedTwoButton = new UiButton(loader.loadTexture("button/DoubleArrow"), new Vector2f(-0.58f, -0.8f), new Vector2f(0.03f, 0.03f), ButtonEnum.SPEED_TWO);
        super.buttons.add(speedTwoButton);
        speedThreeButton = new UiButton(loader.loadTexture("button/TripleArrow"), new Vector2f(-0.50f, -0.8f), new Vector2f(0.03f, 0.03f), ButtonEnum.SPEED_THREE);
        super.buttons.add(speedThreeButton);

        moneyButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(0.87f, -0.8f), new Vector2f(0.08f, 0.03f), ButtonEnum.MONEY);
        super.buttons.add(moneyButton);

        dateText = new GUIText(super.gameModel.getCurrentDate().toString(),1,new Vector2f(0.025f,0.885f),1f,false);
        dateText.setColour(0,0,0);
        TextMaster.loadText(dateText);
        super.texts.add(dateText);

        gameModeText = new GUIText(ButtonEnum.SELECT.toString(), 1f, new Vector2f(0.025f, 0.83f), 1f, false);
        gameModeText.setColour(1,0,0);
        TextMaster.loadText(gameModeText);
    }

    @Override
    public void updateText() {
        dateText.setTextString(super.gameModel.getCurrentDate().toString());
        TextMaster.loadText(dateText);
        super.texts.add(dateText);
    }

    public void resZoneButtonAction() {
        buttonAction(resZoneButton, GameMode.RESIDENTIAL_MODE, gameModeText);
    }

    public void comZoneButtonAction() {
        buttonAction(comZoneButton, GameMode.COMMERCIAL_MODE, gameModeText);
    }

    public void indZoneButtonAction() {
        buttonAction(indZoneButton, GameMode.INDUSTRIAL_MODE, gameModeText);
    }

    public void deZoneButtonAction() {
        buttonAction(deZoneButton, GameMode.DEMOLISH_MODE, gameModeText);
    }

    public void roadButtonAction() {
        buttonAction(roadButton, GameMode.ROAD_MODE, gameModeText);
    }

    public void forestButtonAction() {
        buttonAction(forestButton, GameMode.FOREST_MODE, gameModeText);
    }

    public void policeButtonAction() {
        buttonAction(policeButton, GameMode.POLICE_MODE, gameModeText);
    }

    public void stadiumButtonAction() {
        buttonAction(stadiumButton, GameMode.STADIUM_MODE, gameModeText);
    }

    public void schoolButtonAction() {
        buttonAction(schoolButton, GameMode.SCHOOL_MODE, gameModeText);
    }

    public void universityButton() {
        buttonAction(universityButton, GameMode.UNIVERSITY_MODE, gameModeText);
    }


    public void selectButtonAction() {
        buttonAction(selectZoneButton, GameMode.SELECTION_MODE, gameModeText);
    }

}