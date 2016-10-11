package com.company;

import java.util.*;

import static com.company.Main.offsets;

public class Lee {
    int[] array;
    int[] newArray;
    int size;
    int start;
    int goal;

    public Lee(int[] array, int size, int start, int goal) {
        this.array = array;
        this.newArray = new int[size * size];
        Arrays.fill(newArray, -1);
        this.size = size;
        this.start = start;
        this.goal = goal;
    }

    public ArrayList<Integer> findRoute() {
        PriorityQueue<Integer> queue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return newArray[o1] - newArray[o2];
            }
        });
        queue.add(start);
        newArray[start] = 0;
        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (current == goal)
                return reconstructPath();
            for (int x : offsets) {
                int neighbor = current + x;
                if (array[neighbor] == 0 && newArray[neighbor] == -1) {
                    newArray[neighbor] = newArray[current] + 1;
                    queue.add(neighbor);
                }
            }
        }
        return null;
    }

    private ArrayList<Integer> reconstructPath() {
        ArrayList<Integer> totalPath = new ArrayList<>();
        totalPath.add(goal);
        int current = goal;
        int count = newArray[goal];
        while (current != start) {
            for (int x : offsets) {
                int neighbor = current + x;
                if (neighbor > 0 && neighbor < size * size && newArray[neighbor] == count - 1) {
                    current = neighbor;
                    totalPath.add(neighbor);
                    count--;
                    break;
                }
            }
        }
        return totalPath;
    }
}
