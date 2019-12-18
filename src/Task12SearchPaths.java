import java.util.LinkedList;

public class Task12SearchPaths {

    private static final String START = "7";
    private static final String END = "9";

    public static void main(String[] args) {
        // this graph is directional
        Task12Graph graph = new Task12Graph();
        graph.addEdge("7", "11");
        graph.addEdge("7", "8");
        graph.addEdge("5", "11");
        graph.addEdge("3", "8");
        graph.addEdge("3", "10");
        graph.addEdge("11", "2");
        graph.addEdge("11", "9");
        graph.addEdge("11", "10");
        graph.addEdge("8", "9");
        LinkedList<String> visited = new LinkedList();
        visited.add(START);
        new Task12SearchPaths().depthFirst(graph, visited);
    }

    private void depthFirst(Task12Graph graph, LinkedList<String> visited) {
        LinkedList<String> nodes = graph.adjacentNodes(visited.getLast());
        // examine adjacent nodes
        for (String node : nodes) {
            if (visited.contains(node)) {
                continue;
            }
            if (node.equals(END)) {
                visited.add(node);
                printPath(visited);
                visited.removeLast();
                break;
            }
        }
        for (String node : nodes) {
            System.out.println("2--  ");
            printPath(visited);
            if (visited.contains(node) || node.equals(END)) {
                continue;
            }
            visited.addLast(node);
            depthFirst(graph, visited);
            visited.removeLast();
        }
    }

    private void printPath(LinkedList<String> visited) {
        for (String node : visited) {
            System.out.print(node);
            System.out.print(" ");
        }
        System.out.println();
    }
}