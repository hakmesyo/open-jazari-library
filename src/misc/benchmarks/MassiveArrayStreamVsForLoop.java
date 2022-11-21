/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misc.benchmarks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import jazari.factory.FactoryMatrix;
import jazari.factory.FactoryUtils;

/**
 *
 * @author cezerilab
 */
public class MassiveArrayStreamVsForLoop {

    static int max = 10_000_000;
    static int m = 500;
    static Random rnd = new Random(21);
    static int[] d1 = FactoryMatrix.rand(max, 100, rnd);
    static List<Integer> lst = FactoryMatrix.randList(max, 100, rnd);

    public static void main(String[] args) {
        long t1=FactoryUtils.tic();
        //callStream(m);
        callParallelStream(m);
        //callForLoop(m);
        t1=FactoryUtils.toc(t1);
    }

    private static int[] filterOrdinaryForLoop(int[] d, int thr) {
        List lst = new ArrayList();
        int n = d.length;
        for (int i = 0; i < n; i++) {
            if (d[i] >= thr) {
                lst.add(d[i]);
            }
        }
        int[] ret = new int[lst.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = (int) lst.get(i);
        }
        return ret;
    }

    private static void callStream(int defa) {
        for (int i = 0; i < defa; i++) {
            long t1 = System.currentTimeMillis();
            int a = rnd.nextInt(100);
            long cnt = lst.stream().filter(ref -> ref > a).distinct().count();//.forEach(System.out::println);
            long t2 = System.currentTimeMillis() - t1;
            System.out.println("normal stream filter time = " + t2 + " rnd number:"+a+" count:" + cnt);
        }
    }

    private static void callParallelStream(int defa) {
        for (int i = 0; i < defa; i++) {
            long t1 = System.currentTimeMillis();
            int a = rnd.nextInt(100);
            long cnt = lst.parallelStream().filter(ref -> ref > a).distinct().count();
            long t2 = System.currentTimeMillis() - t1;
            System.out.println("parallel stream filter time = " + t2 + " rnd number:"+a+" count:" + cnt);
        }
    }

    private static void callForLoop(int m) {
        //classic for loop way

        for (int i = 0; i < m; i++) {
            //System.out.println("classic for-loop way starting i:" + i);
            long t1 = System.currentTimeMillis();
            int a=rnd.nextInt(100);
            int[] d2 = filterOrdinaryForLoop(d1, a);
            long t2 = System.currentTimeMillis() - t1;
            System.out.println("for-loop filter time = " + t2 + " rnd number:"+a+" count:" + d2.length);
            //println(d2);
        }
    }

}
