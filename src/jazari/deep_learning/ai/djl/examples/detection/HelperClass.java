/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.deep_learning.ai.djl.examples.detection;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jazari.factory.FactoryUtils;
import jazari.matrix.CMatrix;

/**
 *
 * @author cezerilab
 */
public class HelperClass {
    public static void main(String[] args) {
//        CMatrix cm = CMatrix.getInstance()
//                .imread("build/model/pikachu_output.png")
//                .imshow()
//                ;
//        List lst=Collections.singletonList(Arrays.asList("one,two".split(",")));
//        List lst=Arrays.asList("one,two".split(","));
//        System.out.println("lst = " + lst);
        
        String path="C:\\ds_teknofest\\recorded_images\\ds_cv\\test";
        //File[] imgs=FactoryUtils.getFileListInFolderForImages(path);
        //for (File img : imgs) {
            //CMatrix.getInstance().imread(img).imsave(path+"/"+FactoryUtils.getFileName(img.getName())+".jpg");
        //}
        
        Map<String,Integer> labels=new HashMap();
        labels.put("pikachu", 0);
        labels.put("trafik_isiklari", 1);
        labels.put("trafik_levha_azami_hiz_20", 2);
        labels.put("trafik_levha_azami_hiz_30", 3);
        labels.put("trafik_levha_dur", 4);
        labels.put("trafik_levha_durak", 5);
        labels.put("trafik_levha_engel", 6);
        labels.put("trafik_levha_engelli", 7);
        labels.put("trafik_levha_giris_yok", 8);
        labels.put("trafik_levha_hiz_sinirlamasi_sonu", 9);
        labels.put("trafik_levha_ileri_veya_saga_mecburi_yol", 10);
        labels.put("trafik_levha_ileri_veya_sola_mecburi_yol", 11);
        labels.put("trafik_levha_ileriden_saga_mecburi_yol", 12);
        labels.put("trafik_levha_ileriden_sola_mecburi_yol", 13);
        labels.put("trafik_levha_park", 14);
        labels.put("trafik_levha_park_etmek_yasaktir", 15);
        labels.put("trafik_levha_park_etmek_yasaktir_2", 16);
        labels.put("trafik_levha_saga_donulmez", 17);
        labels.put("trafik_levha_sola_donulmez", 18);
        labels.put("trafik_levha_trafige_kapali", 19);
        labels.put("trafik_levha_yaya_gecidi", 20);
        labels.put("trafik_levha_yol_calismasi", 21);
        labels.put("yaya_insan", 22);
        labels.put("zemin_durak_siyah", 23);
        labels.put("zemin_engel", 24);
        labels.put("zemin_yaya_gecidi", 25);
        labels.put("trafik_isiklari_red", 26);
        labels.put("trafik_isiklari_green", 27);
        String str=FactoryUtils.convertPascalVoc2DJLFormat(path, labels);
        System.out.println("str = " + str);
        FactoryUtils.writeToFile(path+"/index.file", str);
    }
}
