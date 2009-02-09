/*
 * Provides the specifications for a graph coloring solver.
 * There's a single method - run - that takes a Graph as 
 * input and returns the chromatic number of that graph.
 */

public interface ColoringCore {
	public int run( Graph input );
}
