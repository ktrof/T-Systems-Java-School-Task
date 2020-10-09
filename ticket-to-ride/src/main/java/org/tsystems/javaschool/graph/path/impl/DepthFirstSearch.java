package org.tsystems.javaschool.graph.path.impl;

import org.tsystems.javaschool.graph.Edge;
import org.tsystems.javaschool.graph.Vertex;
import org.tsystems.javaschool.graph.path.Path;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Trofim Kremen
 */
public class DepthFirstSearch {

    public static <V extends Vertex<V, E>,
            E extends Edge<V, E>>
    Collection<Path<V, E>> findAllSimplePaths(V sourceVertex, V targetVertex) {
        Set<Path<V, E>> pathsToSourceVertex = new HashSet<>();
        pathsToSourceVertex.add(Path.startFrom(sourceVertex));
        return recursiveFindAllSimplePaths(sourceVertex, targetVertex, pathsToSourceVertex);
    }

    private static <V extends Vertex<V, E>,
            E extends Edge<V, E>>
    Collection<Path<V, E>> recursiveFindAllSimplePaths(V sourceVertex, V targetVertex,
                                              Collection<Path<V, E>> pathsToSourceVertex) {

        if (pathsToSourceVertex.isEmpty()) {
            return Collections.emptyList();
        }
        if (Objects.equals(sourceVertex, targetVertex)) {
            return pathsToSourceVertex;
        }

        Collection<E> outgoingEdges = sourceVertex.getAllOutgoingEdges();
        return outgoingEdges.stream()
                .filter(nextEdge -> pathsToSourceVertex.stream().noneMatch(path -> path.containsEdge(nextEdge)))
                .flatMap(nextEdge -> {
                    V nextVertexSource = nextEdge.getSourceVertex();
                    Collection<Path<V, E>> nextPathToSource = pathsToSourceVertex.stream()
                            .map(path -> path.AddEdge(nextEdge))
                            .collect(Collectors.toList());
                    return recursiveFindAllSimplePaths(nextVertexSource, targetVertex, nextPathToSource).stream();
                })
                .collect(Collectors.toList());
    }

}
