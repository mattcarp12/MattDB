# MattDB

This project is a fork of the [SimpleDB](http://www.cs.bc.edu/~sciore/simpledb/intro.html) project.

I have implemented many features that are absent from SimpleDB, including:
 * Space optimized support for null values
 * LRU buffer management
 * Diagnostics reporting
 * Use of hash table for buffer lookups
 * Bug fixes in metadata manager
   * Prevent duplicate tables, views, indexes, from being created
 * Unit tests
 