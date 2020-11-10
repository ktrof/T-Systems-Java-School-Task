package org.tsystems.javaschool.graph.path;

import org.tsystems.javaschool.graph.Edge;
import org.tsystems.javaschool.graph.Vertex;
import org.tsystems.javaschool.graph.path.impl.SimplePath;

import java.util.Collections;
import java.util.List;

/**
 * The interface Path.
 *
 * @param <V> the Vertex type parameter
 * @param <E> the Edge type parameter
 */
public interface Path<
        V extends Vertex<V, E>,
        E extends Edge<V, E>> {

    /**
     * Start from path.
     *
     * @param <V>          the Vertex type parameter
     * @param <E>          the Edge type parameter
     * @param sourceVertex the source vertex
     * @return the path
     */
    static <V extends Vertex<V, E>,
            E extends Edge<V, E>> Path<V, E> startFrom(V sourceVertex) {
        return new SimplePath<>(sourceVertex, Collections.emptyList());
    }

    /**
     * Gets source vertex.
     *
     * @return the source vertex
     */
    V getSourceVertex();

    /**
     * Gets vertices.
     *
     * @return the vertices
     */
    List<V> getVertices();

    /**
     * Gets edges.
     *
     * @return the edges
     */
    List<E> getEdges();

    /**
     * Gets target vertex.
     *
     * @return the target vertex
     */
    V getTargetVertex();

    /**
     * Contains vertex boolean.
     *
     * @param linkedVertex the linked vertex
     * @return the boolean
     */
    boolean containsVertex(V linkedVertex);

    /**
     * Contains edge boolean.
     *
     * @param linkedEdge the linked edge
     * @return the boolean
     */
    boolean containsEdge(E linkedEdge);

    /**
     * Add edge path.
     *
     * @param edge the edge
     * @return the path
     */
    Path<V, E> AddEdge(E edge);

    /**
     * Is empty boolean.
     *
     * @return the boolean
     */
    boolean isEmpty();

    /**
     * Gets last edge.
     *
     * @return the last edge
     */
    E getLastEdge();

}
