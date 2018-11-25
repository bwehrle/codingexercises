package org.bwehrle.hackerrank.library;

import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {

    @Test
    public void SimpleThreeCityCaseWith1Lib2Roads() {
        int costRoad = 10;
        int costLibrary = 20;
        long expectedCost = 2 * costRoad + costLibrary;
        int[][] cityRoads = new int[][]{
                new int[]{1,2},
                new int[]{2,3},
                new int[]{3,2}
        };
        long calcCost = Solution.roadsAndLibraries(3, costLibrary, costRoad, cityRoads);
        assertEquals(expectedCost, calcCost);
    }


    @Test
    public void FourCityCase1Lib3Roads() {
        int costRoad = 10;
        int costLibrary = 20;
        long expectedCost = 3 * costRoad + costLibrary;
        int[][] cityRoads = new int[][]{
                new int[]{1,2},
                new int[]{2,3},
                new int[]{3,4}
        };
        long calcCost = Solution.roadsAndLibraries(4, costLibrary, costRoad, cityRoads);
        assertEquals(expectedCost, calcCost);
    }

    @Test
    public void FourCityCase1Lib3RoadsIsolatedCity() {
        int costRoad = 10;
        int costLibrary = 20;
        long expectedCost = 3 * costRoad + 2*costLibrary;
        int[][] cityRoads = new int[][]{
                new int[]{1,2},
                new int[]{2,3},
                new int[]{3,2}
        };
        long calcCost = Solution.roadsAndLibraries(4, costLibrary, costRoad, cityRoads);
        assertEquals(expectedCost, calcCost);
    }
}