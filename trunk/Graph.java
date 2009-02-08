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
		// set the appropriate boolean to true, and we're done!
		edges[ getEdgeIndex( v, w ) ] = true;
	}

	/*
	 * Deletes an edge from the graph between the vertices specified.
	 */
	public void deleteEdge( int v, int w ) {
		// set the appropriate boolean to false, and we're done!
		edges[ getEdgeIndex( v, w ) ] = false;
	}

	/*
	 * Checks whether an edge is contained in this graph.
	 */
	public boolean hasEdge( int v, int w ) {
		return edges[ getEdgeIndex( v, w ) ];
	}

	/*
	 * Performs an edge contraction on this graph, and returns the
	 * result in the form of a newly created graph.
	 */
	public Graph contraction( int v, int w ) {
		// ensure v < w
		if( v > w ) {
			return contraction( w, v );
		}

		// first create a new graph with one less vertex than this one
		Graph result = new Graph( vertexCount - 1 );
		
		// this will keep track of where we are in the result graph
		int resIndex = 0;

		// now iterate through each edge in the existing graph
		for( int i = 0; i < vertexCount; i++ ) {
			for( int j = i + 1; j < vertexCount; j++ ) {
				// we'll merge the vertices in the new graph by
				// deleting v and redirecting all the edges 
				// that were incident on v over to w

				// To understand the ensuing if/else block, it helps
				// to note that all the vertices above v in the
				// array will be shifted down to position v - 1
				// in the result array. i.e., if j > i, then vertex 
				// j in this graph will become vertex j - 1 in the next.

				if( i != v && j != v ) {
					result.edges[ resIndex++ ] |= hasEdge( i, j );
				} else if( i == v && j != w ) {
					if( j < v ) {
						result.addEdge( w - 1, j );
					} else {
						result.addEdge( w - 1, j - 1 );
					}
				} else if( j == v && i != w ) {
					if( i < v ) {
						result.addEdge( w - 1, i );
					} else {
						result.addEdge( w - 1, i - 1 );
					}
				}
			}
		}

		return result;
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

		// Note that loops are not allowed. (Trying to find a
		// coloring for a graph containing loops would be rather silly.)
		if( v == w ) {
			throw new IllegalArgumentException( 
					"Vertex loops are not permitted." );
		}
		
		// Also, we want to be able to assume that v < w, so if it's
		// not we'll fix that right here:
		if( v > w ) {
			return getEdgeIndex( w, v );
		} else {
			return vertexCount*v - v*(v + 1)/2 + w - v - 1;
		}
	}

	/*
	 * Checks whether this graph is empty (has no edges).
	 */
	public boolean isEmpty() {
		for( int i = 0; i < edges.length; i++ ) {
			if( edges[i] ) {
				return false;
			}
		}
		return true;
	}
}
