/*
 * Implements a mutable representation of a polynomial
 * of arbitrary degree. Coefficients can be modified,
 * other polynomials can be added or subtracted, the 
 * polynomial can be evaluated at different values of 
 * X, etc.
 */

public class Polynomial {
	int[] coefficients;

	public Polynomial( int degree ) {
		// allocate coefficients for up to 
		// x^(degree) all the way down to 
		// the contstant term at the end
		coefficients = new int[ degree + 1 ];
		
		for( int i = 0; i <= degree; i++ ) {
			coefficients[ i ] = 0;
		}
	}

	/*
	 * Creates a new polynomial of degree given, with the coefficient
	 * specified on x^degree
	 */
	public Polynomial( int degree, int coef ) {
		this( degree );

		coefficients[ degree ] = coef;
	}

	/*
	 * Add val to the coefficient specified.
	 */
	public void addToCoefficient( int val, int coef ) {
		coefficients[ coef ] += val;
	}

	/*
	 * Subtract val from the coefficient specified.
	 */
	public void subFromCoefficient( int val, int coef ) {
		coefficients[ coef ] -= val;
	}

	/*
	 * Add an entire polynomial to this one. Returns a reference to this Polynomial object.
	 */
	public Polynomial add( Polynomial val ) {
		for( int i = 0; i < coefficients.length && i < val.coefficients.length; i++ ) {
			this.coefficients[ i ] += val.coefficients[ i ];
		}

		return this;
	}

	/*
	 * Subtract an entire polynomial from this one. Returns a reference to this Polynomial object.
	 */
	public Polynomial subtract( Polynomial val ) {
		for( int i = 0; i < coefficients.length && i < val.coefficients.length; i++ ) {
			this.coefficients[ i ] -= val.coefficients[ i ];
		}

		return this;
	}

	/*
	 * Evaluates this polynomial at the desired value of X
	 */
	public long evaluate( long xVal ) {
		long result = coefficients[ 0 ];

		for( int i = 1; i < coefficients.length; i++ ) {
			result += coefficients[ i ]*Math.pow( xVal, i );
		}

		return result;
	}

	public String toString() {
		String s = "";

		for( int i = coefficients.length - 1; i > 0; i-- ) {
			s += ( coefficients[i] + "x^" + i + " + " );
		}

		s += coefficients[0];
		return s;
	}
}
