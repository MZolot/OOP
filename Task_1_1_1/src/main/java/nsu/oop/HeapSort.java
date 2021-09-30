package nsu.oop;

public class HeapSort {

    private void swap (int[] arr, int n, int m) {
        arr[n] += arr[m];
        arr[m] = arr[n] - arr[m];
        arr[n] -= arr[m];
    }

    private void findMaxElem (int[] arr, int arrLen, int i) {
        int max = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        if ( l >= arrLen) {
            return;
        }
        if (arr[l] > arr[max]) {
            max = l;
        }
        if (r < arrLen && arr[r] > arr[max]) {
            max = r;
        }
        if (max != i) {
            swap(arr, i, max);
            findMaxElem(arr, arrLen, max);
        }
    }

    public void sort (int[] arr) {
        if (arr == null) {
            return;
        }
        int arrLen = arr.length;
        for (int i = arrLen / 2 - 1; i >= 0; i--) {
            findMaxElem(arr, arrLen, i);
        }
        for (int i = arrLen - 1; i > 0; i--) {
            swap(arr, 0, i);
            findMaxElem(arr, i, 0);
        }
    }

}
