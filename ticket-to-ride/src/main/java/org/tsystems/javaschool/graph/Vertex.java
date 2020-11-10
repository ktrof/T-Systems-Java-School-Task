package org.tsystems.javaschool.graph;

import java.util.Collection;

/**
 * The interface Vertex.
 *
 * @param <V> the Vertex type parameter
 * @param <E> the Edge type parameter
 * @author Trofim Kremen
 */
public interface Vertex<
        V extends Vertex<V, E>,
        E extends Edge<V, E>> {

    /**
     * Gets all outgoing edges.
     *
     * @return the all outgoing edges
     */
    Collection<E> getAllOutgoingEdges();

}
