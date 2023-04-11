package model.util;

import model.common.Buildable;
import model.GameModel;
import model.facility.Road;
import model.common.Coordinate;
import model.common.Dimension;
import model.zone.ResidentialZoneFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PathFinderTest {

    /*
     0 1 2 3 4 5 6 7 8 9
   0 o r x C C C x x x x
   1 x r x C x C x x x x
   2 x r r r C C x x x x
   3 x x x r x x x x x x
   4 x x x r r o x x x x

   1,1; 2,1; 2,2; 2,3; 3,3; 4,3; 4,4;
   1,3; 0,3; 0,4; 0,5; 1,5; 2,5; 2,4
     */
    Buildable map[][];
    PathFinder pf;
    @BeforeEach
    void setUp() {
        GameModel gm = new GameModel(5, 10);
        map = new Buildable[5][10];
        map[0][0] = new ResidentialZoneFactory(gm).createZone(new Coordinate(0, 0));
        map[1][1] = new Road(1,1, new Coordinate(1,1), new Dimension(1,1));
        map[2][1] = new Road(1,1, new Coordinate(2,1), new Dimension(1,1));
        map[2][2] = new Road(1,1, new Coordinate(2,2), new Dimension(1,1));
        map[2][3] = new Road(1,1, new Coordinate(2,3), new Dimension(1,1));
        map[3][3] = new Road(1,1, new Coordinate(3,3), new Dimension(1,1));
        map[4][3] = new Road(1,1, new Coordinate(4,3), new Dimension(1,1));
        map[4][4] = new Road(1,1, new Coordinate(4,4), new Dimension(1,1));
        map[4][5] = new ResidentialZoneFactory(gm).createZone(new Coordinate(4, 5));
        pf = new PathFinder(map);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void euclideanDistance() {
    }

    @Test
    void manhattanDistanceNoPath() {
        assertEquals(pf.manhattanDistance(map[0][0], map[4][5]), -1);
    }
    @Test
    void manhattanDistanceHasPath() {
        map[0][1] = new Road(1,1, new Coordinate(0,1), new Dimension(1,1));
        assertEquals(8, pf.manhattanDistance(map[0][0], map[4][5]));
    }

//    1,3; 0,3; 0,4; 0,5; 1,5; 2,5; 2,4
    @Test
    void manhattanDistanceHasCycle() {
        map[0][1] = new Road(1,1, new Coordinate(0,1), new Dimension(1,1));
        map[1][3] = new Road(1,1, new Coordinate(1,3), new Dimension(1,1));
        map[0][3] = new Road(1,1, new Coordinate(0,3), new Dimension(1,1));
        map[0][4] = new Road(1,1, new Coordinate(0,4), new Dimension(1,1));
        map[0][5] = new Road(1,1, new Coordinate(0,5), new Dimension(1,1));
        map[1][5] = new Road(1,1, new Coordinate(0,5), new Dimension(1,1));
        map[2][4] = new Road(1,1, new Coordinate(0,4), new Dimension(1,1));
        map[2][5] = new Road(1,1, new Coordinate(0,5), new Dimension(1,1));

        assertEquals(8, pf.manhattanDistance(map[0][0], map[4][5]));
    }
}