/*
 * Represents a simple graph composed of vertices and edges.
 * Abstracts away the data structure implementation details,
 * and provides a way to perform simple operations on the 
 * graph (adding/deleting an edge, contracting two vertices,
 * etc).
 *
 * NOTE that the edges are mutable, but the vertices are not!
 * Thus, any method that performs an operation like "contract"
 * will actually create a new graph and return it.
 */

public class Graph {
	// order of the graph
	private int vertexCount;
	// size of the graph
	private int edgeCount;

	// this keeps track of whether two particular vertices
	// have an edge between them or not
	// (see the functions for adding/reading edges for
	//  information on how the edges are tracked)
	private boolean[] edges;
	
	/*
	 * This constructor simply creates an empty graph with the 
	 * specified number of vertices.
	 */
	public Graph( int vertexCount ) {
		this.vertexCount = vertexCount;
		edgeCount = 0;

		// there are ( vertexCount choose 2 ) possible edges
		edges = new boolean[ vertexCount*(vertexCount - 1)/2 ];

		// initialize to an empty graph
		for( int i = 0; i < edges.length; i++ ) {
			edges[ i ] = false;
		}
	}	

	/*
	 * Adds an edge to the graph between the vertices specified.
	 */
	public void addEdge( int v, int w ) {
		if( v == w ) {
			throw new IllegalArgumentException( 
					"Vertex loops are not permitted." );
		}

		// to keep things simple, we want to be able to assume v < w
		if( v > w ) {
			addEdge( w, v );
			return;
		}

		// set the appropriate boolean to true, and we're done!
		edges[ getEdgeIndex( v, w ) ] = true;
	}

	/*
	 * The edges are stored in the array in ascending order, with duplicate
	 * edges and loops omitted. For instance, in a graph of order 3, we have
	 * 0: 0,1
	 * 1: 0,2
	 * 2: 1,2
	 */
	private int getEdgeIndex( int v, int w ) {
		// First we get the offset based off of v, which is given by
		// the summation from i = 1 to v of (vertexCount - i).
		// With some algebraic manipulation, we can get this to the 
		// closed form (vertexCount*v - (v*(v + 1)/2)).
		// Then we must also add the offset for w, which is given by 
		// w - v - 1
		return vertexCount*v - v*(v + 1)/2 + w - v - 1;
	}
}
