import edu.rit.pj.Comm;
import java.io.*;

/*
 * Simply checks for the right number of arguments,
 * creates a new smp core, and starts up
 * the main program.
 */

public class ColoringSmp {
	public static void main( String[] args ) {
		try {
			Comm.init( args );
		}
		catch( IOException e ) {
			e.printStackTrace();
		}
		// make sure we have the right number of args
		checkArgs( args );
		// create an instance of our smp core
		ColoringCoreSmp core = new ColoringCoreSmp();
		// and start the main program
		new ColoringMain( args[0], core ).run();
	}

	private static void checkArgs( String[] args ) {
		// check for appropriate number of args
		if( args.length != 1 ) {
			System.err.println( "Error - expected 1 command line argument." );
			System.err.println( "Usage: java ColoringSmp <inputfile>" );
			System.exit(-1);
		}
	}
}
