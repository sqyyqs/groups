package com.sqy;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        long start = System.nanoTime();
        groupAppStart(args);
        System.out.println((System.nanoTime() - start) / 1_000_000_000.00 + " сек.");
    }

    public static void groupAppStart(String[] args) {
        Set<LineNode> nodes = new HashSet<>();
        if (args.length == 0) {
            throw new IllegalArgumentException("необходимо указать путь в аргументах командной строки");
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            String buffer;
            while (checkLine(buffer = reader.readLine())) {
                nodes.add(new LineNode(buffer));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int maxElements = Integer.MIN_VALUE;
        for (LineNode node : nodes) {
            maxElements = Math.max(maxElements, node.getElements().length);
        }

        setEdges(nodes, maxElements);

        List<Set<LineNode>> connectedComponents = GraphUtils.findConnectedComponents(nodes)
                .stream()
                .filter(connectedComponent -> connectedComponent.size() > 1)
                .toList();

        writeResult("result.txt", connectedComponents);
    }

    private static void setEdges(Set<LineNode> lineNodes, int maxElements) {
        // перебор по колонкам
        for (int idx = 0; idx < maxElements; idx++) {
            Map<String, LineNode> columnBuffer = new HashMap<>();
            for (LineNode lineNode : lineNodes) {
                String[] elements = lineNode.getElements();
                if (idx >= elements.length) {
                    continue;
                }
                if ("\"\"".equals(elements[idx]) || "".equals(elements[idx])) {
                    continue;
                }
                columnBuffer.merge(elements[idx], lineNode, LineNode::addAdjNodeBiDirect);
            }
        }
    }


    private static boolean checkLine(String line) {
        return line != null && line.chars().filter(ch -> ch == '"').count() % 2 == 0;
    }

    private static void writeResult(String fileName, List<Set<LineNode>> connectedComponents) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(connectedComponents.size() + "\n\n");
            int counter = 1;
            for (Set<LineNode> connectedComponent : connectedComponents) {
                writer.write("Группа " + (counter++) + '\n');
                for (LineNode node : connectedComponent) {
                    writer.write(node.getLine() + '\n');
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}