/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.machine_learning.classifiers;

/**
 * To find k nearest neighbors of a new instance Please watch my explanation of
 * how KNN works: xxx - For classification it uses majority vote - For
 * regression it finds the mean (average)
 *
 * Copyright (C) 2014
 *
 * @author Dr Noureddin Sadawi
 *
 * This program is free software: you can redistribute it and/or modify it as
 * you wish ONLY for legal and ethical purposes
 *
 * I ask you only, as a professional courtesy, to cite my name, web page and my
 * YouTube Channel!
 *
 */
import java.util.*;

class KNN_Sadawi {

    // the data

    static float[][] instances = {
        {0.35f, 0.91f, 0.86f, 0.42f, 0.71f},
        {0.21f, 0.12f, 0.76f, 0.22f, 0.92f},
        {0.41f, 0.58f, 0.73f, 0.21f, 0.09f},
        {0.71f, 0.34f, 0.55f, 0.19f, 0.80f},
        {0.79f, 0.45f, 0.79f, 0.21f, 0.44f},
        {0.61f, 0.37f, 0.34f, 0.81f, 0.42f},
        {0.78f, 0.12f, 0.31f, 0.83f, 0.87f},
        {0.52f, 0.23f, 0.73f, 0.45f, 0.78f},
        {0.53f, 0.17f, 0.63f, 0.29f, 0.72f},};

    /**
     * Returns the majority value in an array of strings majority value is the
     * most frequent value (the mode) handles multiple majority values (ties
     * broken at random)
     *
     * @param array an array of strings
     * @return the most frequent string in the array
     */
    private static String findMajorityClass(String[] array) {
        //add the String array to a HashSet to get unique String values
        Set<String> h = new HashSet<String>(Arrays.asList(array));
        //convert the HashSet back to array
        String[] uniqueValues = h.toArray(new String[0]);
        //counts for unique strings
        int[] counts = new int[uniqueValues.length];
        // loop thru unique strings and count how many times they appear in origianl array   
        for (int i = 0; i < uniqueValues.length; i++) {
            for (int j = 0; j < array.length; j++) {
                if (array[j].equals(uniqueValues[i])) {
                    counts[i]++;
                }
            }
        }

        for (int i = 0; i < uniqueValues.length; i++) {
            System.out.println(uniqueValues[i]);
        }
        for (int i = 0; i < counts.length; i++) {
            System.out.println(counts[i]);
        }

        int max = counts[0];
        for (int counter = 1; counter < counts.length; counter++) {
            if (counts[counter] > max) {
                max = counts[counter];
            }
        }
        System.out.println("max # of occurences: " + max);

		// how many times max appears
        //we know that max will appear at least once in counts
        //so the value of freq will be 1 at minimum after this loop
        int freq = 0;
        for (int counter = 0; counter < counts.length; counter++) {
            if (counts[counter] == max) {
                freq++;
            }
        }

        //index of most freq value if we have only one mode
        int index = -1;
        if (freq == 1) {
            for (int counter = 0; counter < counts.length; counter++) {
                if (counts[counter] == max) {
                    index = counter;
                    break;
                }
            }
            //System.out.println("one majority class, index is: "+index);
            return uniqueValues[index];
        } else {//we have multiple modes
            int[] ix = new int[freq];//array of indices of modes
            System.out.println("multiple majority classes: " + freq + " classes");
            int ixi = 0;
            for (int counter = 0; counter < counts.length; counter++) {
                if (counts[counter] == max) {
                    ix[ixi] = counter;//save index of each max count value
                    ixi++; // increase index of ix array
                }
            }

            for (int counter = 0; counter < ix.length; counter++) {
                System.out.println("class index: " + ix[counter]);
            }

            //now choose one at random
            Random generator = new Random();
            //get random number 0 <= rIndex < size of ix
            int rIndex = generator.nextInt(ix.length);
            System.out.println("random index: " + rIndex);
            int nIndex = ix[rIndex];
            //return unique value at that index 
            return uniqueValues[nIndex];
        }

    }

    /**
     * Returns the mean (average) of values in an array of floatss sums
     * elements and then divides the sum by num of elements
     *
     * @param array an array of floats
     * @return the mean
     */
    private static float meanOfArray(float[] m) {
        float sum = 0.0f;
        for (int j = 0; j < m.length; j++) {
            sum += m[j];
        }
        return sum / m.length;
    }

    public static void main(String args[]) {

        int k = 6;// # of neighbours  
        //list to save city data
        List<City> cityList = new ArrayList<City>();
        //list to save distance result
        List<Result> resultList = new ArrayList<Result>();
        // add city data to cityList       
        cityList.add(new City(instances[0], "London"));
        cityList.add(new City(instances[1], "Leeds"));
        cityList.add(new City(instances[2], "Liverpool"));
        cityList.add(new City(instances[3], "London"));
        cityList.add(new City(instances[4], "Liverpool"));
        cityList.add(new City(instances[5], "Leeds"));
        cityList.add(new City(instances[6], "London"));
        cityList.add(new City(instances[7], "Liverpool"));
        cityList.add(new City(instances[8], "Leeds"));
        //data about unknown city
        float[] query = {0.65f, 0.78f, 0.21f, 0.29f, 0.58f};
        //find disnaces
        for (City city : cityList) {
            float dist = 0.0f;
            for (int j = 0; j < city.cityAttributes.length; j++) {
                dist += Math.pow(city.cityAttributes[j] - query[j], 2);
                //System.out.print(city.cityAttributes[j]+" ");    	     
            }
            float distance = (float)Math.sqrt(dist);
            resultList.add(new Result(distance, city.cityName));
            //System.out.println(distance);
        }

        //System.out.println(resultList);
        Collections.sort(resultList, new DistanceComparator());
        String[] ss = new String[k];
        for (int x = 0; x < k; x++) {
            System.out.println(resultList.get(x).cityName + " .... " + resultList.get(x).distance);
            //get classes of k nearest instances (city names) from the list into an array
            ss[x] = resultList.get(x).cityName;
        }
        String majClass = findMajorityClass(ss);
        System.out.println("Class of new instance is: " + majClass);
    }//end main  

    //simple class to model instances (features + class)
    static class City {

        float[] cityAttributes;
        String cityName;

        public City(float[] cityAttributes, String cityName) {
            this.cityName = cityName;
            this.cityAttributes = cityAttributes;
        }
    }

    //simple class to model results (distance + class)

    static class Result {

        float distance;
        String cityName;

        public Result(float distance, String cityName) {
            this.cityName = cityName;
            this.distance = distance;
        }
    }

    //simple comparator class used to compare results via distances

    static class DistanceComparator implements Comparator<Result> {

        @Override
        public int compare(Result a, Result b) {
            return a.distance < b.distance ? -1 : a.distance == b.distance ? 0 : 1;
        }
    }

}
