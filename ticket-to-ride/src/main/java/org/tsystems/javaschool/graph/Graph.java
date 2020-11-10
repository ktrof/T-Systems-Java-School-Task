package org.tsystems.javaschool.graph;

/**
 * The interface Graph.
 *
 * @param <V> the Vertex type parameter
 * @param <E> the Edge type parameter
 * @author Trofim Kremen
 */
public interface Graph<
        V extends Vertex<V, E>,
        E extends Edge<V, E>> {

}
