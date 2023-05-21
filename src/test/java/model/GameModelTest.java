package model;

import model.common.Citizen;
import model.common.Coordinate;
import model.common.Dimension;
import model.exceptions.OperationException;
import model.facility.*;
import model.util.LevelOfEducation;
import model.zone.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.common.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {

    GameModel gm = new GameModel(5, 10);

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 10; i++) {
            Road road = new Road(ROAD_ONE_TIME_COST, ROAD_MAINTENANCE_FEE, new Coordinate(5 - 1, i), new Dimension(1, 1));
            gm.getMasterRoads().add(road);
            gm.addToMap(road);
        }
        Road road = new Road(ROAD_ONE_TIME_COST, ROAD_MAINTENANCE_FEE, new Coordinate(3, 2), new Dimension(1, 1));
        gm.addToMap(road);
        road = new Road(ROAD_ONE_TIME_COST, ROAD_MAINTENANCE_FEE, new Coordinate(3, 4), new Dimension(1, 1));
        gm.addToMap(road);
    }

    // adding zone
    @Test
    void addUniqueZone() {
        try {
            gm.addZone(new ResidentialZoneFactory(gm).createZone(new Coordinate(3, 3)));
        } catch (OperationException e) {
            throw new RuntimeException(e);
        }
        System.out.println(gm.printMap());
        assertEquals(1 + gm.getMasterRoads().size(), gm.getAllBuildable().size());
    }

    @Test
    void addTwiceZone() throws OperationException {
        gm.addZone(new ResidentialZoneFactory(gm).createZone(new Coordinate(3, 3)));
        Assertions.assertThrows(OperationException.class, () -> gm.addZone(new ResidentialZoneFactory(gm).createZone(new Coordinate(3, 3))));
        System.out.println(gm.printMap());
        assertEquals(1 + gm.getMasterRoads().size(), gm.getAllBuildable().size());
    }

    // road
    @Test
    void TestBuildRoadOnEmptyPlot() {
        double beforeBalance = gm.getCityStatistics().getBudget().getBalance();
        Road r = (Road) new RoadFactory(gm).createFacility(new Coordinate(3, 3));
        try {
            gm.addFacility(r);
        } catch (OperationException e) {
            throw new RuntimeException(e);
        }
        double afterBalance = gm.getCityStatistics().getBudget().getBalance();
        assertEquals(ROAD_ONE_TIME_COST, beforeBalance - afterBalance);
        assertEquals(gm.calculateSpend(), ROAD_MAINTENANCE_FEE);
    }

    @Test
    void TestBuildRoadOnNonEmptyPlot() throws OperationException {
        gm.addFacility(new RoadFactory(gm).createFacility(new Coordinate(3, 3)));
        double beforeBalance = gm.getCityStatistics().getBudget().getBalance();
        Assertions.assertThrows(OperationException.class, () -> gm.addFacility(new RoadFactory(gm).createFacility(new Coordinate(3, 3))));
        double afterBalance = gm.getCityStatistics().getBudget().getBalance();
        assertEquals(1 + gm.getMasterRoads().size(), gm.getAllBuildable().size());
        assertEquals(0, beforeBalance - afterBalance);
        assertEquals(ROAD_MAINTENANCE_FEE, gm.calculateSpend());
    }

    @Test
    void TestBuildTwoDifferentRoadOnNonEmptyPlot() throws OperationException {
        double beforeBalance = gm.getCityStatistics().getBudget().getBalance();
        gm.addFacility(new RoadFactory(gm).createFacility(new Coordinate(1, 3)));
        gm.addFacility(new RoadFactory(gm).createFacility(new Coordinate(1, 2)));
        double afterBalance = gm.getCityStatistics().getBudget().getBalance();
        assertEquals(2 + gm.getMasterRoads().size(), gm.getAllBuildable().size());
        assertEquals(ROAD_ONE_TIME_COST * 2, beforeBalance - afterBalance);
        assertEquals(ROAD_MAINTENANCE_FEE * 2, gm.calculateSpend());
    }

    // stadium
    @Test
    void TestBuildStadiumOnEmptyPlot() {
        double beforeBalance = gm.getCityStatistics().getBudget().getBalance();
        Stadium r = (Stadium) new StadiumFactory(gm).createFacility(new Coordinate(1, 1));
        try {
            gm.addFacility(r);
        } catch (OperationException e) {
            throw new RuntimeException(e);
        }
        System.out.println(gm.printMap());
        double afterBalance = gm.getCityStatistics().getBudget().getBalance();
        assertEquals(STADIUM_ONE_TIME_COST, beforeBalance - afterBalance);
        assertEquals(STADIUM_MAINTENANCE_FEE, gm.calculateSpend());
    }

    @Test
    void TestBuildStadiumOnNonEmptyPlot() throws OperationException {
        gm.addFacility(new RoadFactory(gm).createFacility(new Coordinate(2, 2)));
        double beforeBalance = gm.getCityStatistics().getBudget().getBalance();
        // 1,1
        Assertions.assertThrows(OperationException.class, () -> gm.addFacility(new StadiumFactory(gm).createFacility(new Coordinate(1, 1))));
        // 0,0
        Assertions.assertThrows(OperationException.class, () -> gm.addFacility(new StadiumFactory(gm).createFacility(new Coordinate(2, 1))));
        // 0,1
        Assertions.assertThrows(OperationException.class, () -> gm.addFacility(new StadiumFactory(gm).createFacility(new Coordinate(2, 2))));
        // 1,0
        Assertions.assertThrows(OperationException.class, () -> gm.addFacility(new StadiumFactory(gm).createFacility(new Coordinate(1, 2))));
        double afterBalance = gm.getCityStatistics().getBudget().getBalance();
        assertEquals(1 + gm.getMasterRoads().size(), gm.getAllBuildable().size());
        assertEquals(0, beforeBalance - afterBalance);
        assertEquals(ROAD_MAINTENANCE_FEE, gm.calculateSpend());
    }

    @Test
    void TestBuildStadiumEffect() throws OperationException {
        Zone z = new ResidentialZoneFactory(gm).createZone(new Coordinate(3, 1));
        gm.addZone(z);
        double beforeSatis = z.getZoneSatisfaction(gm);
        Stadium r = (Stadium) new StadiumFactory(gm).createFacility(new Coordinate(1, 1));
        gm.addFacility(r);
        double afterSatis = z.getZoneSatisfaction(gm);
        assertEquals(STADIUM_BASE_EFFECT, afterSatis - beforeSatis);
    }

    // forest
    @Test
    void TestBuildForestOnEmptyPlot() {
        double beforeBalance = gm.getCityStatistics().getBudget().getBalance();
        Forest r = (Forest) new ForestFactory(gm).createFacility(new Coordinate(3, 3));
        try {
            gm.addFacility(r);
        } catch (OperationException e) {
            throw new RuntimeException(e);
        }
        double afterBalance = gm.getCityStatistics().getBudget().getBalance();
        assertEquals(FOREST_ONE_TIME_COST, beforeBalance - afterBalance);
        assertEquals(FOREST_MAINTENANCE_FEE, gm.calculateSpend());
    }

    @Test
    void TestBuildForestOnNonEmptyPlot() throws OperationException {
        gm.addFacility(new RoadFactory(gm).createFacility(new Coordinate(3, 3)));
        double beforeBalance = gm.getCityStatistics().getBudget().getBalance();
        Assertions.assertThrows(OperationException.class, () -> gm.addFacility(new ForestFactory(gm).createFacility(new Coordinate(3, 3))));
        double afterBalance = gm.getCityStatistics().getBudget().getBalance();
        assertEquals(1 + gm.getMasterRoads().size(), gm.getAllBuildable().size());
        assertEquals(0, beforeBalance - afterBalance);
        System.out.println(gm.printMap());
        assertEquals(ROAD_MAINTENANCE_FEE, gm.calculateSpend());
    }

    @Test
    void TestForestEffect() throws OperationException {
        // conditions are tested in forest class test.
        // here only test if the change appears.
        Zone z = new ResidentialZoneFactory(gm).createZone(new Coordinate(2, 2));
        gm.addZone(z);
        double beforeSatis = z.getZonRelatedSatisfaction();
        gm.addFacility(new ForestFactory(gm).createFacility(new Coordinate(2, 3)));
        double afterSatis = z.getZonRelatedSatisfaction();

        System.out.println(gm.printMap());
        assertEquals((int) FOREST_BASE_EFFECT, (int) (afterSatis - beforeSatis));
    }

    @Test
    void TestForestEffectElevenYears() throws OperationException {
        // conditions are tested in forest class test.
        // here only test if the change appears.
        Zone z = new ResidentialZoneFactory(gm).createZone(new Coordinate(2, 2));
        gm.addZone(z);
        double beforeSatis = z.getZonRelatedSatisfaction();
        gm.addFacility(new ForestFactory(gm).createFacility(new Coordinate(2, 3)));
        for (int i = 0; i < 10; i++) {
            gm.regularUpdate(366, null);
        }
        double afterSatis = z.getZonRelatedSatisfaction();
        assertEquals((int) FOREST_BASE_EFFECT * 10, (int) (afterSatis - beforeSatis));

        gm.regularUpdate(365, null);
        afterSatis = z.getZonRelatedSatisfaction();
        assertEquals((int) FOREST_BASE_EFFECT * 10, (int) (afterSatis - beforeSatis));

        assertEquals(0, gm.calculateSpend());
    }

    @Test
    void TestForestEffectReverseIndustry() throws OperationException {
        // conditions are tested in forest class test.
        // here only test if the change appears.
        Zone z = new ResidentialZoneFactory(gm).createZone(new Coordinate(2, 2));
        gm.addZone(z);
        Zone z2 = new IndustrialZoneFactory(gm).createZone(new Coordinate(2, 4));
        gm.addZone(z2);

        double beforeSatis = z.getZonRelatedSatisfaction();

        gm.addFacility(new ForestFactory(gm).createFacility(new Coordinate(2, 3)));
        double afterSatis = z.getZonRelatedSatisfaction();
        System.out.println(gm.printMap());
        assertEquals((int) FOREST_BASE_EFFECT, (int) (afterSatis - beforeSatis));
    }

    // school
    @Test
    void TestSchoolConnectedToMasterRoad() throws OperationException {
        Zone z = new ResidentialZoneFactory(gm).createZone(new Coordinate(3, 1));
        gm.addZone(z);
        double beforeBalance = gm.getCityStatistics().getBudget().getBalance();
        School r = (School) new SchoolFactory(gm).createFacility(new Coordinate(2, 3));
        gm.addFacility(r);
        double afterBalance = gm.getCityStatistics().getBudget().getBalance();

        for (int i = 0; i < 10; i++) {
            gm.regularUpdate(1, null);
        }
        System.out.println(gm.printMap());
        System.out.println(z.getCitizens());
        boolean atLeast1PersonHasSecondaryDegree = false;
        for (Citizen citizen : z.getCitizens()) {
            if (citizen.getLevelOfEducation() == LevelOfEducation.SCHOOL) {
                atLeast1PersonHasSecondaryDegree = true;
                break;
            }
        }
        assertTrue(atLeast1PersonHasSecondaryDegree);
        assertEquals(SCHOOL_ONE_TIME_COST, beforeBalance - afterBalance);
    }

    @Test
    void TestSchoolNotConnectedToMasterRoad() throws OperationException {
        Zone z = new ResidentialZoneFactory(gm).createZone(new Coordinate(3, 1));
        gm.addZone(z);
        double beforeBalance = gm.getCityStatistics().getBudget().getBalance();
        School r = (School) new SchoolFactory(gm).createFacility(new Coordinate(1, 3));
        gm.addFacility(r);
        double afterBalance = gm.getCityStatistics().getBudget().getBalance();

        for (int i = 0; i < 10; i++) {
            gm.regularUpdate(1, null);
        }
        System.out.println(gm.printMap());
        System.out.println(z.getCitizens());
        boolean atLeast1PersonHasSecondaryDegree = false;
        for (Citizen citizen : z.getCitizens()) {
            if (citizen.getLevelOfEducation() == LevelOfEducation.SCHOOL) {
                atLeast1PersonHasSecondaryDegree = true;
                break;
            }
        }
        assertFalse(atLeast1PersonHasSecondaryDegree);
        assertEquals(SCHOOL_ONE_TIME_COST, beforeBalance - afterBalance);
    }

    // university
    @Test
    void TestUniversityConnectedToMasterRoad() throws OperationException {
        Zone z = new ResidentialZoneFactory(gm).createZone(new Coordinate(3, 1));
        gm.addZone(z);
        double beforeBalance = gm.getCityStatistics().getBudget().getBalance();
        University r = (University) new UniversityFactory(gm).createFacility(new Coordinate(2, 5));
        gm.addFacility(r);
        double afterBalance = gm.getCityStatistics().getBudget().getBalance();

        for (int i = 0; i < 10; i++) {
            gm.regularUpdate(1, null);
        }
        System.out.println(gm.printMap());
        System.out.println(z.getCitizens());
        boolean atLeast1PersonHasHigherDegree = false;
        for (Citizen citizen : z.getCitizens()) {
            if (citizen.getLevelOfEducation() == LevelOfEducation.UNIVERSITY) {
                atLeast1PersonHasHigherDegree = true;
                break;
            }
        }
        assertTrue(atLeast1PersonHasHigherDegree);
        assertEquals(UNIVERSITY_ONE_TIME_COST, beforeBalance - afterBalance);
    }

    @Test
    void TestUniversityNotConnectedToMasterRoad() throws OperationException {
        Zone z = new ResidentialZoneFactory(gm).createZone(new Coordinate(3, 1));
        gm.addZone(z);
        double beforeBalance = gm.getCityStatistics().getBudget().getBalance();
        University r = (University) new UniversityFactory(gm).createFacility(new Coordinate(1, 5));
        gm.addFacility(r);
        double afterBalance = gm.getCityStatistics().getBudget().getBalance();

        for (int i = 0; i < 10; i++) {
            gm.regularUpdate(1, null);
        }
        System.out.println(gm.printMap());
        System.out.println(z.getCitizens());
        boolean atLeast1PersonHasHigherDegree = false;
        for (Citizen citizen : z.getCitizens()) {
            if (citizen.getLevelOfEducation() == LevelOfEducation.UNIVERSITY) {
                atLeast1PersonHasHigherDegree = true;
                break;
            }
        }
        assertFalse(atLeast1PersonHasHigherDegree);
        assertEquals(UNIVERSITY_ONE_TIME_COST, beforeBalance - afterBalance);
    }

    // demolition road

    @Test
    void TestDemolishUnlinkedRoad() throws OperationException {
        Zone z = new ResidentialZoneFactory(gm).createZone(new Coordinate(3, 1));
        gm.addZone(z);
        double beforeBalance = gm.getCityStatistics().getBudget().getBalance();
        System.out.println(gm.printMap());

        try {
            gm.removeBuildable(new Coordinate(3, 2));
        } catch (OperationException e) {
            throw new RuntimeException(e);
        }
        double afterBalance = gm.getCityStatistics().getBudget().getBalance();
        assertFalse(gm.isPlotAvailable(z));
        assertEquals(ROAD_ONE_TIME_COST * RETURN_RATE, afterBalance - beforeBalance);
    }

    @Test
    void TestDemolishLinkedRoad() throws OperationException {
        Zone z = new ResidentialZoneFactory(gm).createZone(new Coordinate(2, 2));
        gm.addZone(z);
        System.out.println(gm.printMap());

        double beforeBalance = gm.getCityStatistics().getBudget().getBalance();
        try {
            gm.removeBuildable(new Coordinate(3, 3));
        } catch (OperationException e) {
            System.out.println(e.getMessage());
        }
        double afterBalance = gm.getCityStatistics().getBudget().getBalance();

        assertEquals(0, beforeBalance - afterBalance);
    }

    // demolition service building

    @Test
    void TestDemolishPoliceNotAffectingSatisfaction() throws OperationException {
        Zone z = new ResidentialZoneFactory(gm).createZone(new Coordinate(3, 9));
        gm.addZone(z);
        Facility f = new PoliceFactory(gm).createFacility(new Coordinate(2, 2));
        gm.addFacility(f);
        System.out.println(gm.printMap());
        double beforeBalance = gm.getCityStatistics().getBudget().getBalance();
        double beforeSatis = z.getZonRelatedSatisfaction();
        try {
            gm.removeBuildable(new Coordinate(2, 2));
        } catch (OperationException e) {
            throw new RuntimeException(e);
        }
        double afterBalance = gm.getCityStatistics().getBudget().getBalance();
        double afterSatis = z.getZonRelatedSatisfaction();
        assertEquals(POLICE_ONE_TIME_COST * RETURN_RATE, afterBalance - beforeBalance);
        assertEquals(0, beforeSatis - afterSatis);

    }

    @Test
    void TestDemolishStadiumNotAffectingSatisfaction() throws OperationException {
        Zone z = new ResidentialZoneFactory(gm).createZone(new Coordinate(3, 9));
        gm.addZone(z);
        Facility f = new StadiumFactory(gm).createFacility(new Coordinate(2, 0));
        gm.addFacility(f);
        System.out.println(gm.printMap());
        double beforeBalance = gm.getCityStatistics().getBudget().getBalance();
        double beforeSatis = z.getZonRelatedSatisfaction();
        try {
            gm.removeBuildable(new Coordinate(2, 0));
        } catch (OperationException e) {
            throw new RuntimeException(e);
        }
        double afterBalance = gm.getCityStatistics().getBudget().getBalance();
        double afterSatis = z.getZonRelatedSatisfaction();
        assertEquals(STADIUM_ONE_TIME_COST * RETURN_RATE, afterBalance - beforeBalance);
        assertEquals(0, beforeSatis - afterSatis);
    }

    @Test
    void TestDemolishPoliceAffectingSatisfaction() throws OperationException {
        Zone z = new ResidentialZoneFactory(gm).createZone(new Coordinate(3, 1));
        gm.addZone(z);
        Facility f = new PoliceFactory(gm).createFacility(new Coordinate(2, 2));
        gm.addFacility(f);
        System.out.println(gm.printMap());
        double beforeBalance = gm.getCityStatistics().getBudget().getBalance();
        double beforeSatis = z.getZonRelatedSatisfaction();
        try {
            gm.removeBuildable(new Coordinate(2, 2));
        } catch (OperationException e) {
            throw new RuntimeException(e);
        }
        double afterBalance = gm.getCityStatistics().getBudget().getBalance();
        double afterSatis = z.getZonRelatedSatisfaction();
        assertEquals(POLICE_ONE_TIME_COST * RETURN_RATE, afterBalance - beforeBalance);
        assertEquals(POLICE_BASE_EFFECT, beforeSatis - afterSatis);
    }

    @Test
    void TestDemolishStadiumAffectingSatisfaction() throws OperationException {
        Zone z = new ResidentialZoneFactory(gm).createZone(new Coordinate(2, 2));
        gm.addZone(z);
        Facility f = new StadiumFactory(gm).createFacility(new Coordinate(2, 0));
        gm.addFacility(f);
        System.out.println(gm.printMap());
        double beforeBalance = gm.getCityStatistics().getBudget().getBalance();
        double beforeSatis = z.getZonRelatedSatisfaction();
        try {
            gm.removeBuildable(new Coordinate(2, 0));
        } catch (OperationException e) {
            throw new RuntimeException(e);
        }
        double afterBalance = gm.getCityStatistics().getBudget().getBalance();
        double afterSatis = z.getZonRelatedSatisfaction();
        assertEquals(STADIUM_ONE_TIME_COST * RETURN_RATE, afterBalance - beforeBalance);
        assertEquals(STADIUM_BASE_EFFECT, beforeSatis - afterSatis);
    }

    // commercial zone

    @Test
    void TestAddingCommercialZoneOnEmptyField() throws OperationException {
        double beforeBalance = gm.getCityStatistics().getBudget().getBalance();
        Zone z = new CommercialZoneFactory(gm).createZone(new Coordinate(3, 1));
        gm.addZone(z);
        double afterBalance = gm.getCityStatistics().getBudget().getBalance();
        System.out.println(gm.printMap());
        assertEquals(ZONE_ONE_TIME_COST, beforeBalance - afterBalance);
        assertEquals(CommercialZone.class, gm.getMap()[3][1].getClass());
    }

    @Test
    void TestAddingCommercialZoneOnNonEmptyField() throws OperationException {
        Zone r = new ResidentialZoneFactory(gm).createZone(new Coordinate(3, 1));
        gm.addZone(r);
        double beforeBalance = gm.getCityStatistics().getBudget().getBalance();
        Zone c = new CommercialZoneFactory(gm).createZone(new Coordinate(3, 1));
        try {
            gm.addZone(c);
        } catch (OperationException e) {
            System.out.println(e.getMessage());
        }
        double afterBalance = gm.getCityStatistics().getBudget().getBalance();
        System.out.println(gm.printMap());
        assertEquals(0, beforeBalance - afterBalance);
        assertEquals(ResidentialZone.class, gm.getMap()[3][1].getClass());
    }

    @Test
    void TestDeZoningCommercialZoneWithNoBuildingOnIt() throws OperationException {
        Zone z = new CommercialZoneFactory(gm).createZone(new Coordinate(3, 1));
        gm.addZone(z);
        try {
            gm.removeBuildable(new Coordinate(3, 1));
        } catch (OperationException e) {
            throw new RuntimeException(e);
        }
        assertNull(gm.getMap()[3][1]);
    }

    @Test
    void TestDeZoningCommercialZoneWithBuildingOnIt() throws OperationException {
        Zone z = new CommercialZoneFactory(gm).createZone(new Coordinate(3, 1));
        gm.addZone(z);
        for (int i = 0; i < 10; i++) {
            gm.regularUpdate(6, null);
        }
        try {
            gm.removeBuildable(new Coordinate(3, 1));
        } catch (OperationException e) {
            System.out.println(e.getMessage());
        }
        assertEquals(CommercialZone.class, gm.getMap()[3][1].getClass());
    }

    // industrial zone

    @Test
    void TestAddingIndustrialZoneOnEmptyField() throws OperationException {
        double beforeBalance = gm.getCityStatistics().getBudget().getBalance();
        Zone z = new IndustrialZoneFactory(gm).createZone(new Coordinate(3, 1));
        gm.addZone(z);
        double afterBalance = gm.getCityStatistics().getBudget().getBalance();
        System.out.println(gm.printMap());
        assertEquals(ZONE_ONE_TIME_COST, beforeBalance - afterBalance);
        assertEquals(IndustrialZone.class, gm.getMap()[3][1].getClass());
    }

    @Test
    void TestAddingIndustrialZoneOnNonEmptyField() throws OperationException {
        Zone r = new ResidentialZoneFactory(gm).createZone(new Coordinate(3, 1));
        gm.addZone(r);
        double beforeBalance = gm.getCityStatistics().getBudget().getBalance();
        Zone i = new IndustrialZoneFactory(gm).createZone(new Coordinate(3, 1));
        try {
            gm.addZone(i);
        } catch (OperationException e) {
            System.out.println(e.getMessage());
        }
        double afterBalance = gm.getCityStatistics().getBudget().getBalance();
        System.out.println(gm.printMap());
        assertEquals(0, beforeBalance - afterBalance);
        assertEquals(ResidentialZone.class, gm.getMap()[3][1].getClass());
    }

    @Test
    void TestDeZoningIndustrialZoneWithNoBuildingOnIt() throws OperationException {
        Zone z = new IndustrialZoneFactory(gm).createZone(new Coordinate(3, 1));
        gm.addZone(z);
        try {
            gm.removeBuildable(new Coordinate(3, 1));
        } catch (OperationException e) {
            throw new RuntimeException(e);
        }
        assertNull(gm.getMap()[3][1]);
    }

    @Test
    void TestDeZoningIndustrialZoneWithBuildingOnIt() throws OperationException {
        Zone z = new IndustrialZoneFactory(gm).createZone(new Coordinate(3, 1));
        gm.addZone(z);
        for (int i = 0; i < 10; i++) {
            gm.regularUpdate(6, null);
        }
        try {
            gm.removeBuildable(new Coordinate(3, 1));
        } catch (OperationException e) {
            System.out.println(e.getMessage());
        }
        assertEquals(IndustrialZone.class, gm.getMap()[3][1].getClass());
    }

    /////
}