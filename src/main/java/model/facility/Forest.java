package model.facility;

import model.common.Buildable;
import model.common.Coordinate;
import model.common.Dimension;
import model.common.SideEffect;
import model.util.BuildableType;
import model.util.Date;
import model.zone.Zone;

public class Forest extends EffectualFacility {

    private int age;
    private Date birthday;
    private Date lastUpdate;

    public Forest(int oneTimeCost, int maintenanceFee, Coordinate coordinate, Dimension dimension, float influenceRadius, Date birthday) {
        super(oneTimeCost, maintenanceFee, coordinate, dimension, influenceRadius);
        age = 0;
        this.birthday = birthday;
        this.lastUpdate = birthday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public void incAge(Date now) {
        if (now.dateDifference(lastUpdate).get("years") > 1) {
            lastUpdate = now;
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

    @Override
    public void effect(Zone zone, Buildable[][] map) {
        if (condition(zone, map)) {
            zone.getEffectedBy().add(this);
            zone.updateForestEffect(getPositiveEffect());
            for(Buildable bad: zone.getEffectedBy()) {
                if (bad.getBuildableType() == BuildableType.INDUSTRIAL &&
                        isInBetween(zone.getCoordinate(), this.getCoordinate(), bad.getCoordinate())) {
                    ((SideEffect)bad).reverseEffect(zone, map);
                }
            }
        }
    }

    @Override
    public void reverseEffect(Zone zone, Buildable[][] map) {
        if (condition(zone, map)) {
            zone.getEffectedBy().remove(this);
            zone.updateForestEffect(-getPositiveEffect());
            for(Buildable bad: zone.getEffectedBy()) {
                if (bad.getBuildableType() == BuildableType.INDUSTRIAL &&
                        isInBetween(zone.getCoordinate(), this.getCoordinate(), bad.getCoordinate())) {
                    ((SideEffect)bad).effect(zone, map);
                }
            }
        }
    }

    @Override
    public boolean condition(Zone zone, Buildable[][] map) {
        return (hasDirectView(zone, map));
    }

    private double getPositiveEffect() {
        return age / 10.0;
    }

    private boolean hasDirectView(Zone zone, Buildable[][] map) {
        Coordinate zC = zone.getCoordinate();
        Coordinate self = this.getCoordinate();
        // check horizontally
        if (zC.getRow() == self.getRow()) {
            int diff = self.getCol() - zC.getCol();
            if (diff > 3) {return false;}
            if (diff < 0) { // self在左边
                for (int i = 1; i < -diff; i++) {
                    if (map[self.getRow()][self.getCol()+i] != null) {
                        return false;
                    }
                }
            } else {
                for (int i = 1; i < diff; i++) {
                    if (map[self.getRow()][self.getCol()-i] != null) {
                        return false;
                    }
                }
            }
        } else if (zC.getCol() == self.getCol()) {
            int diff = self.getRow() - zC.getRow();
            if (diff > 3) {return false;}
            if (diff < 0) { // self上边
                for (int i = 1; i < -diff; i++) {
                    if (map[self.getRow()+i][self.getCol()] != null) {
                        return false;
                    }
                }
            } else {
                for (int i = 1; i < diff; i++) {
                    if (map[self.getRow()-i][self.getCol()] != null) {
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
                (a.getCoordinate().getCol() - b.getCoordinate().getCol())==1 &&
                (c.getCoordinate().getCol() - b.getCoordinate().getCol())==1) ||
                (a.getCoordinate().getCol() == b.getCoordinate().getCol() &&
                b.getCoordinate().getCol() == c.getCoordinate().getCol() &&
                Math.abs(a.getCoordinate().getRow() - b.getCoordinate().getRow())==1 &&
                Math.abs(c.getCoordinate().getRow() - b.getCoordinate().getRow())==1);
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