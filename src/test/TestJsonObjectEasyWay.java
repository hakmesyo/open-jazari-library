/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jazari.factory.FactoryUtils;
import org.apache.commons.compress.archivers.zip.PKWareExtraHeader;
import processing.data.JSONArray;
import processing.data.JSONObject;

/**
 *
 * @author cezerilab
 */
public class TestJsonObjectEasyWay {

    public static void main(String[] args) {
        String path = "D:\\DATASETS\\teknofest_2023_ulasimda_yapay_zeka\\last_years\\gonderilecek_veriler\\veriler.json";
        String jsonText = FactoryUtils.readFile(path);
        JSONObject js = JSONObject.parse(jsonText);
        JSONArray map = js.getJSONArray("frameler");
        int n = map.size();
        Map<String,Integer> uniqueTur=new HashMap<>();
        for (int i = 0; i < n; i++) {
            JSONObject obj = (JSONObject) map.get(i);
            String frame_url = obj.getString("frame_url");
            int frame_width = obj.getInt("frame_width");
            int frame_height = obj.getInt("frame_height");
            JSONArray objeler = obj.getJSONArray("objeler");
            int k=objeler.size();
            for (int j = 0; j < k; j++) {
                JSONObject obj2=objeler.getJSONObject(j);
                String tur=obj2.getString("tur");
                Double x0=obj2.getDouble("x0");
                Double y0=obj2.getDouble("y0");
                Double x1=obj2.getDouble("x1");
                Double y1=obj2.getDouble("y1");
                uniqueTur.put(tur, 1);
            }            
        }
        int a = 1;
    }
}
