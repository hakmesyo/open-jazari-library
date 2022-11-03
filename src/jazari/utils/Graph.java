/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jazari.utils;

// Java program to implement Graph
// with the help of Generics
import java.awt.Point;
import java.util.*;
import jazari.factory.FactoryUtils;

public class Graph<T> {

    // We use Hashmap to store the edges in the graph
    private Map<T, List<T>> map = new HashMap<>();

    // This function adds a new vertex to the graph
    public void addVertex(T s) {
        map.put(s, new LinkedList<T>());
    }
    
    public List<T> getVertex(T s){
        return map.get(s);
    }

    // This function adds the edge
    // between source to destination
    public void addEdge(T source,
            T destination,
            boolean bidirectional) {

        if (!map.containsKey(source)) {
            addVertex(source);
        }

        if (!map.containsKey(destination)) {
            addVertex(destination);
        }

        map.get(source).add(destination);
        if (bidirectional == true) {
            map.get(destination).add(source);
        }
    }

    // This function gives the count of vertices
    public void getVertexCount() {
        System.out.println("The graph has "
                + map.keySet().size()
                + " vertex");
    }

    // This function gives the count of edges
    public void getEdgesCount(boolean bidirection) {
        int count = 0;
        for (T v : map.keySet()) {
            count += map.get(v).size();
        }
        if (bidirection == true) {
            count = count / 2;
        }
        System.out.println("The graph has "
                + count
                + " edges.");
    }

    // This function gives whether
    // a vertex is present or not.
    public void hasVertex(T s) {
        if (map.containsKey(s)) {
            System.out.println("The graph contains "
                    + s + " as a vertex.");
        } else {
            System.out.println("The graph does not contain "
                    + s + " as a vertex.");
        }
    }

    // This function gives whether an edge is present or not.
    public void hasEdge(T s, T d) {
        if (map.get(s).contains(d)) {
            System.out.println("The graph has an edge between "
                    + s + " and " + d + ".");
        } else {
            System.out.println("The graph has no edge between "
                    + s + " and " + d + ".");
        }
    }

    // This function computes cost on the edge.
    public float getCost(T s, T d) {
        float ret=FactoryUtils.getEucledianDistance((Point)s,(Point)d);
        return ret;
    }

    // Prints the adjancency list of each vertex.
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (T v : map.keySet()) {
            builder.append(v.toString() + ": ");
            for (T w : map.get(v)) {
                builder.append(w.toString() + " ");
            }
            builder.append("\n");
        }

        return (builder.toString());
    }

    public static void main(String args[]) {

        // Object of graph is created.
        Graph<Point> g = new Graph<Point>();
        Point p0=new Point(50,50);
        Point p1=new Point(100,50);
        Point p2=new Point(150,150);
        Point p3=new Point(100,200);
        Point p4=new Point(50,200);
        // edges are added.
        // Since the graph is bidirectional,
        // so boolean bidirectional is passed as true.
        g.addEdge(p0, p1, true);
        g.addEdge(p0, p4, true);
        g.addEdge(p1, p2, true);
        g.addEdge(p1, p3, true);
        g.addEdge(p1, p4, true);
        g.addEdge(p2, p3, true);
        g.addEdge(p3, p4, true);

        // Printing the graph
        System.out.println("Graph:\n"
                + g.toString());

        // Gives the no of vertices in the graph.
        g.getVertexCount();

        // Gives the no of edges in the graph.
        g.getEdgesCount(true);

        // Tells whether the edge is present or not.
        g.hasEdge(p3, p4);

        // Tells whether vertex is present or not
        g.hasVertex(p4);
        
        System.out.println(g.getCost(p0, p1));
        
        Point p;
        List<Point> lst=g.getVertex(p0);
        while(true){
            int n=lst.size();
            for (int i = 0; i < n; i++) {
                lst=g.getVertex(p0);
            }
        }
        
        
        
    }

}
