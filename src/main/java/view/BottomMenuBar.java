package view;

import engine.guis.ButtonEnum;
import engine.guis.UiButton;
import engine.renderEngine.Loader;
import org.joml.Vector2f;

public class BottomMenuBar extends Menu{

    private Loader loader = new Loader();
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
    private UiButton destroyButton;
    private UiButton speedPauseButton;
    private UiButton speedOneButton;
    private UiButton speedTwoButton;
    private UiButton speedThreeButton;
    private UiButton moneyButton;

    private String buttonTexture = "Button";

    public BottomMenuBar() {
        super();
        loadComponents();
    }

    @Override
    protected void loadComponents() {
        resZoneButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(-0.9f, -0.85f), new Vector2f(0.05f, 0.05f), ButtonEnum.RESIDENTIAL_ZONE);
        super.buttons.add(resZoneButton);
        comZoneButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(-0.75f, -0.85f), new Vector2f(0.05f, 0.05f), ButtonEnum.COMMERICAL_ZONE);
        super.buttons.add(comZoneButton);
        indZoneButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(-0.60f, -0.85f), new Vector2f(0.05f, 0.05f), ButtonEnum.INDUSTRIAL_ZONE);
        super.buttons.add(indZoneButton);
        deZoneButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(-0.45f, -0.85f), new Vector2f(0.05f, 0.05f), ButtonEnum.DE_ZONE);
        super.buttons.add(deZoneButton);

        roadButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(-0.2f, -0.85f), new Vector2f(0.05f, 0.05f), ButtonEnum.ROAD);
        super.buttons.add(roadButton);
        forestButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(-0.05f, -0.85f), new Vector2f(0.05f, 0.05f), ButtonEnum.FOREST);
        super.buttons.add(forestButton);
        policeButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(0.1f, -0.85f), new Vector2f(0.05f, 0.05f), ButtonEnum.POLICE);
        super.buttons.add(policeButton);
        stadiumButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(0.25f, -0.85f), new Vector2f(0.05f, 0.05f), ButtonEnum.STADIUM);
        super.buttons.add(stadiumButton);
        schoolButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(0.4f, -0.85f), new Vector2f(0.05f, 0.05f), ButtonEnum.SCHOOL);
        super.buttons.add(schoolButton);
        universityButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(0.55f, -0.85f), new Vector2f(0.05f, 0.05f), ButtonEnum.UNIVERSITY);
        super.buttons.add(universityButton);

        destroyButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(0.9f, -0.85f), new Vector2f(0.05f, 0.05f), ButtonEnum.DESTROY);
        super.buttons.add(destroyButton);

        speedPauseButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(-0.74f, -0.7f), new Vector2f(0.03f, 0.03f), ButtonEnum.SPEED_PAUSE);
        super.buttons.add(speedPauseButton);
        speedOneButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(-0.66f, -0.7f), new Vector2f(0.03f, 0.03f), ButtonEnum.SPEED_ONE);
        super.buttons.add(speedOneButton);
        speedTwoButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(-0.58f, -0.7f), new Vector2f(0.03f, 0.03f), ButtonEnum.SPEED_TWO);
        super.buttons.add(speedTwoButton);
        speedThreeButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(-0.50f, -0.7f), new Vector2f(0.03f, 0.03f), ButtonEnum.SPEED_THREE);
        super.buttons.add(speedThreeButton);

        moneyButton = new UiButton(loader.loadTexture(buttonTexture), new Vector2f(-0.87f, -0.7f), new Vector2f(0.08f, 0.03f), ButtonEnum.MONEY);
        super.buttons.add(moneyButton);
    }



}
