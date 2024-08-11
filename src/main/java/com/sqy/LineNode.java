package com.sqy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LineNode {

    private final String line;
    private final List<LineNode> adjNodes = new ArrayList<>();
    private final String[] elements;

    public LineNode(String line) {
        this.line = line;
        this.elements = line.split(";");
    }

    public LineNode addAdjNodeBidirect(LineNode lineNode) {
        adjNodes.add(lineNode);
        lineNode.addAdjNode(this);
        return this;
    }

    private void addAdjNode(LineNode lineNode) {
        adjNodes.add(lineNode);
    }

    public String getLine() {
        return line;
    }

    public String[] getElements() {
        return elements;
    }

    public List<LineNode> getAdjNodes() {
        return adjNodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineNode lineNode = (LineNode) o;
        return Objects.equals(line, lineNode.line) && Objects.equals(adjNodes, lineNode.adjNodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(line);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LineNode{");
        sb.append("line='").append(line).append('\'');
        sb.append(", adjNodes(size)=").append(adjNodes.size());
        sb.append('}');
        return sb.toString();
    }
}
