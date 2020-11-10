package org.tsystems.javaschool.graph.path.impl;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.tsystems.javaschool.graph.Edge;
import org.tsystems.javaschool.graph.Vertex;
import org.tsystems.javaschool.graph.path.Path;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Trofim Kremen
 */
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class SimplePath<
        V extends Vertex<V, E>,
        E extends Edge<V, E>> implements Path<V, E> {

    private final V sourceVertex;
    private final List<E> edges;


    @Override
    public V getSourceVertex() {
        return  sourceVertex;
    }

    @Override
    public List<V> getVertices() {
        List<V> vertices = new ArrayList<>();
        vertices.add(sourceVertex);
        for (E edge : edges) {
            vertices.add(edge.getTargetVertex());
        }
        return vertices;
    }

    @Override
    public List<E> getEdges() {
        return edges;
    }

    @Override
    public V getTargetVertex() {
        return getLastVertex();
    }

    @Override
    public boolean containsVertex(V linkedVertex) {
        return sourceVertex.equals(linkedVertex) || edges.stream().allMatch(
                e -> e.getTargetVertex().equals(linkedVertex)
        );
    }

    @Override
    public boolean containsEdge(E linkedEdge) {
        return edges.contains(linkedEdge);
    }

    @Override
    public Path<V, E> AddEdge(E edge) {
        if (!getLastVertex().equals(edge.getSourceVertex())) {
            throw new IllegalArgumentException("Vertices are not adjacent. Unable to add edge to the path");
        }
        List<E> newEdges = new ArrayList<>(edges);
        newEdges.add(edge);
        return new SimplePath<>(sourceVertex, newEdges);
    }

    private V getLastVertex() {
        if (edges.isEmpty()) {
            return sourceVertex;
        } else {
            return getLastEdge().getTargetVertex();
        }
    }

    @Override
    public boolean isEmpty() {
        return edges.isEmpty();
    }

    @Override
    public E getLastEdge() {
        return edges.get(edges.size() - 1);
    }
}
