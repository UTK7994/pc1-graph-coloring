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

		Polynomial result = countColorings( input );
		
		int k = findMinColorsNeeded( result );

		return k;
	}

	protected Polynomial countColorings( Graph g ) {
		if( g.isEmpty() ) {
			// if there are no edges left,
			// return X to the number of vertices
			return new Polynomial( g.order(), 1 );
		}

		Polynomial delPoly, conPoly;
		Point edge = g.pickEdge();

		g.deleteEdge( edge.x, edge.y );
		delPoly = countColorings( g );
		// add the edge back before we contract
		g.addEdge( edge.x, edge.y );

		conPoly = countColorings( g.contraction( edge.x, edge.y ) );

		delPoly.subtract( conPoly );

		return delPoly;
	}

	protected int findMinColorsNeeded( Polynomial chromaticPoly ) {
		long numColorings = 0;
		int k = 0;

		while( numColorings <= 0 ) {
			k++;
			numColorings = chromaticPoly.evaluate( k );
		}

		return k;
	}
}
