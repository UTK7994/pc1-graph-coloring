import java.io.*;

/*
 * This class takes an input file name in the constructor,
 * and then provides a method to read the file, parse the 
 * contents, and create a new Graph object based on the results.
 */

public class GraphInputReader {
	private BufferedReader input;

	public GraphInputReader( String fileName ) {
		FileInputStream fileStream = null;
		try {
			fileStream = new FileInputStream( fileName );
		}
		catch( FileNotFoundException e ) {
			System.err.println( "Error: Input file '" + fileName + "' not found!" );
			System.exit( -1 );
		}

		this.input = new BufferedReader( new InputStreamReader(fileStream) );
	}

	public Graph readInput() {
		try {
			// read the initial int that will tell us how
			// many vertices are in the input
			int numPoints = 0;
			try {
				numPoints = Integer.parseInt( input.readLine() );
			}
			catch( NumberFormatException e ) {
				System.err.println( "Error parsing input file!" );
				System.exit( -1 );
			}

			Graph result = new Graph( numPoints );

			// FIXME
			// read in the edges and add them to the graph before we return it!

			return result;
		}
		catch( IOException e ) {
			System.err.println( "Error: unable to read the input file!" );
			System.exit( -1 );
		}
		// we will never get here, but the java compiler insists on this statement
		return null;
	}
}
