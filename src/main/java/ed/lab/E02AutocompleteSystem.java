package ed.lab;

import java.util.*;

public class E02AutocompleteSystem {

    private final Map<String, Integer> freq = new HashMap<>();
    private final StringBuilder current = new StringBuilder();

    public E02AutocompleteSystem(String[] sentences, int[] times) {
        for (int i = 0; i < sentences.length; i++) {
            freq.put(sentences[i], times[i]);
        }
    }

    public List<String> input(char c) {
        if (c == '#') {
            String sentence = current.toString();
            freq.put(sentence, freq.getOrDefault(sentence, 0) + 1);
            current.setLength(0);
            return Collections.emptyList();
        }

        current.append(c);
        String prefix = current.toString();

        PriorityQueue<String> pq = new PriorityQueue<>(3, (a, b) -> {
            int fa = freq.get(a);
            int fb = freq.get(b);
            if (fa != fb) return fb - fa;
            return a.compareTo(b);
        });

        for (String s : freq.keySet()) {
            if (s.startsWith(prefix)) {
                pq.offer(s);
                if (pq.size() > 3) pq.poll();
            }
        }

        List<String> res = new ArrayList<>();
        while (!pq.isEmpty()) res.add(pq.poll());
        Collections.reverse(res);
        return res;
    }
}
