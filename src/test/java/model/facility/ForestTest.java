package model.facility;

import model.GameModel;
import model.common.Buildable;
import model.common.Coordinate;
import model.common.Dimension;
import model.zone.ResidentialZoneFactory;
import model.zone.Zone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ForestTest {


    /*
     0 1 2 3 4 5 6 7 8 9
   0 o r x C Z C x x x x
   1 x r x C x C x x x x
   2 x r r Z F Z Z x x x
   3 x x x r x x x x x x
   4 x x x r r Z x x x x

   1,1; 2,1; 2,2; 2,3; 3,3; 4,3; 4,4;
   1,3; 0,3; 0,4; 0,5; 1,5; 2,5; 2,4
     */

    Buildable[][] map;
    GameModel gm = new GameModel(5, 10);
    Forest forest = (Forest) new ForestFactory(gm).createFacility(new Coordinate(2, 4));

    @BeforeEach
    void setUp() {
        map = new Buildable[5][10];
        map = gm.getMap();
        map[2][4] = forest;
        map[2][6] = new ResidentialZoneFactory(gm).createZone(new Coordinate(2, 6));
        map[2][5] = new ResidentialZoneFactory(gm).createZone(new Coordinate(2, 5));
        map[2][3] = new ResidentialZoneFactory(gm).createZone(new Coordinate(2, 3));
        map[0][4] = new ResidentialZoneFactory(gm).createZone(new Coordinate(0, 4));
        map[4][5] = new ResidentialZoneFactory(gm).createZone(new Coordinate(4, 5));

    }

    @Test
    void condition_Neighbour() {
        assertFalse(forest.condition((Zone) map[2][3], gm));
    }

    @Test
    void condition_OneAway() {
        assertFalse(forest.condition((Zone) map[0][4], gm));
    }

    @Test
    void condition_HasBetween() {
        System.out.println(gm.printMap());
        assertFalse(forest.condition((Zone) map[2][6], gm));
    }

    @Test
    void condition_NotInLine() {
        assertFalse(forest.condition((Zone) map[4][5], gm));
    }


    @Test
    void hasDirectViewYesTwoAway() {
        map[0][1] = new Road(1, 1, new Coordinate(0, 1), new Dimension(1, 1));
        map[3][4] = new Road(1, 1, new Coordinate(3, 4), new Dimension(3, 4));
        map[4][4] = new ResidentialZoneFactory(gm).createZone(new Coordinate(4, 4));
        assertTrue(forest.hasDirectView((Zone) map[4][4], map));
    }

    @Test
    void hasDirectViewNo_OneBetween() {
        map[0][1] = new Road(1, 1, new Coordinate(0, 1), new Dimension(1, 1));
        map[1][3] = new Road(1, 1, new Coordinate(1, 3), new Dimension(1, 1));
        map[0][3] = new Road(1, 1, new Coordinate(0, 3), new Dimension(1, 1));
        map[0][5] = new Road(1, 1, new Coordinate(0, 5), new Dimension(1, 1));
        map[1][5] = new Road(1, 1, new Coordinate(0, 5), new Dimension(1, 1));
        map[2][5] = new Road(1, 1, new Coordinate(0, 5), new Dimension(1, 1));
        assertTrue(forest.hasDirectView((Zone) map[0][4], map));
    }

    @Test
    void hasDirectViewYesNeighbour() {
        assertTrue(forest.hasDirectView((Zone) map[2][3], map));
    }

    @Test
    void hasDirectViewYes_RoadBetween() {
        map[1][4] = new Road(1, 1, new Coordinate(1, 4), new Dimension(1, 1));
        assertTrue(forest.hasDirectView((Zone) map[0][4], map));
    }
}