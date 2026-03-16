import java.io.*;
import java.util.*;

public class tryHeapSort {

    // --- Heap implementation for Strings ---
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
    }
    }