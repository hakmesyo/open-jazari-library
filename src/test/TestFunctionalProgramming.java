/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import jazari.matrix.CMatrix;

/**
 *
 * @author cezerilab
 */
public class TestFunctionalProgramming {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap();
        map.put("apple", 12);
        map.put("orange", 3);
        map.put("kivi", 7);
        map.put("apple", 5);
        System.out.println(map.size());
        List<String> list = Arrays.asList("orange", "apple", "banana", "hazelnut", "pistachio");
        List<String> listOfFruits = list.stream()
                .map(e -> e.toUpperCase())
                .filter(e -> e.startsWith("B"))
                .collect(Collectors.toList());
        System.out.println("listOfFruits = " + list);
        System.out.println("listOfFruits = " + listOfFruits);

        IntStream intStream = IntStream.rangeClosed(0, 100);
        List<Integer> listOfInt = intStream.boxed().collect(Collectors.toList());
        System.out.println("listOfInt = " + listOfInt);

        Long fact = IntStream.rangeClosed(1, 25).mapToLong(e -> (long) e).reduce(1, (a, b) -> a * b);
        System.out.println("fact = " + fact);

        int[] a = CMatrix.getInstance().range(0, 10).toIntArray1D();
        Stream aa = Arrays.stream(a).boxed();

        Consumer<String> printOut = e -> System.out.println(e);
        list.forEach(printOut);

        Function<Integer, Integer> functionCall = e -> 3 * e + 5;
        System.out.println("Fucntion<P,R> = " + functionCall.apply(3));
        listOfInt.stream().map(functionCall).forEach(e -> System.out.print(e + " "));

        Function<Integer, String> oddOrEven = x -> {
            return x % 2 == 0 ? "even" : "odd";
        };

        Employee e1 = new Employee("ahmet");
        Employee e2 = new Employee("hasan");
        Employee e3 = new Employee("ali");
        Employee e4 = new Employee("ahmet");
        List<Employee> employees=new ArrayList();
        employees.add(e1);
        employees.add(e2);
        employees.add(e3);
        employees.add(e4);
        
        System.out.println("\nburası:"+employees.stream().map(Employee::getName).collect(Collectors.toList()));
        
        List<Integer> numbers=Arrays.stream(new int[]{1,2,3,4,5}).boxed().collect(Collectors.toList());
        int total=numbers.stream().filter(x->x%2==0).mapToInt(x->x*x).sum();
        System.out.println("total = " + total);
        
        Function<Integer,Integer> sl=x->x*x;
        System.out.println("output:"+sl.apply(5));
        
    }
}
class Employee {

    private String name;

    public Employee(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
