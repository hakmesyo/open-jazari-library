/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import jazari.utils.PythonPickle;

/**
 *
 * @author cezerilab
 */
public class TestPythonPickle {

    public static void main(String[] args) throws IOException {
        PythonPickle output = PythonPickle.fromFile("D:\\DATASETS\\cifar-10-python\\cifar-10-batches-py\\data_batch_1");
        
        int a=3;
    }
}
