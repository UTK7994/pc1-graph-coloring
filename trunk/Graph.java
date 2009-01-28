/*
 * Represents a simple graph composed of vertices and edges.
 * Abstracts away the data structure implementation details,
 * and provides a way to perform simple operations on the 
 * graph (adding/deleting an edge, contracting two vertices,
 * etc).
 */

public class Graph {
	private int vertexCount;
	// add member variables for keeping track of the edges
	// in the graph here
	
	public Graph( int vertexCount ) {
		this.vertexCount = vertexCount;
	}
}
