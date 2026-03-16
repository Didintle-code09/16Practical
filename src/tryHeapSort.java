/*
 * tryHeapsort.java
 * Author: Didi
 *
 * NOTE: This code was developed with the assistance of Microsoft Copilot
 * and ChatGPT for debugging guidance.
 *
 * Task: Build a heap from the words in "joyce1922_ulysses.text"
 * and compare bottom-up vs top-down heap construction for sorting.
 */

import java.io.*;
import java.util.*;

public class tryHeapSort {


    static class Heap {
        private String[] heap;
        private int size;

        public Heap(int capacity) {
            heap = new String[capacity];
            size = 0;
        }

        // Insert top-down
        public void insert(String word) {
            heap[size] = word;
            int current = size;
            size++;

            while (current > 0) {
                int parent = (current - 1) / 2;
                if (heap[current].compareTo(heap[parent]) < 0) {
                    swap(current, parent);
                    current = parent;
                } else break;
            }
        }

        // Bottom-up build
        public void buildHeapBottomUp(String[] words) {
            heap = words.clone();
            size = words.length;
            for (int i = size / 2 - 1; i >= 0; i--) {
                heapify(i);
            }
        }

        private void heapify(int i) {
            int smallest = i;
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < size && heap[left].compareTo(heap[smallest]) < 0) {
                smallest = left;
            }
            if (right < size && heap[right].compareTo(heap[smallest]) < 0) {
                smallest = right;
            }
            if (smallest != i) {
                swap(i, smallest);
                heapify(smallest);
            }
        }

        public String extractMin() {
            if (size == 0) return null;
            String min = heap[0];
            heap[0] = heap[size - 1];
            size--;
            heapify(0);
            return min;
        }

        public String[] heapSort() {
            String[] sorted = new String[size];
            for (int i = 0; i < sorted.length; i++) {
                sorted[i] = extractMin();
            }
            return sorted;
        }

        private void swap(int i, int j) {
            String temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;
        }
    }


    public static long timeHeapSortBottomUp(String[] words) {
        Heap h = new Heap(words.length);
        long start = System.nanoTime();
        h.buildHeapBottomUp(words);
        h.heapSort();
        return System.nanoTime() - start;
    }

    public static long timeHeapSortTopDown(String[] words) {
        Heap h = new Heap(words.length);
        long start = System.nanoTime();
        for (String w : words) {
            h.insert(w);
        }
        h.heapSort();
        return System.nanoTime() - start;
    }


    public static String[] readWordsFromFile(String filename) throws IOException {
        List<String> words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Clean word (keep apostrophes if desired)
                String[] parts = line.split("\\s+");
                for (String w : parts) {
                    w = w.replaceAll("[^a-zA-Z']", "").toLowerCase();
                    if (!w.isEmpty()) {
                        words.add(w);
                    }
                }
            }
        }
        return words.toArray(new String[0]);
    }


    public static void main(String[] args) throws IOException {
        // Test with small array
        String[] testWords = {
                "apple","banana","pear","grape","orange","melon","kiwi",
                "plum","cherry","date","fig","lime","lemon","apricot",
                "mango","papaya","berry","coconut","guava","nectarine"
        };

        long bottomUpTimeSmall = timeHeapSortBottomUp(testWords);
        long topDownTimeSmall = timeHeapSortTopDown(testWords);

        System.out.println("Small Test ");
        System.out.println("Bottom-up heap sort time: " + bottomUpTimeSmall + " ns");
        System.out.println("Top-down heap sort time: " + topDownTimeSmall + " ns");


        String[] ulyssesWords = readWordsFromFile("src/joyce1922_ulysses.text");

        long bottomUpTimeBig = timeHeapSortBottomUp(ulyssesWords);
        long topDownTimeBig = timeHeapSortTopDown(ulyssesWords);

        System.out.println("\nUlysses Dataset");
        System.out.println("Bottom-up heap sort time: " + bottomUpTimeBig + " ns");
        System.out.println("Top-down heap sort time: " + topDownTimeBig + " ns");


        Heap h = new Heap(ulyssesWords.length);
        h.buildHeapBottomUp(ulyssesWords);
        String[] sortedWords = h.heapSort();
        System.out.println("First 20 sorted words: " + Arrays.toString(Arrays.copyOfRange(sortedWords, 0, 20)));
    }
}
