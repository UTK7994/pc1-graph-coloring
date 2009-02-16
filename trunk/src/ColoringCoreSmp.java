import java.awt.Point; // ahh, if only Java had tuples...
import edu.rit.pj.*;

/*
 * Provides a smp implementation of the deletion-contraction algorithm.
 */

public class ColoringCoreSmp extends ColoringCoreSeq {
	public int run( Graph input ) {
		// first check if we have an empty graph
		if( input.isEmpty() ) {
			return 1;
		}

		Polynomial result = findChromPolySmp( input );

		// and once we get the chromatic polynomial, 
		// find the min number of colors needed
		int k = findMinColorsNeeded( result );

		return k;
	}

	protected Polynomial findChromPolySmp( final Graph input ) {
		// The trick here is figuring out how fine-grained
		// to make the parallelism. Every time we recurse, 
		// the number of graphs in our current level in the 
		// Zykov tree doubles. To split up the work, we'll 
		// pick a number of recursions, and have the threads
		// find all the chromatic polynomials at that 
		// particular level in the tree. Once all of these 
		// have been found, we can then reduce our results
		// sequentially down to one final polynomial.

		// However, if there are only, say, two edges
		// in the graph, we can't go down more than two
		// levels of recursion, so we'll have to take the 
		// min of two values.

		// 128 chunks/cpu at 8 cpus seems reasonable
		final int DEFAULT_DEPTH = 10;
		final int initialDepth = Math.min( DEFAULT_DEPTH, input.size() );
		final int resultSize = (int)Math.pow( 2, initialDepth );

		final Polynomial[] results = new Polynomial[ resultSize ];

                try {
                new ParallelTeam().execute( new ParallelRegion() {
                public void run() throws Exception {
		execute( 0, resultSize, new IntegerForLoop() {
			
			public void run( int first, int last ) {
				for( int i = first; i <= last; i++ ) {
					// calculate the graph for this index
					Graph localGraph = genGraphForIndex( input, i, initialDepth );
					// and find the polynomial for this graph
					results[i] = countColorings( localGraph );
				}
			}
		});}});}
		catch( Exception e ) {
			// so this error handling is kind of generic...
			// but we shouldn't be hitting any exceptions
			// here anyway, so I'd say this is alright.
			e.printStackTrace();
			System.exit( -1 );
		}

		// once we've shrunk the problem down to a manageable level, 
		// we can calculate our way through those results to find 
		// the final polynomial
		return reduceResults( results, resultSize );
	}

	private Graph genGraphForIndex( Graph g, int i, int depth ) {
		// copy the graph we're being passed to avoid side effects
		return genGraphForIndex2( new Graph(g), i, depth );
	}

	private Graph genGraphForIndex2( Graph g, int i, int depth ) {
		if( depth == 0 )
			return g;

		Point edge = g.pickEdge();

		// here the check the lsb of i, and recur based on that bit
		if( (i % 2) == 0 ) {
			g.deleteEdge( edge.x, edge.y );
			return genGraphForIndex2( g, i >> 1, depth - 1 );
		} else {
			return genGraphForIndex2( g.contraction( edge.x, edge.y ), 
					i >> 1, depth - 1 );
		}
	}

	/*
	 * Reduces the results in place in the array, and returns the result once
	 * we get down to a single polynomial at index 0. Each recursive call
	 * reduces the number of polynomials in half, discarding the data farther
	 * up in the array.
	 */
	private Polynomial reduceResults( final Polynomial[] results, int resultSize ) {
		if( resultSize == 1 )
			return results[0];

		int newResultSize = resultSize / 2;

		for( int i = 0; i < newResultSize; i++ ) {
			results[i] = results[i*2].subtract( results[i*2 + 1] );
		}

		return reduceResults( results, newResultSize / 2 );
	}
}
