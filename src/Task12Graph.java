import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class Task12Graph {
    private Map<String, LinkedHashSet<String>> map = new HashMap();

    public void addEdge(String node1, String node2) { //добавить грань
        LinkedHashSet<String> adjacent = map.get(node1); //ищем есть ли примыкающие
        if(adjacent==null) { //если таких нет создаем множество примыкающих
            adjacent = new LinkedHashSet();
            map.put(node1, adjacent);
        }
        adjacent.add(node2);//добавить грань окончательно
    }
    public LinkedList<String> adjacentNodes(String last) { //возвращает список примыкающих вершин
        LinkedHashSet<String> adjacent = map.get(last); // достает список
        if(adjacent==null) { // если аткого нет, то возврашаем пустой список
            return new LinkedList();
        }
        return new LinkedList<String>(adjacent);
    }
}