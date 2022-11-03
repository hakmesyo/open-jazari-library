/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.nd4j;

import java.util.Arrays;
import java.util.function.IntToDoubleFunction;
import java.util.stream.IntStream;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

/**
 *
 * @author DELL LAB
 */
public class NewClass {
    public static void main(String[] args) {
        //long[][] d={{1,2,3},{4,5,6}};
        IntStream b=IntStream.range(0, 12);
        double[] d=b.mapToDouble((int e) -> (double)e).toArray();
        System.out.println("d = " + Arrays.toString(d));
        INDArray a=Nd4j.create(d,new int[]{d.length,1});
        System.out.println(a);
    }
}
