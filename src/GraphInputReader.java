import java.io.*;
import java.util.regex.*;

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

			readEdgeData( result );

			return result;
		}
		catch( IOException e ) {
			System.err.println( "Error: unable to read the input file!" );
			System.exit( -1 );
		}
		// we will never get here, but the java compiler insists on this statement
		return null;
	}

	/*
	 * Reads in the edge data from 'input', parses it, and adds the 
	 * appropriate edges to the graph passed in.
	 */
	private void readEdgeData( Graph g ) {
		// each line will consist of a vertex number, followed by whitespace, 
		// followed by another vertex number
		Pattern edgePattern = Pattern.compile( "^([0-9]+)\\s([0-9]+)$" );
		Matcher edgeMatcher = null;
		String inLine = null;
		String v1, v2;

		// try to read in the first edge
		try {
			inLine = input.readLine();
		}
		catch( IOException e ) {
			System.err.println( "Error reading graph data from input file!" );
			System.exit( -1 );
		}

		// parse the input if there was any, and keep going 'til we run out
		while( inLine != null ) {
			edgeMatcher = edgePattern.matcher( inLine );
			edgeMatcher.find();
			v1 = edgeMatcher.group( 1 );
			v2 = edgeMatcher.group( 2 );

			try {
				g.addEdge( Integer.parseInt(v1), Integer.parseInt(v2) );
			}
			catch( NumberFormatException e ) {
				System.err.println( "Error parsing graph data from input file!" );
				System.exit( -1 );
			}

			// next line:
			try {
				inLine = input.readLine();
			}
			catch( IOException e ) {
				System.err.println( "Error reading graph data from input file!" );
				System.exit( -1 );
			}
		}
	}
}
