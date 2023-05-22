package view;

import controller.Controller;
import engine.fontMeshCreator.GUIText;
import engine.fontRendering.TextMaster;
import engine.guis.UiTab;
import engine.renderEngine.Loader;
import model.GameModel;
import model.common.Citizen;
import model.zone.Zone;
import model.zone.ZoneStatistics;
import org.joml.Vector2f;

import java.util.ArrayList;

public class ZoneSelector extends Menu{

    private UiTab tab;
    private String tabTexture = "Test";
    private Loader loader = new Loader();
    private Zone zone;
    private ZoneStatistics zoneStatistics;
    private GUIText zoneType;
    private GUIText zonePopulation;
    private GUIText zoneCapacity;
    private GUIText zoneSatisfaction;
    private GUIText citizenColumn;
    private GUIText ageColumn;
    private GUIText educationColumn;
    private GUIText employedColumn;
    private GUIText pensionColumn;
    private GUIText taxPaidColumn;
    private GUIText satisfactionColumn;


    public ZoneSelector(Controller controller, GameModel gameModel, Zone zone) {
        super(controller, gameModel);
        this.zone = zone;
        this.zoneStatistics = zone.getStatistics();
        loadComponents();
    }

    @Override
    protected void loadComponents() {
        tab = new UiTab(loader.loadTexture(tabTexture), new Vector2f(0f, 0.1f), new Vector2f(0.8f, 0.7f));
        super.tabs.add(tab);

        zoneType = new GUIText("Zone type: " + zone.getBuildableType(), 1f, new Vector2f(0.12f, 0.11f), 1, false);
        TextMaster.loadText(zoneType);
        super.texts.add(zoneType);

        zoneCapacity = new GUIText("Max Capacity: " + zone.getCapacity(), 1f, new Vector2f(0.34f, 0.11f), 1, false);
        TextMaster.loadText(zoneCapacity);
        super.texts.add(zoneCapacity);

        zonePopulation = new GUIText("Current Population: " + zone.getPopulation(), 1f, new Vector2f(0.52f, 0.11f), 1, false);
        TextMaster.loadText(zonePopulation);
        super.texts.add(zonePopulation);

        zoneSatisfaction = new GUIText("Current Satisfaction: " + Math.round(zone.getZoneSatisfaction(gameModel) * 100)/100.0 + "%", 1f, new Vector2f(0.7f, 0.11f), 1, false);
        TextMaster.loadText(zoneSatisfaction);
        super.texts.add(zoneSatisfaction);

        citizenColumn = new GUIText("Citizens", 1.3f, new Vector2f(0.12f, 0.2f), 1f, false);
        TextMaster.loadText(citizenColumn);
        super.texts.add(citizenColumn);

        ageColumn = new GUIText("Age", 1.3f, new Vector2f(0.22f, 0.2f), 1f, false);
        TextMaster.loadText(ageColumn);
        super.texts.add(ageColumn);

        educationColumn = new GUIText("Education Level", 1.3f, new Vector2f(0.28f, 0.2f), 1f, false);
        TextMaster.loadText(educationColumn);
        super.texts.add(educationColumn);

        employedColumn = new GUIText("Employed", 1.3f, new Vector2f(0.45f, 0.2f), 1f, false);
        TextMaster.loadText(employedColumn);
        super.texts.add(employedColumn);

        pensionColumn = new GUIText("Pension", 1.3f, new Vector2f(0.56f, 0.2f), 1f, false);
        TextMaster.loadText(pensionColumn);
        super.texts.add(pensionColumn);

        taxPaidColumn = new GUIText("Tax Payed", 1.3f, new Vector2f(0.67f, 0.2f), 1f, false);
        TextMaster.loadText(taxPaidColumn);
        super.texts.add(taxPaidColumn);

        satisfactionColumn = new GUIText("Satisfaction", 1.3f, new Vector2f(0.8f, 0.2f), 1f, false);
        TextMaster.loadText(satisfactionColumn);
        super.texts.add(satisfactionColumn);

        createCitizenTexts();
    }

    @Override
    public void updateText() {
        super.clearText();
        zoneType.setTextString("Zone type: " + zone.getBuildableType());
        TextMaster.loadText(zoneType);
        super.texts.add(zoneType);

        zoneCapacity.setTextString("Max Capacity: " + zone.getCapacity());
        TextMaster.loadText(zoneCapacity);
        super.texts.add(zoneCapacity);

        zonePopulation.setTextString("Current Population: " + zone.getPopulation());
        TextMaster.loadText(zonePopulation);
        super.texts.add(zonePopulation);

        zoneSatisfaction.setTextString("Current Satisfaction: " + Math.round(zone.getZoneSatisfaction(gameModel)*100)/100.0 + "%");
        TextMaster.loadText(zoneSatisfaction);
        super.texts.add(zoneSatisfaction);

        TextMaster.loadText(citizenColumn);
        super.texts.add(citizenColumn);

        TextMaster.loadText(ageColumn);
        super.texts.add(ageColumn);

        TextMaster.loadText(educationColumn);
        super.texts.add(educationColumn);

        TextMaster.loadText(employedColumn);
        super.texts.add(employedColumn);

        TextMaster.loadText(pensionColumn);
        super.texts.add(pensionColumn);

        TextMaster.loadText(taxPaidColumn);
        super.texts.add(taxPaidColumn);

        TextMaster.loadText(satisfactionColumn);
        super.texts.add(satisfactionColumn);

        createCitizenTexts();
    }

    private void createCitizenTexts() {
        float height = 0.26f;
        float increment = 0.035f;
        float finalHeight;
        for (int i = 0; i < zone.getCitizens().size(); i++) {
            Citizen citizen = zone.getCitizens().get(i);
            finalHeight = height + (increment * i);
            GUIText citizenName = new GUIText("Citizen " + (i+1) , 1f, new Vector2f(0.12f, finalHeight), 1, false);
            TextMaster.loadText(citizenName);
            super.texts.add(citizenName);

            GUIText citizenAge = new GUIText(String.valueOf(citizen.getAge()), 1f, new Vector2f(0.22f, finalHeight), 1, false);
            TextMaster.loadText(citizenAge);
            super.texts.add(citizenAge);

            GUIText citizenEducation = new GUIText(citizen.getLevelOfEducation().toString(), 1f, new Vector2f(0.28f, finalHeight), 1, false);
            TextMaster.loadText(citizenEducation);
            super.texts.add(citizenEducation);

            GUIText citizenEmployed = new GUIText(citizen.isUnemployed() ? "No" : "Yes", 1f, new Vector2f(0.45f, finalHeight), 1, false);
            TextMaster.loadText(citizenEmployed);
            super.texts.add(citizenEmployed);

            GUIText citizenPension = new GUIText(String.valueOf(Math.round(citizen.getPension()*100)/100.0) , 1f, new Vector2f(0.56f, finalHeight), 1, false);
            TextMaster.loadText(citizenPension);
            super.texts.add(citizenPension);

            GUIText citizenTax = new GUIText(String.valueOf(Math.round(citizen.getPast20AvgIncome()*100)/100.0), 1f, new Vector2f(0.67f, finalHeight), 1, false);
            TextMaster.loadText(citizenTax);
            super.texts.add(citizenTax);

            GUIText citizenSatisfaction = new GUIText(String.valueOf(Math.round(citizen.getSatisfaction(gameModel)*100)/100.0) , 1f, new Vector2f(0.8f, finalHeight), 1, false);
            TextMaster.loadText(citizenSatisfaction);
            super.texts.add(citizenSatisfaction);
        }
    }
}
