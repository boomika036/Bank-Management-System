package index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomerIndex {

    private final HashMap<String, List<Integer>> index = new HashMap<>();

    public void add(String customerName, int accountId) {
        index.computeIfAbsent(customerName, k -> new ArrayList<>())
             .add(accountId);
    }

    public List<Integer> find(String customerName) {
        return index.getOrDefault(customerName, new ArrayList<>());
    }
}