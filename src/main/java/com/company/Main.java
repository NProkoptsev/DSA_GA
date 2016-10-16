package com.company;

import com.google.caliper.AfterExperiment;
import com.google.caliper.BeforeExperiment;
import com.google.caliper.api.Macrobenchmark;
import com.google.caliper.api.VmOptions;
import com.google.caliper.runner.CaliperMain;

import java.io.IOException;
import java.util.ArrayList;

@VmOptions("-XX:-TieredCompilation")
public class Main {
    public static int size = 480;
    public static int[] array;
    public static ArrayList<Integer> reds;
    public static int count = 1;
    public static int[] offsets = new int[]{-size, -1, 1, size, -size - 1, -size + 1, size - 1, size + 1};
    ArrayList<Integer> list1;

    public static void main(String[] args) throws IOException {
        CaliperMain.main(Main.class, args);
    }

    public static int linearize(int... p) {
        return p[0] * size + p[1];
    }

    public static int[] delinearize(int x) {
        return new int[]{x / size, x % size};
    }

//    public static Iterable<Integer> getAdjacentCells(int current, int size) {
//        LinkedList<Integer> offsets = new LinkedList<>();
//        if (current >= size) {
//            offsets.addFirst(-size);
//            if (current % size > 0)
//                offsets.addLast(-size - 1);
//            if (current % size < size - 1)
//                offsets.addLast(-size + 1);
//        }
//        if (current < size * (size - 1)) {
//            offsets.addFirst(size);
//            if (current % size > 0)
//                offsets.addLast(size - 1);
//            if (current % size < size - 1)
//                offsets.addLast(size + 1);
//        }
//        if (current % size > 0)
//            offsets.addFirst(-1);
//        if (current % size < size - 1)
//            offsets.addFirst(1);
//        return offsets;
//    }


    @BeforeExperiment
    public void setUp() throws IOException {

        reds = new ArrayList<>();
        array = GraphReader.seeBMPImage("Labirint.bmp", reds, 7);
        array = GraphBuilder.build(array, size);
    }

    @Macrobenchmark
    public void lee() {
        list1 = new Lee(array, size, reds.get(0), reds.get(1)).findRoute();
    }

    @Macrobenchmark
    public void astar() {
        list1 = new AStar(array, size, reds.get(0), reds.get(1)).findRoute();
    }

    @AfterExperiment()
    public void printResult() throws IOException {
        System.out.println(list1);
        System.out.println(getLength());
        GraphWriter.writeBMPImage("Labirint.bmp", list1, "output" + count++ + ".bmp");
    }

    public double getLength() {
        double sum = 0;
        int cur = list1.get(0);
        for (int i = 1; i < list1.size(); i++) {
            sum += AStar.heuristicCost(cur, list1.get(i));
            cur = list1.get(i);
        }
        return sum;

    }
}
