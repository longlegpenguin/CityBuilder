package model.util;

import model.Buildable;
import model.ResidentialZone;
import model.Road;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PathFinderTest {

    /*
     0 1 2 3 4 5 6 7 8 9
   0 o r x x x x x x x x
   1 x r x x x x x x x x
   2 x r r r x x x x x x
   3 x x x r x x x x x x
   4 x x x r r o x x x x

   1,1; 2,1; 2,2; 2,3; 3,3; 4,3; 4,4;
     */
    Buildable map[][];
    PathFinder pf;
    @BeforeEach
    void setUp() {

        map = new Buildable[5][10];
        map[0][0] = new ResidentialZone();
        map[0][1] = new Road();

        pf = new PathFinder(map);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void euclideanDistance() {
    }

    @Test
    void manhattanDistance() {
        assertEquals(pf.manhattanDistance(map[0][0], map[4][5]), 8);
    }
}