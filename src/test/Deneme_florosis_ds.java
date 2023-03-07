/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.File;
import jazari.factory.FactoryUtils;
import jazari.matrix.CMatrix;

/**
 *
 * @author dell_lab
 */
public class Deneme_florosis_ds {

    public static void main(String[] args) {
        String path = "C:\\Users\\dell_lab\\Desktop\\ds";

        splitDataSet(path, new String[]{"2", "3", "4", "5"}, 0.85, 0.15);

    }

    private static void splitDataSet(String path, String[] className, double train_r, double test_r) {
        for (int k = 0; k < className.length; k++) {
            File[] folders = FactoryUtils.getFolderListInFolder(path + "/" + className[k]);
            folders = FactoryUtils.shuffle(folders, 123);
            int nr = folders.length;
            int n_train = (int) Math.round(nr * train_r);
            int n_test = nr - n_train;
            String dest_path = "C:\\Users\\dell_lab\\Desktop\\ds_split";
            FactoryUtils.makeDirectory(dest_path + "/" + className[k]);
            String train_path = dest_path + "/" + className[k] + "/train";
            FactoryUtils.makeDirectory(train_path);
            String test_path = dest_path + "/" + className[k] + "/test";
            FactoryUtils.makeDirectory(test_path);
            for (int i = 0; i < nr; i++) {
                if (i < n_train) {
                    FactoryUtils.copyDirectory(folders[i], new File(train_path + "\\" +folders[i].getName()));
                } else {
                    FactoryUtils.copyDirectory(folders[i], new File(test_path + "\\"  +folders[i].getName()));
                }
            }
        }

    }
}
