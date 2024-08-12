package com.sqy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GraphUtils {

    private GraphUtils() {
    }

    public static List<Set<LineNode>> findConnectedComponents(Set<LineNode> graph) {
        List<Set<LineNode>> components = new ArrayList<>();
        Set<LineNode> visited = new HashSet<>();

        for (LineNode node : graph) {
            if (!visited.contains(node)) {
                Set<LineNode> component = new HashSet<>();
                dfs(node, visited, component);
                components.add(component);
            }
        }
        return components;
    }

    private static void dfs(LineNode node, Set<LineNode> visited, Set<LineNode> component) {
        visited.add(node);
        component.add(node);

        for (LineNode neighbor : node.getAdjNodes()) {
            if (!visited.contains(neighbor)) {
                dfs(neighbor, visited, component);
            }
        }
    }

}
