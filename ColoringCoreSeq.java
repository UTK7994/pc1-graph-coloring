import java.awt.Point; // ahh, if only Java had tuples...

/*
 * Provides a sequential implementation of the deletion-contraction algorithm.
 */

public class ColoringCoreSeq implements ColoringCore {
	public int run( Graph input ) {
		// first check if we have an empty graph
		if( input.isEmpty() ) {
			return 1;
		}

		// starting with 2, we'll try each value until we find
		// one that gives us more than 0 colorings
		int n = 1;
		boolean found = false;

		while( !found ) {
			long result = countColorings( input, ++n );
			System.out.println( result + " " + n + "-colorings found." );
			found = ( result > 0 );
		}

		return n;
	}

	protected long countColorings( Graph g, int n ) {
		if( g.isEmpty() ) {
			// if there are no edges left,
			// return n to the number of vertices
			return (long)Math.pow( n, g.order() );
		}

		long delCount, conCount;
		Point edge = g.pickEdge();

		g.deleteEdge( edge.x, edge.y );
		delCount = countColorings( g, n );
		// add the edge back before we contract
		g.addEdge( edge.x, edge.y );

		conCount = countColorings( g.contraction( edge.x, edge.y ), n );

		return delCount - conCount;
	}
}
