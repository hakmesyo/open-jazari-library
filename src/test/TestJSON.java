/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cezerilab
 */
public class TestJSON {

    public static void main(String[] args) {
        List<A> foo = new ArrayList();
        foo.add(new A(11,"php"));
        foo.add(new A(3,"java"));
        foo.add(new A(2,"c"));

        String json = new Gson().toJson(foo);
        System.out.println(json);
    }
}

class A{
    int a=3;
    String b="java";
    
    public A(int a, String b){
        this.a=a;
        this.b=b;
    }
}
