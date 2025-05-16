package ed.lab;

import java.util.*;

public class E02AutocompleteSystem {

    private final TrieNode root = new TrieNode();
    private final StringBuilder current = new StringBuilder();

    public E02AutocompleteSystem(String[] sentences, int[] times) {
        for (int i = 0; i < sentences.length; i++) {
            insert(sentences[i], times[i]);
        }
    }

    public List<String> input(char c) {
        if (c == '#') {
            String sentence = current.toString();
            insert(sentence, 1);
            current.setLength(0);
            return Collections.emptyList();
        }

        current.append(c);
        return search(current.toString());
    }

    private void insert(String sentence, int count) {
        TrieNode node = root;
        for (char ch : sentence.toCharArray()) {
            node.children.putIfAbsent(ch, new TrieNode());
            node = node.children.get(ch);
        }
        node.freq.put(sentence, node.freq.getOrDefault(sentence, 0) + count);
    }

    private List<String> search(String prefix) {
        TrieNode node = root;
        for (char ch : prefix.toCharArray()) {
            if (!node.children.containsKey(ch)) {
                return Collections.emptyList();
            }
            node = node.children.get(ch);
        }

        // Obtener todas las oraciones con sus frecuencias siguiendo desde este nodo
        Map<String, Integer> sentencesMap = new HashMap<>();
        collectSentences(node, sentencesMap);

        // Ordenar oraciones por frecuencia descendente y lexicografía ascendente
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(
                (a, b) -> {
                    if (!a.getValue().equals(b.getValue())) {
                        return b.getValue() - a.getValue();
                    }
                    return a.getKey().compareTo(b.getKey());
                }
        );

        pq.addAll(sentencesMap.entrySet());

        List<String> res = new ArrayList<>(3);
        for (int i = 0; i < 3 && !pq.isEmpty(); i++) {
            res.add(pq.poll().getKey());
        }

        return res;
    }

    private void collectSentences(TrieNode node, Map<String, Integer> sentencesMap) {
        for (Map.Entry<String, Integer> entry : node.freq.entrySet()) {
            sentencesMap.put(entry.getKey(), entry.getValue());
        }
        for (TrieNode child : node.children.values()) {
            collectSentences(child, sentencesMap);
        }
    }

    private static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        Map<String, Integer> freq = new HashMap<>();
    }
}

