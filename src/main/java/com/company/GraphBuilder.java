package com.company;

import static com.company.Main.linearize;

public class GraphBuilder {
    public static int[] build(int[] array, int size) {
        int[] newArray = new int[size * size];

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (array[linearize(y, x)] == -1)
                    markAsUnavailable(newArray, y, x, 3, size);
            }
        }

        for (int i = 0; i < size; i++)
            for (int j = 0; j < 3; j++) {
                newArray[linearize(j, i)] = -1;
                newArray[linearize(size - (1 + j), i)] = -1;
                newArray[linearize(i, j)] = -1;
                newArray[linearize(i, size - (1 + j))] = -1;
            }
        return newArray;
    }

    private static void markAsUnavailable(int[] newArray, int y, int x, int rad, int size) {
        int x1 = x - rad > 0 ? x - rad : 0;
        int y1 = y - rad > 0 ? y - rad : 0;
        int x2 = x + rad < size ? x + rad : size - 1;
        int y2 = y + rad < size ? y + rad : size - 1;

        for (int i = y1; i <= y2; i++)
            for (int j = x1; j <= x2; j++) {
                newArray[linearize(i, j)] = -1;
            }
    }
}
