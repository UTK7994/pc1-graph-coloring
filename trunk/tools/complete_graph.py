#!/usr/bin/python

# This script accepts a number of vertices n,
# and outputs a complete graph on n vertices.

import sys

n = int( sys.argv[1] )

print n

for v in range( 0, n - 1 ):
	for w in range( v + 1, n ):
		print "%i\t%i" % ( v, w )
