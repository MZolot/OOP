import nsu.oop.HeapSort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HeapTest {

    @Test
    public void TestOrdered () {
        int n = 5;
        HeapSort hs = new HeapSort(n);
        int[] arr1 = {1, 2, 3, 4, 5};
        int[] arrCorrect = {1, 2, 3, 4, 5};
        hs.sort(arr1);
        Assertions.assertArrayEquals(arrCorrect, arr1);
    }

    @Test
    public void TestUnordered () {
        int n = 5;
        HeapSort hs = new HeapSort(n);
        int[] arr = {1, 3, 5, 2, 4};
        int[] arrCorrect = {1, 2, 3, 4, 5};
        hs.sort(arr);
        Assertions.assertArrayEquals(arrCorrect, arr);
    }

    @Test
    public void TestSameNumbers () {
        int n = 5;
        HeapSort hs = new HeapSort(n);
        int[] arr3 = {1, 1, 2, 1, 2};
        int[] arrCorrect3 = {1, 1, 1, 2, 2};
        hs.sort(arr3);
        Assertions.assertArrayEquals(arrCorrect3, arr3);
    }

    @Test
    public void TestAllSameNumbers () {
        int n = 5;
        HeapSort hs = new HeapSort(n);
        int[] arr4 = {1, 1, 1, 1, 1};
        int[] arrCorrect4 = {1, 1, 1, 1, 1};
        hs.sort(arr4);
        Assertions.assertArrayEquals(arrCorrect4, arr4);
    }

    @Test
    public void TestNegativeNumbers () {
        int n = 5;
        HeapSort hs = new HeapSort(n);
        int[] arr5 = {-1, -2, -3, -4, -5};
        int[] arrCorrect5 = {-5, -4, -3, -2, -1};
        hs.sort(arr5);
        Assertions.assertArrayEquals(arrCorrect5, arr5);
    }

    @Test
    public void TestDifferentNumbers () {
        int n = 5;
        HeapSort hs = new HeapSort(n);
        int[] arr6 = {-10, 0, -2, 1, 2};
        int[] arrCorrect6 = {-10, -2, 0, 1, 2};
        hs.sort(arr6);
        Assertions.assertArrayEquals(arrCorrect6, arr6);
    }

    @Test
    public void TestEmptyArr () {
        HeapSort hs0 = new HeapSort(0);
        int[] arr0 = {};
        int[] emptyArr = {};
        hs0.sort(arr0);
        Assertions.assertArrayEquals(emptyArr, arr0);
    }

    @Test
    public void TestOnlyNumber () {
        HeapSort hs1 = new HeapSort(1);
        int[] arr1 = {1};
        int[] correctArr1 = {1};
        hs1.sort(arr1);
        Assertions.assertArrayEquals(correctArr1, arr1);
    }

}
