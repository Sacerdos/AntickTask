import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

// This class represents a directed graph using adjacency
// list representation
class Task13 {
    private int V;   // No. of vertices
    private LinkedList<Integer> adj[]; // Adjacency List
    private ArrayList<Integer> check = new ArrayList<>();

    //Constructor
    Task13(int v) {
        V = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i)
            adj[i] = new LinkedList();
    }

    // Function to add an edge into the graph
    void addEdge(int v, int w) {
        adj[v].add(w);
        check.add(v);
        check.add(w);
    }

    // A recursive function used by topologicalSort
    void topologicalSortUtil(int v, boolean visited[],
                             Stack stack) {
        // Mark the current node as visited.
        visited[v] = true;
        Integer i;

        // Recur for all the vertices adjacent to this
        // vertex
        Iterator<Integer> it = adj[v].iterator();
        while (it.hasNext()) {
            i = it.next();
            if (!visited[i])
                topologicalSortUtil(i, visited, stack);
        }
        stack.push(v);
    }

    // The function to do Topological Sort. It uses
    // recursive topologicalSortUtil()
    void topologicalSort() {
        Stack stack = new Stack();

        // Mark all the vertices as not visited
        boolean visited[] = new boolean[V];
        for (int i = 0; i < V; i++)
            visited[i] = false;

        // Call the recursive helper function to store
        // Topological Sort starting from all vertices
        // one by one
        for (int i = 0; i < V; i++)
            if (visited[i] == false && check.contains(i))
                topologicalSortUtil(i, visited, stack);

        // Print contents of stack
        while (stack.empty() == false)
            System.out.print(stack.pop() + " ");


    }

    // Driver method
    public static void main(String args[]) {
        // Create a graph given in the above diagram
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