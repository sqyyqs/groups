package com.sqy;

import java.util.*;

public class LineNode {

    private final String line;
    private final Set<LineNode> adjNodes = new HashSet<>();
    private final String[] elements;

    public LineNode(String line) {
        this.line = line;
        this.elements = line.split(";");
    }


    public LineNode addAdjNodeBiDirect(LineNode lineNode) {
        adjNodes.add(lineNode);
        lineNode.addAdjNode(this);
        return this;
    }

    public LineNode addAdjNode(LineNode lineNode) {
        if (!this.equals(lineNode)) {
            adjNodes.add(lineNode);
        }
        return this;
    }


    public String getLine() {
        return line;
    }

    public String[] getElements() {
        return elements;
    }

    public Set<LineNode> getAdjNodes() {
        return adjNodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineNode lineNode = (LineNode) o;
        return Objects.equals(line, lineNode.line);
    }

    @Override
    public int hashCode() {
        return Objects.hash(line);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LineNode{");
        sb.append("line='").append(line).append('\'');
        sb.append(", adjNodes=").append(adjNodes.stream().map(LineNode::getLine).toList());
        sb.append('}');
        return sb.toString();
    }
}
