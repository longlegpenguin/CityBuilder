package model.util;

import model.Buildable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
        Node result = DFS(start, goal, graph);

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
    }

    private Node DFS(Buildable start, Buildable goal, List<Node> graph) {
        List<Node> opens = new LinkedList<>();
        Node current;
        Node goalNode = new Node(null, -1, goal);
        opens.add(new Node(null, 0, start));
        while (true) {
            if (opens.isEmpty()) { return null; }

            current = opens.get(1);
            if (isGoal(current, goalNode)) {
                return current;
            }
            opens.remove(1);
            for (Node node : successors(current, goalNode)) {
                if (node != current.parent && !graph.contains(node)) {
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
        return currentItem.self == goal.self;
    }

    /**
     * Generates a list of director neighbours of a node.
     * With the node as parent and one more in cost.
     *
     * @param current the node to search for neighbours
     * @return the list of neighbours
     */
    private List<Node> successors(Node current, Node goal) {
        ArrayList<Node> successors = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int sRow = current.getRow() + i;
                int sCol = current.getCol() + j;
                if (isRoad(sRow, sCol) || isGoal(current, goal)) {
                    successors.add(
                            new Node(
                                    current,
                                    current.cost + 1,
                                    map[sRow][sCol]
                            ));
                }
            }
        }
        return successors;
    }

    private boolean isRoad(int sRow, int sCol) {
        return sRow < map.length && sRow >= 0 &&
                sCol < map[0].length && sCol >= 0 &&
                map[sRow][sCol].getBuildableType() == BuildableType.ROAD;
    }
}

