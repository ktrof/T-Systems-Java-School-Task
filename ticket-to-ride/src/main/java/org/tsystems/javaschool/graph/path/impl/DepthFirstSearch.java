package org.tsystems.javaschool.graph.path.impl;

import lombok.extern.slf4j.Slf4j;
import org.tsystems.javaschool.graph.Edge;
import org.tsystems.javaschool.graph.Vertex;
import org.tsystems.javaschool.graph.path.Path;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Trofim Kremen
 */
@Slf4j
public class DepthFirstSearch {

    public static <V extends Vertex<V, E>,
            E extends Edge<V, E>>
    Collection<Path<V, E>> findAllSimplePaths(V sourceVertex, V targetVertex, PathEdgeFilter<V, E> pathEdgeFilter) {
        Set<Path<V, E>> pathsToSourceVertex = new HashSet<>();
        pathsToSourceVertex.add(Path.startFrom(sourceVertex));
        return recursiveFindAllSimplePaths(sourceVertex, targetVertex, pathsToSourceVertex, pathEdgeFilter);
    }

    private static <V extends Vertex<V, E>,
            E extends Edge<V, E>>
    Collection<Path<V, E>> recursiveFindAllSimplePaths(V sourceVertex, V targetVertex,
                                                       Collection<Path<V, E>> pathsToSourceVertex,
                                                       PathEdgeFilter<V, E> pathEdgeFilter) {

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
                    V nextVertexSource = nextEdge.getTargetVertex();
                    Collection<Path<V, E>> nextPathToSource = pathsToSourceVertex.stream()
                            .filter(path -> pathEdgeFilter.mayAppend(nextEdge, path))
                            .map(path -> path.AddEdge(nextEdge))
                            .collect(Collectors.toList());
                    return recursiveFindAllSimplePaths(nextVertexSource, targetVertex, nextPathToSource, pathEdgeFilter).stream();
                })
                .collect(Collectors.toList());
    }

    @FunctionalInterface
    public interface PathEdgeFilter<
            V extends Vertex<V, E>,
            E extends Edge<V, E>> {

        boolean mayAppend(E nextEdge, Path<V, E> currentPath);
    }

}
