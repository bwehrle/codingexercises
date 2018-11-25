package org.bwehrle.hackerrank.library;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Solution {

    static boolean allCitiesConnected(int cityCount, int distinctRoadCount) {
        return (cityCount * (cityCount-1))/2 == distinctRoadCount;
    }

    static long allConnectedCase(int cityCount, int c_lib, int c_road) {
        //  all cities are connected and are cheaper than libraries;
        // cost = c_lib_count + c_road * (cityCount-1);
        return c_lib + c_road*(cityCount-1);
    }

    static long allLibrariesCase(int cityCount, int c_lib) {
        return cityCount + c_lib;
    }

    static class CityEntry {
        Integer cityId;
        Set<Integer> potentialConnections;
        //Set<Integer> roadsBuiltToCities;
        Boolean hasLibraryConnection;
    }

    private static long buildDepthFirstRoadsAndUpdateCities(CityEntry cityWithLibrary, Map<Integer, CityEntry>  cityRoadMap) {
        int countRoadsBuilt = 0;

        Set<Integer> visitedCities = new HashSet<>(cityRoadMap.size());
        ArrayDeque<Integer> parentFirstConnectionList = new ArrayDeque<>(cityWithLibrary.potentialConnections);
        visitedCities.add(cityWithLibrary.cityId);
        Integer lastCityId = cityWithLibrary.cityId;
        while (!parentFirstConnectionList.isEmpty()) {
            Integer currentCityId = parentFirstConnectionList.pop();
            visitedCities.add(currentCityId);
            CityEntry linkedCity = cityRoadMap.get(currentCityId);
            if (!linkedCity.hasLibraryConnection) {
                //System.out.println("Building road from " + lastCityId + " to " + currentCityId);
                // building road from n ... n+M -> n+M+1
                countRoadsBuilt += 1;
                linkedCity.hasLibraryConnection = true;
            }
            Set<Integer> nextCitiesToVisit = new HashSet<>(linkedCity.potentialConnections);
            nextCitiesToVisit.removeIf(visitedCities::contains);
            parentFirstConnectionList.addAll(nextCitiesToVisit);
            lastCityId = currentCityId;
        }
        return countRoadsBuilt;
    }

    private static long minRoadAndLibraryCost(int cityCount, int costLibrary, int costRoad, int[][] cityRoadArr) {
        int roadBuiltCount = 0;
        int libBuiltCount = 0;

        Map<Integer, CityEntry> cityRoadMap = buildRoadMap(cityCount, cityRoadArr);
        for (CityEntry city : cityRoadMap.values()) {
            if (!city.hasLibraryConnection) {
                city.hasLibraryConnection = true;
                libBuiltCount  += 1;
                roadBuiltCount += buildDepthFirstRoadsAndUpdateCities(city, cityRoadMap);
            }
        }
        return libBuiltCount*costLibrary + roadBuiltCount * costRoad;
    }

    // what about cities that are not listed; need to add empty entries for these
    private static Map<Integer, CityEntry> buildRoadMap(int cityCount, int[][] cityRoadArr) {
        HashMap<Integer, CityEntry> cityEntryHashMap = new HashMap<>(cityCount);
        for (int i = 0; i< cityCount; i++) {
            CityEntry cityEntry = new CityEntry();
            cityEntry.cityId = i+1;
            cityEntry.hasLibraryConnection = false;
            Set<Integer> roadsToCities = new HashSet<>();
            for (int[] roadArr : cityRoadArr) {
                if (roadArr[0] == cityEntry.cityId) {
                    roadsToCities.add(roadArr[1]);
                } else if (roadArr[1] == cityEntry.cityId) {
                    roadsToCities.add(roadArr[0]);
                }
            }
            cityEntry.potentialConnections = roadsToCities;
            cityEntryHashMap.put(cityEntry.cityId, cityEntry);
        }
        return cityEntryHashMap;
    }


    public static long roadsAndLibraries(int cityCount, int costLibrary, int costRoad, int[][] cityRoadArr) {
        int distinctRoadCount = cityRoadArr.length;
        if (costLibrary < costRoad) {
            return allLibrariesCase(cityCount, costLibrary);
        }
        else if (allCitiesConnected(cityCount, distinctRoadCount)) {
            return allConnectedCase(cityCount, costLibrary, costRoad);
        }
        else {
            return minRoadAndLibraryCost(cityCount, costLibrary, costRoad, cityRoadArr);
        }
    }


    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int q = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int qItr = 0; qItr < q; qItr++) {
            String[] nmC_libC_road = scanner.nextLine().split(" ");

            int n = Integer.parseInt(nmC_libC_road[0]);

            int m = Integer.parseInt(nmC_libC_road[1]);

            int c_lib = Integer.parseInt(nmC_libC_road[2]);

            int c_road = Integer.parseInt(nmC_libC_road[3]);

            int[][] cities = new int[m][2];

            for (int i = 0; i < m; i++) {
                String[] citiesRowItems = scanner.nextLine().split(" ");
                scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

                for (int j = 0; j < 2; j++) {
                    int citiesItem = Integer.parseInt(citiesRowItems[j]);
                    cities[i][j] = citiesItem;
                }
            }

            long result = roadsAndLibraries(n, c_lib, c_road, cities);

            bufferedWriter.write(String.valueOf(result));
            bufferedWriter.newLine();
        }

        bufferedWriter.close();

        scanner.close();
    }
}

