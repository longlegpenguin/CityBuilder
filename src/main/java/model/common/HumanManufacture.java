package model.common;

import model.GameModel;
import model.facility.Education;
import model.util.BuildableType;
import model.util.LevelOfEducation;
import model.util.PathFinder;
import model.zone.Zone;

import java.util.ArrayList;
import java.util.Random;

public class HumanManufacture {

    public static int startingNrCitizens = 20;

    /**
     * Getting an available random Residential zone.
     *
     * @param gm
     * @return Residential zone if found, otherwise null.
     */
    public static Zone getLivingPlace(GameModel gm) {
        ArrayList<Zone> availableResidentialZones = new ArrayList<>();
        for (Buildable buildable : gm.getZoneBuildable()) {
            Zone zone = (Zone) buildable;
            if (zone.getBuildableType() == BuildableType.RESIDENTIAL &&
                    zone.getStatistics().getPopulation() < zone.getStatistics().getCapacity()) {
                availableResidentialZones.add(zone);
            }
        }
        if (availableResidentialZones.size() != 0) {
            Random rand = new Random();
            int random = rand.nextInt(availableResidentialZones.size());
            return availableResidentialZones.get(random);
        }
        return null;
    }

    /**
     * Getting the closest working place (zone) from the available ones.
     *
     * @param availableWorkingZones
     * @param livingPlace
     * @param gm
     * @return closest working place (zone) if found, otherwise null.
     */
    public static Zone getClosestWorkingPlace(ArrayList<Zone> availableWorkingZones, Zone livingPlace, GameModel gm) {
        int closestDistance = 100;
        Zone closestZone = null;
        if (availableWorkingZones == null) return null;
        for (Zone zone : availableWorkingZones) {
            int distanceToWork = Citizen.getDistanceLiveWork(gm, zone, livingPlace);
            if (distanceToWork < closestDistance) {
                closestDistance = distanceToWork;
                closestZone = zone;
            }
        }
        return closestZone;
    }

    public static Zone getWorkingPlace(GameModel gm, Zone livingPlace) {
        ArrayList<Zone> availableWorkingZones = new ArrayList<>();
        for (Buildable buildable : gm.getZoneBuildable()) {
            Zone zone = (Zone) buildable;
            int distanceLiveWork = new PathFinder(gm.getMap()).manhattanDistance(zone, livingPlace);
            if ((zone.getBuildableType() == BuildableType.INDUSTRIAL || zone.getBuildableType() == BuildableType.COMMERCIAL) &&
                    zone.getStatistics().getPopulation() < zone.getStatistics().getCapacity() && distanceLiveWork != -1) {
                availableWorkingZones.add(zone);
            }
        }
        return getClosestWorkingPlace(availableWorkingZones, livingPlace, gm);
    }

    /**
     * Returns a random available level of education.
     *
     * @param gm
     * @return
     */
    private static LevelOfEducation getEducationLevel(GameModel gm) {
        Random rand = new Random();
        int random = rand.nextInt(3);
        if (random == 1) {
            int capacityAllSchools = 0;
            for (Buildable buildable : gm.getAllBuildable()) {
                Education education = (Education) buildable;
                if (buildable.getBuildableType() == BuildableType.SCHOOL) {
                    capacityAllSchools += education.getCapacity();
                }
            }
            if (gm.getCityStatistics().getNrCitizenSecondaryEducation(gm.getCityRegistry()) < capacityAllSchools)
                return LevelOfEducation.SCHOOL;
        }

        if (random == 2) {
            int capacityAllUniversities = 0;
            for (Buildable buildable : gm.getAllBuildable()) {
                Education education = (Education) buildable;
                if (buildable.getBuildableType() == BuildableType.UNIVERSITY) {
                    capacityAllUniversities += education.getCapacity();
                }
            }
            if (gm.getCityStatistics().getNrCitizenHigherEducation(gm.getCityRegistry()) < capacityAllUniversities)
                return LevelOfEducation.UNIVERSITY;
        }

        return LevelOfEducation.PRIMARY;
    }

    /**
     * Returns a young citizen, assigns it to a random residential zone (if available), and to the closest workPlace
     * (if available)
     *
     * @param gm
     * @return
     */
    public static Citizen createYoungCitizen(GameModel gm) {
        Zone livingPlace = getLivingPlace(gm);
        Zone workPlace = getWorkingPlace(gm, livingPlace);
        Citizen newCitizen = new Citizen(workPlace, livingPlace, getEducationLevel(gm));
        if (livingPlace != null) livingPlace.addCitizen(newCitizen, gm);
        if (workPlace != null) workPlace.addCitizen(newCitizen, gm);
        return newCitizen;
    }

    /**
     * Creates a young citizen with workPlace and livingPlace given.
     *
     * @param gm
     * @param workPlace
     * @param livingPlace
     */
    public static void createYoungCitizen(GameModel gm, Zone workPlace, Zone livingPlace) {
        Citizen newCitizen = new Citizen(workPlace, livingPlace, getEducationLevel(gm));
        if (livingPlace != null) livingPlace.addCitizen(newCitizen, gm);
        if (workPlace != null) workPlace.addCitizen(newCitizen, gm);
    }

}
