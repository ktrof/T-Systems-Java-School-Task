package org.tsystems.javaschool.graph;

/**
 * The interface Edge.
 *
 * @param <V> the Vertex type parameter
 * @param <E> the Edge type parameter
 * @author Trofim Kremen
 */
public interface Edge<
        V extends Vertex<V, E>,
        E extends Edge<V, E>> {

    /**
     * Gets source vertex.
     *
     * @return the source vertex
     */
    V getSourceVertex();

    /**
     * Gets target vertex.
     *
     * @return the target vertex
     */
    V getTargetVertex();

}
