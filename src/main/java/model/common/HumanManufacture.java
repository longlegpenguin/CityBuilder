package model.common;

import model.GameModel;
import model.facility.School;
import model.facility.University;
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
                    zone.getStatistics().getPopulation() < zone.getCapacity() && zone.isConnected()) {
                availableResidentialZones.add(zone);
            }
        }
        Random rand = new Random();
        int random = rand.nextInt(availableResidentialZones.size());
        return availableResidentialZones.get(random);
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
        Zone closestZone = null;
        int closestDistance = 100;
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
            if ((zone.getBuildableType() == BuildableType.INDUSTRIAL ||
                    zone.getBuildableType() == BuildableType.COMMERCIAL) &&
                    zone.getStatistics().getPopulation() < zone.getCapacity()
                    && distanceLiveWork != -1) {
                availableWorkingZones.add(zone);
            }
        }
        return getClosestWorkingPlace(availableWorkingZones, livingPlace, gm);
    }

    public static ArrayList<School> getAvailableSchools(GameModel gm, Zone livingPlace) {
        ArrayList<School> availableSchools = new ArrayList<>();
        for (Buildable buildable : gm.getAllBuildable()) {
            if (new PathFinder(gm.getMap()).manhattanDistance(buildable, livingPlace) != -1) {
                if (buildable.getClass() == School.class) {
                    availableSchools.add((School) buildable);
                }
            }
        }
        return availableSchools;
    }

    private static boolean areEnoughPlacesSecondaryEducation(GameModel gm, ArrayList<School> availableSchools) {
        return gm.getCityStatistics().getNrCitizenSecondaryEducation(gm.getCityRegistry()) < getCapacitySchools(availableSchools);
    }

    private static int getCapacitySchools(ArrayList<School> schools) {
        int capacityAllSchools = 0;
        for (School school : schools) {
            capacityAllSchools += school.getCapacity();
        }
        return capacityAllSchools;
    }

    public static ArrayList<University> getAvailableUniversities(GameModel gm, Zone livingPlace) {
        ArrayList<University> availableUniversities = new ArrayList<>();
        for (Buildable buildable : gm.getAllBuildable()) {
            if (new PathFinder(gm.getMap()).manhattanDistance(buildable, livingPlace) != -1) {
                if (buildable.getClass() == University.class) {
                    availableUniversities.add((University) buildable);
                }
            }
        }
        return availableUniversities;
    }

    private static boolean areEnoughPlacesHigherEducation(GameModel gm, ArrayList<University> availableUniversities) {
        return gm.getCityStatistics().getNrCitizenHigherEducation(gm.getCityRegistry()) < getCapacityUniversities(availableUniversities);
    }

    private static int getCapacityUniversities(ArrayList<University> universities) {
        int capacityAllUniversities = 0;
        for (University university : universities) {
            capacityAllUniversities += university.getCapacity();
        }
        return capacityAllUniversities;
    }

    /**
     * Returns a random available level of education.
     *
     * @param gm
     * @return
     */
    public static LevelOfEducation getEducationLevel(GameModel gm, Zone livingPlace) {
        Random rand = new Random();
        int random = rand.nextInt(3);

        ArrayList<School> availableSchools = getAvailableSchools(gm, livingPlace);
        ArrayList<University> availableUniversities = getAvailableUniversities(gm, livingPlace);

        if (random == 1 && areEnoughPlacesSecondaryEducation(gm, availableSchools)) {
            return LevelOfEducation.SCHOOL;
        }

        if (random == 2 && areEnoughPlacesHigherEducation(gm, availableUniversities)) {
            return LevelOfEducation.UNIVERSITY;
        }

        return LevelOfEducation.PRIMARY;
    }

    /**
     * Returns a young citizen, assigns it to a random residential zone (if available), and to the closest workPlace
     * (if available)
     *
     * @param gm
     */
    public static void createYoungCitizen(GameModel gm) {
        Zone livingPlace = getLivingPlace(gm);
        Zone workPlace = getWorkingPlace(gm, livingPlace);
        Citizen newCitizen = new Citizen(workPlace, livingPlace, getEducationLevel(gm, livingPlace));
        livingPlace.addCitizen(newCitizen, gm);
        if (workPlace != null) {
            workPlace.addCitizen(newCitizen, gm);
        }
    }

    /**
     * Creates a young citizen with workPlace and livingPlace given.
     *
     * @param gm
     * @param workPlace
     * @param livingPlace
     */
    public static void createYoungCitizen(GameModel gm, Zone workPlace, Zone livingPlace) {
        Citizen newCitizen = new Citizen(workPlace, livingPlace, getEducationLevel(gm, livingPlace));
        livingPlace.addCitizen(newCitizen, gm);
        if (workPlace != null) workPlace.addCitizen(newCitizen, gm);
    }

}
