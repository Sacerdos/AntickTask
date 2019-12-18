import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;


class Task13 {
    private int V;   // Количество вершин
    private LinkedList<Integer> adj[]; // Список "соседств"
    private ArrayList<Integer> check = new ArrayList<>(); //для проверки

    //Конструктор
    Task13(int v) {
        V = v;
        adj = new LinkedList[v];//список размером с количество вершин
        for (int i = 0; i < v; ++i)
            adj[i] = new LinkedList();//каждому элементу свой список "соседств"
    }

    // Добавление грани в граф
    void addEdge(int v, int w) {
        adj[v].add(w);
        check.add(v);//проверочное хранение
        check.add(w);
    }

    // Рекурсивная функция из topologicalSort
    void topologicalSortUtil(int v, boolean visited[],
                             Stack stack) {
        // Отмечаем, что эту "ветку" посетили.
        visited[v] = true;
        Integer i;

        // Входим в рекурсию для всех вершин по соседству
        Iterator<Integer> it = adj[v].iterator();
        while (it.hasNext()) {
            i = it.next();
            if (!visited[i])
                topologicalSortUtil(i, visited, stack);
        }
        //System.out.println(v + " " + stack);
        stack.push(v);//кладем номер вершины в стек
    }

    //вызывабщий метод
    void topologicalSort() {
        Stack stack = new Stack();

        // отмечаем все вершины "непосещенными"
        boolean visited[] = new boolean[V];
        for (int i = 0; i < V; i++)
            visited[i] = false;

        // Вызываем функцию сортировки по порядку
        for (int i = 0; i < V; i++)
            if (!visited[i] && check.contains(i)) { // учитываем только упоминаемые вершины
                System.out.println(i + " " + stack);
                topologicalSortUtil(i, visited, stack);
            }

        //Выдача результата
        while (!stack.empty())
            System.out.print(stack.pop() + " ");
    }

    public static void main(String args[]) {
        Task13 g = new Task13(11);
        g.addEdge(4, 10);
        g.addEdge(10, 1);
        g.addEdge(6, 10);
        g.addEdge(6, 7);
        g.addEdge(10, 8);
        g.addEdge(10, 9);
        g.addEdge(7, 8);
        g.addEdge(2, 7);
        g.addEdge(2, 9);
        g.topologicalSort();
    }
}