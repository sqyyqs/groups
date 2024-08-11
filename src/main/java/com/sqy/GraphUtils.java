package com.sqy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GraphUtils {

    private GraphUtils() {
    }

    public static List<List<LineNode>> findConnectedComponents(List<LineNode> nodes) {
        List<List<LineNode>> components = new ArrayList<>();
        Set<LineNode> visited = new HashSet<>();

        for (LineNode node : nodes) {
            if (!visited.contains(node)) {
                List<LineNode> component = new ArrayList<>();
                dfs(node, visited, component);
                components.add(component);
            }
        }

        return components;
    }

    private static void dfs(LineNode node, Set<LineNode> visited, List<LineNode> component) {
        visited.add(node);
        component.add(node);

        for (LineNode adjNode : node.getAdjNodes()) {
            if (!visited.contains(adjNode)) {
                dfs(adjNode, visited, component);
            }
        }
    }
}
