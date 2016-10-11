package com.company;

import java.util.ArrayList;

import static com.company.Main.linearize;

public class GraphBuilder {


    public static int[] build(int[] array, int size) {
        int[] newArray = new int[size*size];

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (array[linearize(y,x)] == -1)
                    markAsAnavailble( y, x, newArray, size);
            }
        }
        for (int i = 0; i < size; i++) {
            newArray[linearize(0,i)] = -1;
            newArray[linearize(1,i)] = -1;
            newArray[linearize(size-1,i)] = -1;
            newArray[linearize(size-2,i)] = -1;
            newArray[linearize(i,0)] = -1;
            newArray[linearize(i,1)] = -1;
            newArray[linearize(i,size-1)] = -1;
            newArray[linearize(i,size-2)] = -1;
        }
        return newArray;
    }


    private static void markAsAnavailble( int y, int x, int[] newArray, int size) {
        int x1 = x - 2 > 0 ? x - 2 : 0;
        int y1 = y - 2 > 0 ? y - 2 : 0;
        int x2 = x + 2 < size ? x + 2 : 0;
        int y2 = y + 2 < size ? y + 2 : 0;

        for (int i = y1; i <= y2; i++)
            for (int j = x1; j <= x2; j++) {
                newArray[linearize(i,j)] = -1;
            }
    }
}
