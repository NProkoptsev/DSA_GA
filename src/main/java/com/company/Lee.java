package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

import static com.company.Main.offsets1;
import static com.company.Main.offsets2;

public class Lee {
    int[] array;
    double[] newArray;
    int size;
    int start;
    int goal;

    public Lee(int[] array, int size, int start, int goal) {
        this.array = array;
        this.newArray = new double[size * size];
        Arrays.fill(newArray, -1);
        this.size = size;
        this.start = start;
        this.goal = goal;
    }

    public ArrayList<Integer> findRoute() {
        PriorityQueue<Integer> queue = new PriorityQueue<>((o1, o2) -> {
            if (newArray[o1] - newArray[o2] > 0)
                return 1;
            else if (newArray[o1] - newArray[o2] < 0)
                return -1;
            else return 0;
        });
        queue.add(start);
        newArray[start] = 0;
        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (current == goal)
                return reconstructPath();
            for (int x : offsets1) {
                int neighbor = current + x;
                if (array[neighbor] == 0 && newArray[neighbor] == -1) {
                    newArray[neighbor] = newArray[current] + 1;
                    queue.add(neighbor);
                }
            }
            for (int x : offsets2) {
                int neighbor = current + x;
                if (array[neighbor] == 0 && newArray[neighbor] == -1) {
                    newArray[neighbor] = newArray[current] + Math.sqrt(2);
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
        double count = newArray[goal];
        boolean flag = true;
        while (current != start) {
            for (int x : offsets1) {
                int neighbor = current + x;
                if (newArray[neighbor] == count - 1) {
                    current = neighbor;
                    totalPath.add(neighbor);
                    count--;
                    flag = false;
                    break;
                }
            }
            if (flag)
                for (int x : offsets2) {
                    int neighbor = current + x;
                    if (newArray[neighbor] == count - Math.sqrt(2)) {
                        current = neighbor;
                        totalPath.add(neighbor);
                        count -= Math.sqrt(2);
                        break;
                    }
                }
            flag = true;
        }
        return totalPath;
    }
}
