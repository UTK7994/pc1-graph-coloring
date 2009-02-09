import java.io.*;

/*
 * This class takes the command line arguments and a ColoringCore
 * object. It performs the input, does the timing measurements, 
 * and uses the ColoringCore to calculate the answer.
 */

public class ColoringMain {
	private GraphInputReader inReader;
	private ColoringCore core;

	public ColoringMain( String inFile, ColoringCore core ) {
		this.inReader = new GraphInputReader( inFile );
		this.core = core;
	}

	public void run() {
		// record the start time
		long t1 = System.currentTimeMillis();

		// read in the input
		Graph inGraph = inReader.readInput();
		
		// do the calculations
		int chromaticNumber = core.run( inGraph );

		// print out the output here
		System.out.println( "The chromatic number is: " + chromaticNumber );

		// record and print the end time
		long t2 = System.currentTimeMillis();
		System.out.println( "Runtime: " + (t2 - t1) + "ms" );
	}
}
