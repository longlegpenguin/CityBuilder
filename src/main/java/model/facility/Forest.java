package model.facility;

import model.GameModel;
import model.common.*;
import model.util.BuildableType;
import model.util.Date;
import model.util.PathFinder;
import model.zone.Zone;

import java.util.LinkedList;

public class Forest extends EffectualFacility {

    private int age;
    private final Date birthday;
    private Date lastUpdate;
    private double totalEffectCnt;
    private boolean grew;

    public Forest(int oneTimeCost, int maintenanceFee, Coordinate coordinate, Dimension dimension, float influenceRadius, Date birthday) {
        super(oneTimeCost, maintenanceFee, coordinate, dimension, influenceRadius);
        age = 1;
        this.birthday = birthday;
        this.lastUpdate = birthday;
        totalEffectCnt = getPositiveEffect();
        grew = true;
    }

    public int getAge() {
        return age;
    }

    public void setAgeToTen() {
        this.age = 10;
    }

    public void setTotalEffectCntToTenYears() {
        this.totalEffectCnt = getPositiveEffect()*10;
    }

    /**
     * Increments forests age by one, if one year passed.
     * @param now the current time
     */
    public void incAge(Date now) {
        if (now.dateDifference(lastUpdate).get("years") >= 1) {
            lastUpdate = now;
            if (age < 10) {
                grew = true;
            }
            age += 1;
            System.out.println("forest"+age);
        }
    }

    @Override
    public BuildableType getBuildableType() {
        return BuildableType.FOREST;
    }

    @Override
    public boolean isUnderConstruction() {
        return false;
    }

    /**
     * Filter out industrial zones lies between a given zone and the forest.
     * @param zone given zone
     * @param gm gme model
     * @return the list of bad zones
     */
    public LinkedList<SideEffect> getBadEffectIndustrial(Zone zone, GameModel gm) {
        LinkedList<SideEffect> iz = new LinkedList<>();
        for (Buildable buildable :
                gm.getZoneBuildable()) {
            if (buildable.getBuildableType() == BuildableType.INDUSTRIAL &&
                    isInBetween(zone.getCoordinate(), this.getCoordinate(), buildable.getCoordinate())) {
                SideEffect z = (SideEffect) buildable;
                if (z.condition(zone, gm)) {
                    iz.add(z);
                }
            }
        }
        return iz;
    }

    public void grewEffect(Zone zone, GameModel gm) {
        if (condition(zone, gm)) {
            zone.updateForestEffect(zone.getStatistics().getSatisfaction().getForestEffect() - totalEffectCnt);
            totalEffectCnt += getPositiveEffect();
            zone.updateForestEffect(zone.getStatistics().getSatisfaction().getForestEffect() + totalEffectCnt);
            System.out.println("grew effect" + totalEffectCnt);
        }
    }
    @Override
    public void effect(Zone zone, GameModel gm) {
        if (condition(zone, gm)) {
            System.out.println("Forest effect" + totalEffectCnt);
            zone.updateForestEffect(zone.getStatistics().getSatisfaction().getForestEffect() + totalEffectCnt);
            for (SideEffect s : getBadEffectIndustrial(zone, gm)) {
                s.reverseEffect(zone, gm);
                System.out.println("Bad effects removed by Forest...");
            }
        }
//        if (condition(zone, gm)) {
//            zone.updateForestEffect(zone.getStatistics().getSatisfaction().getForestEffect() - totalEffectCnt);
//            if (age == 1) {
////                for (SideEffect s : getBadEffectIndustrial(zone, gm)) {
////                    s.reverseEffect(zone, gm);
////                    System.out.println("Bad effects removed by Forest...");
////                }
//            }
//            totalEffectCnt += getPositiveEffect();
//            zone.updateForestEffect(zone.getStatistics().getSatisfaction().getForestEffect() + totalEffectCnt);
//        }
//        if (condition(zone, gm) && grew) {
//            zone.updateForestEffect(zone.getStatistics().getSatisfaction().getForestEffect() + getPositiveEffect());
//            if (age == 1) {
//                for (SideEffect s : getBadEffectIndustrial(zone, gm)) {
//                    s.reverseEffect(zone, gm);
//                    System.out.println("Bad effects removed by Forest...");
//                }
//            }
//            totalEffectCnt += getPositiveEffect();
//            grew = false;
//        } else {
//
//        }
    }

