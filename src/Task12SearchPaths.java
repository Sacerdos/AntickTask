import java.util.LinkedList;

public class Task12SearchPaths {

    private static final String START = "7"; //задаем точки начала и конца
    private static final String END = "9";

    public static void main(String[] args) {
        // this graph is directional
        Task12Graph graph = new Task12Graph();
        graph.addEdge("7", "11"); // задаем грани
        graph.addEdge("7", "8");
        graph.addEdge("5", "11");
        graph.addEdge("3", "8");
        graph.addEdge("3", "10");
        graph.addEdge("11", "2");
        graph.addEdge("11", "9");
        graph.addEdge("11", "10");
        graph.addEdge("8", "9");
        LinkedList<String> visited = new LinkedList(); // список посещенных
        visited.add(START); //добавляем в посещенные нашу точку отправления
        new Task12SearchPaths().depthFirst(graph, visited); //начало поиска
    }

    private void depthFirst(Task12Graph graph, LinkedList<String> visited) {
        LinkedList<String> nodes = graph.adjacentNodes(visited.getLast());
        for (String node : nodes) { //изучаем вершины смежные

            if (visited.contains(node)) { //если уже посещали то пропускаем
                continue;
            }
            if (node.equals(END)) {  //если дошли до конца, то пишем маршрут
                visited.add(node);
                printPath(visited);
                visited.removeLast();//удаляем последнюю посещенную вершину
                break;
            }
        }
        for (String node : nodes) {
            //System.out.println(node);
            if (visited.contains(node) || node.equals(END)) {
                continue;
            }//если посещали или конец, то пропускаем
            visited.addLast(node); // добавляем вершину
            depthFirst(graph, visited); //и сней рекурсивно идем глубже
            visited.removeLast(); //удаляем добавленную вершину
        }
    }

    private void printPath(LinkedList<String> visited) { //печатаем маршрут
        for (String node : visited) {
            System.out.print(node);
            System.out.print(" ");
        }
        System.out.println();
    }
}