/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.deep_learning.tensorflow_js.utils;

import jazari.enums.EnumBackEnd;
import jazari.enums.EnumDataSource;
import jazari.enums.EnumDatasetType;
import jazari.enums.EnumDevelopmentPlatform;
import jazari.enums.EnumInferenceType;
import jazari.enums.EnumLearningMode;
import jazari.enums.EnumLearningType;

/**
 *
 * @author BAP1
 */
public class Settings {
    public static EnumDevelopmentPlatform development_platform=EnumDevelopmentPlatform.PYTHON;
    public static EnumBackEnd back_end=EnumBackEnd.CPU;
    public static EnumDataSource data_source=EnumDataSource.CAMERA;
    public static EnumDatasetType dataset_type=EnumDatasetType.IMAGE;
    public static EnumLearningMode learning_mode=EnumLearningMode.TEST;
    public static EnumLearningType learning_type=EnumLearningType.CLASSIFICATION;
    public static EnumInferenceType inference_type=EnumInferenceType.REAL_TIME; 
    public static String modelPath="";
    public static String testFolderPath="";
    
    public static void main(String[] args) {
        if (development_platform==EnumDevelopmentPlatform.PYTHON) {
            System.out.println("ok");
        }
    }
}
