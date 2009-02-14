import java.awt.Point; // ahh, if only Java had tuples...

/*
 * Provides a smp implementation of the deletion-contraction algorithm.
 */

public class ColoringCoreSmp extends ColoringCoreSeq {
	public int run( Graph input ) {
		// first check if we have an empty graph
		if( input.isEmpty() ) {
			return 1;
		}

		// FIXME find the chromatic ploynomial using smp here:
		Polynomial result = new Polynomial( 1, 1 );

		// and once we get the chromatic polynomial, 
		// find the min number of colors needed
		int k = findMinColorsNeeded( result );

		return k;
	}
}
