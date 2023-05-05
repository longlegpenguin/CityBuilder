package model.facility;

import model.GameModel;
import model.common.*;
import model.util.BuildableType;
import model.util.Date;
import model.zone.IndustrialZone;
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
        totalEffectCnt = 0;
        grew = true;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void incAge(Date now) {
        if (now.dateDifference(lastUpdate).get("years") >= 1) {
            lastUpdate = now;
            if (age < 10) {
                grew = true;
            }
            age += 1;
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

    public LinkedList<SideEffect> getBadEffectIndustrial(Zone zone, GameModel gm) {
        LinkedList<SideEffect> iz = new LinkedList<>();
        for (Buildable buildable :
                gm.getZoneBuildable()) {
            if (buildable.getBuildableType() == BuildableType.INDUSTRIAL) {
                SideEffect z = (SideEffect) buildable;
                if (z.condition(zone, gm)) {
                    iz.add(z);
                }
            }
        }
        return iz;
    }

    @Override
    public void effect(Zone zone, GameModel gm) {
        if (condition(zone, gm) && grew) {
//            System.out.println("Forest grows");
//            System.out.println(zone.getStatistics().getSatisfaction().getForestEffect() + getPositiveEffect());
            zone.updateForestEffect(zone.getStatistics().getSatisfaction().getForestEffect() + getPositiveEffect());
            System.out.println("Forest effect: " + zone.getStatistics().getSatisfaction().getForestEffect());
            if (age == 1) {
                for (SideEffect s :
                        getBadEffectIndustrial(zone, gm)) {
                    Buildable bad = (Buildable) s;
                    if (isInBetween(zone.getCoordinate(), this.getCoordinate(), bad.getCoordinate())) {
                        s.reverseEffect(zone, gm);
                        System.out.println("Bad effects removed by Forest...");
                    }
                }
            }
            totalEffectCnt += getPositiveEffect();
            grew = false;
        }
    }

    @Override
    public void reverseEffect(Zone zone, GameModel gm) {
        if (condition(zone, gm)) {
            zone.getEffectedBy().remove(this);
            zone.updateForestEffect(zone.getStatistics().getSatisfaction().getForestEffect() - totalEffectCnt);
            for (SideEffect s :
                    getBadEffectIndustrial(zone, gm)) {
                Buildable bad = (Buildable) s;
                if (isInBetween(zone.getCoordinate(), this.getCoordinate(), bad.getCoordinate())) {
                    s.effect(zone, gm);
                }
            }
        }
    }

    @Override
    public boolean condition(Zone zone, GameModel gm) {
        return (hasDirectView(zone, gm.getMap()));
    }

    private double getPositiveEffect() {
        return Constants.FOREST_BASE_EFFECT ;
    }

    private boolean hasDirectView(Zone zone, Buildable[][] map) {
        Coordinate zC = zone.getCoordinate();
        Coordinate self = this.getCoordinate();
        // check horizontally
        if (zC.getRow() == self.getRow()) {
            int diff = self.getCol() - zC.getCol();
            if (diff > 3) {
                return false;
            }
            if (diff < 0) { // self在左边
                for (int i = 1; i < -diff; i++) {
                    if (map[self.getRow()][self.getCol() + i] != null) {
                        return false;
                    }
                }
            } else {
                for (int i = 1; i < diff; i++) {
                    if (map[self.getRow()][self.getCol() - i] != null) {
                        return false;
                    }
                }
            }
        } else if (zC.getCol() == self.getCol()) {
            int diff = self.getRow() - zC.getRow();
            if (diff > 3) {
                return false;
            }
            if (diff < 0) { // self上边
                for (int i = 1; i < -diff; i++) {
                    if (map[self.getRow() + i][self.getCol()] != null) {
                        return false;
                    }
                }
            } else {
                for (int i = 1; i < diff; i++) {
                    if (map[self.getRow() - i][self.getCol()] != null) {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }
        return true;

    }

    private boolean isInBetween(Coordinate a, Coordinate b, Coordinate c) {
        boolean vertically = (a.getCol() == b.getCol() && a.getCol() == c.getCol()) &&
                ((b.getRow() > a.getRow() && b.getRow() < c.getRow()) ||
                        (a.getRow() > b.getRow() && c.getRow() < b.getRow()));
        boolean horizontally = (a.getRow() == b.getRow() && a.getRow() == c.getRow()) &&
                ((b.getCol() > a.getCol() && b.getCol() < c.getCol()) ||
                        (a.getCol() > b.getCol() && c.getCol() < b.getCol()));
        return vertically || horizontally;
    }

    private boolean isInOneLine(Buildable a, Buildable b, Buildable c) {
        return (a.getCoordinate().getRow() == b.getCoordinate().getRow() &&
                b.getCoordinate().getRow() == c.getCoordinate().getRow() &&
                (a.getCoordinate().getCol() - b.getCoordinate().getCol()) == 1 &&
                (c.getCoordinate().getCol() - b.getCoordinate().getCol()) == 1) ||
                (a.getCoordinate().getCol() == b.getCoordinate().getCol() &&
                        b.getCoordinate().getCol() == c.getCoordinate().getCol() &&
                        Math.abs(a.getCoordinate().getRow() - b.getCoordinate().getRow()) == 1 &&
                        Math.abs(c.getCoordinate().getRow() - b.getCoordinate().getRow()) == 1);
    }

    private boolean isInMapCoordinate(Coordinate coordinate, Buildable[][] map) {
        return isInMap(coordinate.getRow(), coordinate.getCol(), map);
    }

    private boolean isGoalCoordinate(Coordinate current, Coordinate goalCoordinate) {
        return current.equals(goalCoordinate);
    }

    private boolean isEmpty(int sRow, int sCol, Buildable[][] map) {
        return map[sRow][sCol] == null;
    }

    private boolean isInMap(int sRow, int sCol, Buildable[][] map) {
        return sRow < map.length && sRow >= 0 &&
                sCol < map[0].length && sCol >= 0;
    }
}
