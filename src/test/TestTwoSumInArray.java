/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jazari.matrix.CMatrix;

/**
 *
 * @author cezerilab
 */
public class TestTwoSumInArray {
    public static void main(String[] args) {
        int[] d=CMatrix.getInstance().rand(20, 1, 0, 20).round().toIntArray1D();
        System.out.println("d = " + Arrays.toString(d));
        twoSum(d,17);
        //System.out.println("ret = " + Arrays.toString(ret));
    }

    private static void twoSum(int[] d, int t) {
        Map<String,Integer> lst=new HashMap();
        for (int i = 0; i < d.length; i++) {
            for (int j = i; j < d.length; j++) {
                int s=d[i]+d[j];
                if (s==t) {
                    lst.put(""+d[i]+"+"+d[j],t);
                }
            }
        }
        System.out.println("lst = " + lst);
    }
}
