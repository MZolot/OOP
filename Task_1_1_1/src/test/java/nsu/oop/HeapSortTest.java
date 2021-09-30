package nsu.oop;

import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HeapSortTest {

    HeapSort hs = new HeapSort();

    @Test
    public void testOrdered () {
        int[] arr = {1, 2, 3, 4, 5};
        int[] arrCorrect = Arrays.copyOf(arr, arr.length);
        Arrays.sort(arrCorrect);
        hs.sort(arr);
        Assertions.assertArrayEquals(arrCorrect, arr);
    }

    @Test
    public void testUnordered () {
        int[] arr = {1, 3, 5, 2, 4};
        int[] arrCorrect = Arrays.copyOf(arr, arr.length);
        Arrays.sort(arrCorrect);
        hs.sort(arr);
        Assertions.assertArrayEquals(arrCorrect, arr);
    }

    @Test
    public void testSameNumbers () {
        int[] arr = {1, 1, 2, 1, 2};
        int[] arrCorrect = Arrays.copyOf(arr, arr.length);
        Arrays.sort(arrCorrect);
        hs.sort(arr);
        Assertions.assertArrayEquals(arrCorrect, arr);
    }

    @Test
    public void testAllSameNumbers () {
        int[] arr = {1, 1, 1, 1, 1};
        int[] arrCorrect = Arrays.copyOf(arr, arr.length);
        hs.sort(arr);
        Assertions.assertArrayEquals(arrCorrect, arr);
    }

    @Test
    public void testNegativeNumbers () {
        int[] arr = {-1, -2, -3, -4, -5};
        int[] arrCorrect = Arrays.copyOf(arr, arr.length);
        Arrays.sort(arrCorrect);
        hs.sort(arr);
        Assertions.assertArrayEquals(arrCorrect, arr);
    }

    @Test
    public void testDifferentNumbers () {
        int[] arr = {-10, 0, -2, 1, 2};
        int[] arrCorrect = Arrays.copyOf(arr, arr.length);
        Arrays.sort(arrCorrect);
        hs.sort(arr);
        Assertions.assertArrayEquals(arrCorrect, arr);
    }

    @Test
    public void testEmptyArr () {
        int[] arr = {};
        int[] emptyArr = {};
        hs.sort(arr);
        Assertions.assertArrayEquals(emptyArr, arr);
    }

    @Test
    public void testOnlyNumber () {
        int[] arr = {1};
        int[] correctArr = {1};
        hs.sort(arr);
        Assertions.assertArrayEquals(correctArr, arr);
    }

    @Test
    public void testNull () {
        hs.sort(null);
    }
}