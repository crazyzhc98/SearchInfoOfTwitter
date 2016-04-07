package smallWorld;

import java.util.HashMap;
import java.util.List;

/******************************************************************************
 *  Compilation:  javac Graph.java
 *  Execution:    java Graph
 *  Dependencies: ST.java SET.java In.java StdOut.java
 *  
 *  Undirected graph data type implemented using a symbol table
 *  whose keys are vertices (String) and whose values are sets
 *  of neighbors (SET of Strings).
 *
 *  Remarks
 *  -------
 *   - Parallel edges are not allowed
 *   - Self-loop are allowed
 *   - Adjacency lists store many different copies of the same
 *     String. You can use less memory by interning the strings.
 *
 ******************************************************************************/

/**
 *  The <tt>Graph</tt> class represents an undirected graph of vertices
 *  with string names.
 *  It supports the following operations: add an edge, add a vertex,
 *  get all of the vertices, iterate over all of the neighbors adjacent
 *  to a vertex, is there a vertex, is there an edge between two vertices.
 *  Self-loops are permitted; parallel edges are discarded.
 *  <p>
 *  For additional documentation, see <a href="http://introcs.cs.princeton.edu/45graph">Section 4.5</a> of
 *  <i>Introduction to Programming in Java: An Interdisciplinary Approach</i> by Robert Sedgewick and Kevin Wayne.
 */
public class Graph {

    // symbol table: key = string vertex, value = set of neighboring vertices
    private ST<String, SET<String>> st;
    
    private ST<String, SET<String>> stIn;
    
    private ST<String, SET<String>> stOut;
    
//    private ST<String,SET<String>> stAll;
    

    // number of edges
    private int E;

   /**
     * Create an empty graph with no vertices or edges.
     */
    public Graph() {
        st = new ST<String, SET<String>>();
        stIn = new ST<String, SET<String>>();
        stOut = new ST<String, SET<String>>();
    }

   /**
     * Create an graph from given input stream using given delimiter.
     */
    public Graph(In in, String delimiter) {
        st = new ST<String, SET<String>>();
        stIn = new ST<String, SET<String>>();
        stOut = new ST<String, SET<String>>();
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] names = line.split(delimiter);
            for (int i = 1; i < names.length; i++) {
                addEdge(names[0], names[i]);
            }
        }
    }

   /**
     * Number of vertices.
     */
    public int V() {
        return st.size();
    }

   /**
     * Number of edges.
     */
    public int E() {
        return E;
    }

    // throw an exception if v is not a vertex
    private void validateVertex(String v) {
        if (!hasVertex(v)) throw new IllegalArgumentException(v + " is not a vertex");
    }

   /**
     * Degree of this vertex.
     */
    public int degree(String v) {
        validateVertex(v);
        return st.get(v).size();
    }
    
    /**
     * InDegree of this vertex.
     */
    public int degreeIn(String v) {
        validateVertex(v);
        return stIn.get(v).size();
    }
    
    /**
     * OutDegree of this vertex.
     */
    public int degreeOut(String v) {
        validateVertex(v);
        return stOut.get(v).size();
    }
    

   /**
     * Add edge v-w to this graph (if it is not already an edge)
     */
    public void addEdge(String v, String w) {
        if (!hasVertex(v)) addVertex(v);
        if (!hasVertex(w)) addVertex(w);
        if (!hasEdge(v, w)) E++;
        st.get(v).add(w);
        st.get(w).add(v);
        
        stIn.get(v).add(w);
        stOut.get(w).add(v);
//        stAll.get(v).add(w);
//        stAll.get(w).add(v);
    }

   /**
     * Add vertex v to this graph (if it is not already a vertex)
     */
    public void addVertex(String v) {
        if (!hasVertex(v)) {
        	st.put(v, new SET<String>());
        	stIn.put(v, new SET<String>());
        	stOut.put(v, new SET<String>());
        }
    }


   /**
     * Return the set of vertices as an Iterable.
     */
    public Iterable<String> vertices() {
        return st;
    }
    
    public ST<String,SET<String>> getST(){
    	return st;
    }
    
    public ST<String,SET<String>> getSTOut(){
    	return stOut;
    }
    
    
   /**
     * Return the set of neighbors of vertex v as in Iterable.
     */
    public Iterable<String> adjacentTo(String v) {
        validateVertex(v);
        return st.get(v);
    }
    
    
    public List<String> getNeighbors(String v){
    	return st.get(v).getAll();
    }
    
    /**
     * Return a of neighbors of vertex v .
     */
    public String getRandomAt(String v) {
        validateVertex(v);
        return st.get(v).getRandom();
    }
    

   /**
     * Is v a vertex in this graph?
     */
    public boolean hasVertex(String v) {
        return st.contains(v);
    }

   /**
     * Is v-w an edge in this graph?
     */
    public boolean hasEdge(String v, String w) {
        validateVertex(v);
        validateVertex(w);
        return st.get(v).contains(w);
    }

   /**
     * Return a string representation of the graph.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (String v : st) {
            s.append(v + ": ");
            for (String w : st.get(v)) {
                s.append(w + " ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        Graph G = new Graph();
        G.addEdge("1", "2");
        G.addEdge("1", "3");
        G.addEdge("1", "4");
        G.addEdge("2", "3");
        G.addEdge("3", "2");
        G.addEdge("3", "4");
        G.addEdge("4", "2");
//        G.addVertex("H");

        // print out graph
        StdOut.println(G);

        
    }

	

}