    @Override
    public void reverseEffect(Zone zone, GameModel gm) {
        if (condition(zone, gm)) {
            zone.updateForestEffect(zone.getStatistics().getSatisfaction().getForestEffect() - totalEffectCnt);
            for (SideEffect s : getBadEffectIndustrial(zone, gm)) {
                s.effect(zone, gm);
                System.out.println("Bad effects back...");
                System.out.println(s);
            }
        }
    }

    @Override
    public boolean condition(Zone zone, GameModel gm) {
        return (hasDirectView(zone, gm.getMap()) &&
                new PathFinder(gm.getMap()).squareDistance(zone, this) < influenceRadius) &&
                zone.isConnected();
    }

    private double getPositiveEffect() {
        return Constants.FOREST_BASE_EFFECT ;
    }

    /**
     * Checks if a zone can see the forest directly
     * @param zone zone to be checked
     * @param map map of city
     * @return true if can
     */
    private boolean hasDirectView(Zone zone, Buildable[][] map) {
        Coordinate zC = zone.getCoordinate();
        Coordinate self = this.getCoordinate();

        if (zC.getRow() == self.getRow()) { // check horizontally
            if (horizontallyBlocked(map, zC, self)) {
                return false;
            }
        } else if (zC.getCol() == self.getCol()) { // check vertically
            if (verticallyBlocked(map, zC, self)) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * Checks if b is in one line and betwee a and c
     * @param a Coordinate
     * @param b Coordinate
     * @param c Coordinate
     * @return true if is between
     */
    private boolean isInBetween(Coordinate a, Coordinate b, Coordinate c) {
        boolean vertically = (a.getCol() == b.getCol() && a.getCol() == c.getCol()) &&
                ((b.getRow() > a.getRow() && b.getRow() < c.getRow()) ||
                        (a.getRow() > b.getRow() && c.getRow() < b.getRow()));
        boolean horizontally = (a.getRow() == b.getRow() && a.getRow() == c.getRow()) &&
                ((b.getCol() > a.getCol() && b.getCol() < c.getCol()) ||
                        (a.getCol() > b.getCol() && c.getCol() < b.getCol()));
        return vertically || horizontally;
    }

    /**
     * Checks if there is a real building at the given place
     * @param sRow row coordinate
     * @param sCol column coordinate
     * @param map map of the city
     * @return true if there is one.
     */
    private boolean isNotEmpty(int sRow, int sCol, Buildable[][] map) {
        return map[sRow][sCol] != null && map[sRow][sCol].getBuildableType() != BuildableType.ROAD;
    }


    private boolean horizontallyBlocked(Buildable[][] map, Coordinate zC, Coordinate self) {
        int diff = self.getCol() - zC.getCol();
        if (diff > 3) {
            return true;
        }
        if (diff < 0) { // self on the left
            for (int i = 1; i < -diff; i++) {
                if (isNotEmpty(self.getRow(), self.getCol() + i, map)) {
                    return true;
                }
            }
        } else {
            for (int i = 1; i < diff; i++) {
                if (isNotEmpty(self.getRow(), self.getCol() - i, map)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean verticallyBlocked(Buildable[][] map, Coordinate zC, Coordinate self) {
        int diff = self.getRow() - zC.getRow();
        if (diff > 3) {
            return true;
        }
        if (diff < 0) { // self on above
            for (int i = 1; i < -diff; i++) {
                if (isNotEmpty(self.getRow() + i, self.getCol(), map)) {
                    return true;
                }
            }
        } else {
            for (int i = 1; i < diff; i++) {
                if (isNotEmpty(self.getRow() - i, self.getCol(), map)) {
                    return true;
                }
            }
        }
        return false;
    }

}
