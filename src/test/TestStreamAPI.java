/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author cezerilab
 */
public class TestStreamAPI {
    public static void main(String[] args) {
        List lst=toUpperCase("ahmet","MUSa","Salih","Beyazid","Mustafa","hasan","AbdulKadir");
        System.out.println("lst = " + lst);
        long cnt=getCountLongerThanFive(lst);
        System.out.println("cnt = " + cnt);
    }
    
    public static List toUpperCase(String ... str){
        return Arrays.stream(str).map(e -> e.toUpperCase()).collect(Collectors.toList());
    }

    private static long getCountLongerThanFive(List<String> lst) {
        return lst.stream().filter(e -> e.length()>5).mapToInt(e -> e.length()).count();
    }
    
    private static List<String> toFlatMap(List<List<String>> lst){
        return lst.stream().flatMap(Collection::stream).collect(Collectors.toList());
    }
}
