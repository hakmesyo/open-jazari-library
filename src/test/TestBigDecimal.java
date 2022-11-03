/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.math.BigDecimal;
import jazari.utils.bigdecimal.BigDecimalMath;

/**
 *
 * @author cezerilab
 */
public class TestBigDecimal {

    public static void main(String[] args) {
        BigDecimal b1 = new BigDecimal("4");
        BigDecimal b2 = new BigDecimal("2500000");//.divide(new BigDecimal(31536000));
        System.out.println("sonuc b1 taban:"+b1.pow(b2.intValue()));
        System.out.println("sonuc b2 taban:"+b2.pow(b1.intValue()));
//        int n = 365 * 24 * 3600;
//        System.out.println("n = " + n);
//        BigDecimal ret=BigDecimalMath.pow(b2, b2);
//        System.out.println("ret = " + ret);
    }
}
