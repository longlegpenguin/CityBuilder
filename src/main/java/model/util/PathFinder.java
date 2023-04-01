package model.util;

import model.Buildable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PathFinder {

    private Buildable[][] map;

    public PathFinder(Buildable[][] map) {
        this.map = map;
    }

    /**
     * Calculates the euclidean distance between two buildables
     *
     * @param start the starting buildable
     * @param goal  the goal buildable
     * @return the euclidean distance
     */
    public double euclideanDistance(Buildable start, Buildable goal) {
        Coordinate star = start.getCoordinate();
        Coordinate goa = goal.getCoordinate();
        return Math.sqrt(
                (star.getRow() - goa.getRow()) ^ 2 +
                        (star.getCol() - goa.getCol()) ^ 2
        );
    }

    /**
     * Calculates the manhattan distance between two buildables
     *
     * @param start the starting buildable
     * @param goal  the goal buildable
     * @return the manhattan distance
     */
    public int manhattanDistance(Buildable start, Buildable goal) {
        List<Node> graph = new ArrayList<>();
        Node result = BFS(start, goal, graph);
        return result == null ? -1 : result.cost;
    }

    class Node {
        Node parent;
        int cost;
        Buildable self;

        public Node(Node parent, int cost, Buildable self) {
            this.parent = parent;
            this.cost = cost;
            this.self = self;
        }

        int getRow() {
            return self.getCoordinate().getRow();
        }

        int getCol() {
            return self.getCoordinate().getCol();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return self.equals(node.self);
        }

        @Override
        public int hashCode() {
            return Objects.hash(self);
        }
    }

    private Node BFS(Buildable start, Buildable goal, List<Node> graph) {
        List<Node> opens = new ArrayList<>();
        Node current;
        Node goalNode = new Node(null, -1, goal);
        opens.add(new Node(null, 0, start));
        while (true) {
            if (opens.isEmpty()) {
                return null;
            }

            current = opens.get(0);
            if (isGoal(current, goalNode)) {
                return current;
            }
            opens.remove(0);
            graph.add(current);
            for (Node node : successors(current)) {
                if (!graph.contains(node) && node != current && node != current.parent) {
                    opens.add(node);
                    graph.add(node);
                }
            }
        }
    }

    /**
     * Decides if goal state is reached
     *
     * @param currentItem the current search state
     * @param goal        the goal state
     * @return true if reached, otherwise, false
     */
    private boolean isGoal(Node currentItem, Node goal) {
        int cRow = currentItem.getRow();
        int cCol = currentItem.getCol();
        int gRow = goal.getRow();
        int gCol = goal.getCol();
        return (cRow + 1 == gRow && cCol == gCol) ||
                (cRow - 1 == gRow && cCol == gCol) ||
                (cRow == gRow && cCol + 1 == gCol) ||
                (cRow == gRow && cCol - 1 == gCol);
    }

    /**
     * Generates a list of director neighbours of a node.
     * With the node as parent and one more in cost.
     *
     * @param current the node to search for neighbours
     * @return the list of neighbours
     */
    private List<Node> successors(Node current) {
        ArrayList<Node> successors = new ArrayList<>();
        int sRow = current.getRow();
        int sCol = current.getCol();
        if (isRoad(sRow - 1, sCol)) {
            successors.add(new Node(current, current.cost + 1, map[sRow - 1][sCol]));
        }
        if (isRoad(sRow + 1, sCol)) {
            successors.add(new Node(current, current.cost + 1, map[sRow + 1][sCol]));
        }
        if (isRoad(sRow, sCol - 1)) {
            successors.add(new Node(current, current.cost + 1, map[sRow][sCol - 1]));
        }
        if (isRoad(sRow, sCol + 1)) {
            successors.add(new Node(current, current.cost + 1, map[sRow][sCol + 1]));
        }
        return successors;
    }

    private boolean isRoad(int sRow, int sCol) {
        return sRow < map.length && sRow >= 0 &&
                sCol < map[0].length && sCol >= 0 &&
                map[sRow][sCol] != null &&
                map[sRow][sCol].getBuildableType() == BuildableType.ROAD;
    }
}

