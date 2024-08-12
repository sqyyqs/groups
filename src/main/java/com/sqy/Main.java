package com.sqy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) {
        long start = System.nanoTime();
        groupAppStart(args);
        System.out.println((System.nanoTime() - start) / 1_000_000_000.00 + " сек.");
    }

    public static void groupAppStart(String[] args) {
        List<LineNode> lineNodes = new ArrayList<>();
        int maxElements = Integer.MIN_VALUE;
        if (args.length == 0) {
            throw new IllegalArgumentException("необходимо указать путь в аргументах командной строки");
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            String line;
            while (checkLine(line = reader.readLine())) {
                LineNode lineNode = new LineNode(line);
                lineNodes.add(lineNode);
                maxElements = Math.max(maxElements, lineNode.getElements().length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        setEdges(lineNodes, maxElements);

        List<List<LineNode>> connectedComponents = GraphUtils.findConnectedComponents(lineNodes);

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

    private static void setEdges(List<LineNode> lineNodes, int maxElements) {
        // перебор по колонкам
        for (int idx = 0; idx < maxElements; idx++) {
            Map<String, LineNode> columnBuffer = new HashMap<>();
            for (LineNode lineNode : lineNodes) {
                String[] elements = lineNode.getElements();
                if (elements.length < (idx + 1)) {
                    continue;
                }
                if ("\"\"".equals(elements[idx])) {
                    continue;
                }
                columnBuffer.merge(elements[idx], lineNode, LineNode::addAdjNode);
            }
            columnBuffer.clear();
        }
    }


    private static boolean checkLine(String line) {
        return line != null && line.chars().filter(ch -> ch == '"').count() % 2 == 0;
    }
}