package com.company;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import static com.company.Main.delinearize;
import static com.company.Main.offsets;

public class AStar {
    public int[] array;
    private int start, goal;
    private Set<Integer> closedSet;
    private PriorityQueue<Integer> openSet;
    private double[] gScore, fScore;
    private int[] cameFrom;
    private int size;


    public AStar(int[] array, int size, int start, int goal) {
        this.array = array;
        this.start = start;
        this.goal = goal;
        this.size = size;
        closedSet = new HashSet<>();
        openSet = new PriorityQueue<>((o1, o2) -> {
            if (fScore[o1] - fScore[o2] > 0)
                return 1;
            else if (fScore[o1] - fScore[o2] < 0)
                return -1;
            else return 0;
        });
        openSet.add(start);
        cameFrom = new int[size * size];
        gScore = new double[size * size];
        fScore = new double[size * size];
        for (int i = 0; i < size * size; i++) {
            gScore[i] = Integer.MAX_VALUE;
            fScore[i] = Integer.MAX_VALUE;
        }
        gScore[this.start] = 0;
        fScore[this.start] = heuristicCost(this.start, this.goal);
    }

    public static double heuristicCost(int start, int goal) {
        int h = delinearize(start)[0] - delinearize(goal)[0],
                w = delinearize(start)[1] - delinearize(goal)[1];
        return Math.sqrt(h * h + w * w);
    }

    public ArrayList<Integer> findRoute() {
        while (!openSet.isEmpty()) {
            int current = openSet.poll();
            if (current == goal)
                return reconstructPath();
//            closedSet.add(current);
            for (int x : offsets) {
                int neighbor = x + current;
//                if (closedSet.contains(neighbor)  || array[current] == -1)
                if (array[current] == -1)
                    continue;
                double tentative_gScore = gScore[current] + heuristicCost(current, neighbor);
//                if (!openSet.contains(neighbor)) {
//                    openSet.add(neighbor);
//
//                   //fScore[neighbor] = Integer.MAX_VALUE;
//
//                }else
                if (tentative_gScore >= gScore[neighbor])
                    continue;
                gScore[neighbor] = tentative_gScore;
                fScore[neighbor] = tentative_gScore + heuristicCost(neighbor, goal);
                cameFrom[neighbor] = current;
                openSet.add(neighbor);
            }
        }
        return null;
    }


    private ArrayList<Integer> reconstructPath() {
        ArrayList<Integer> totalPath = new ArrayList<>();
        totalPath.add(goal);
        int current = goal;
        while (current != start) {
            current = cameFrom[current];
            totalPath.add(current);
        }
        return totalPath;
    }
}


