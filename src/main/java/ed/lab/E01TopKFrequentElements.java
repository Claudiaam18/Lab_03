package ed.lab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class E01TopKFrequentElements {
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        // Paso 2: Crear buckets donde índice representa la frecuencia
        List<Integer>[] buckets = new List[nums.length + 1];
        for (int i = 0; i <= nums.length; i++) {
            buckets[i] = new ArrayList<>();
        }

        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            int number = entry.getKey();
            int freq = entry.getValue();
            buckets[freq].add(number);
        }

        List<Integer> result = new ArrayList<>();
        for (int i = buckets.length - 1; i >= 0 && result.size() < k; i--) {
            result.addAll(buckets[i]);
        }

        if (result.size() < k) {
            throw new IllegalArgumentException("k es demasiado grande, hay menos elementos únicos disponibles");
        }

        int[] topK = new int[k];
        for (int i = 0; i < k; i++) {
            topK[i] = result.get(i);
        }
        return topK;
    }
}
