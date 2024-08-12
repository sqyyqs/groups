package com.sqy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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

        List<Set<LineNode>> connectedComponents = GraphUtils.findConnectedComponents(nodes);

        System.out.println(connectedComponents.stream().filter(components -> components.size() > 1).count());

        AtomicInteger atomicInteger = new AtomicInteger(1);
        connectedComponents.stream()
                .sorted((a, b) -> Integer.compare(b.size(), a.size()))
                .forEach(connected -> {
                    System.out.println("Группа " + atomicInteger.getAndIncrement());
                    connected.forEach(node -> System.out.println(node.getLine()));
                    System.out.println();
                });
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
            columnBuffer.clear();
        }
    }


    private static boolean checkLine(String line) {
        return line != null && line.chars().filter(ch -> ch == '"').count() % 2 == 0;
    }
}