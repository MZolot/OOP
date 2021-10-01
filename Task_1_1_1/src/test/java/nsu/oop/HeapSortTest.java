package nsu.oop;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.provider.MethodSource;

class HeapSortTest {

    HeapSort hs = new HeapSort();

    static private ArrayList<int[]> arrays() {
        var arrList = new ArrayList<int[]>();

        arrList.add(new int[]{1, 2, 3, 4, 5});
        arrList.add(new int[]{1, 3, 5, 2, 4});
        arrList.add(new int[]{1, 1, 2, 1, 2});
        arrList.add(new int[]{1, 1, 1, 1, 1});
        arrList.add(new int[]{-1, -2, -3, -4, -5});
        arrList.add(new int[]{-10, 0, -2, 1, 2});
        arrList.add(new int[]{});
        arrList.add(new int[]{1});

        return arrList;
    }

    @ParameterizedTest
    @MethodSource("arrays")
    void testHeap(int[] arr) {
        int[] arrCorrect = Arrays.copyOf(arr, arr.length);
        Arrays.sort(arrCorrect);
        hs.sort(arr);
        Assertions.assertArrayEquals(arrCorrect, arr);
    }

    @Test
    public void testNull () {
        Assertions.assertThrows(IllegalArgumentException.class, ()-> hs.sort(null));
    }
}