/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import jazari.matrix.CMatrix;

/**
 *
 * @author cezerilab
 */
public class TestAnnotations {
    public static void main(String[] args) {
        String path="C:\\Users\\cezerilab\\Desktop\\object_detection_dataset\\images\\val";
        CMatrix cm = CMatrix.getInstance()
//                .imread(path)
//                .imshow()
//                .reduceImageSize(path,1500,1000)
//                .annotateImages(path)
                .convertPascalVoc2Yolo(path,new String[]{"0:object"})
//                .convertPascalVoc2Yolo(path,new String[]{""
//                      + "0:park_yapilmaz",
//                        "1:car",
//                        "2:person",
//                        "3:park",
//                        "4:stop",
//                        "5:engelli_park",
//                        "6:truck",
//                        "7:bisiklet",
//                        "8:sola_viraj",
//                        "9:bus",
//                        "10:durak",
//                        "11:sola_donulmez",
//                        "12:yaya_gecidi",
//                        "13:green_light",
//                        "14:girilmez",
//                        "15:ileri_veya_saga",
//                        "16:ileri_veya_sola",
//                        "17:ileri",
//                        "18:red_light",
//                        "19:ada",
//                        "20:saga_mecburi",
//                        "21:sola_mecburi",
//                        "22:saga_viraj",
//                        "23:saga_donulmez",
//                        "24:gidis_gelis",
//                        "25:saga_serite_gir",
//                        "26:sola_serite_gir",
//                        "27:duba"
//                })
                ;
    }
}
