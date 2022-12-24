/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.deep_learning.tensorflow_js.test.ssd_object_detection;

import java.io.File;
import jazari.factory.FactoryUtils;

/**
 *
 * @author cezerilab
 */
public class TFRecordCSV {
    public static void main(String[] args) {
        String path="C:\\Users\\cezerilab\\Downloads\\archive (3)";
        //422 train, 107 test
        String[] ret=FactoryUtils.convertPascalVoc2CsvFormat(path+"\\annotations",0.8f,0.2f);
        FactoryUtils.writeToFile(path+"\\train_labels.csv", ret[0]);
        FactoryUtils.writeToFile(path+"\\test_labels.csv", ret[1]);
    }
}
