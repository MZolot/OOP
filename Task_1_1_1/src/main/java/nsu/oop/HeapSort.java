package nsu.oop;

public class HeapSort {

    private final int[] heap;
    private final int k;

    public HeapSort(int n) {
        this.heap = new int[n];
        k = n;
    }

    private void swap(int n, int m) {
        heap[n] += heap[m];
        heap[m] = heap[n] - heap[m];
        heap[n] -= heap[m];
    }

    private void siftUp(int k) {
        int k0 = (k - 1) / 2;
        if (k == 0 || heap[(k0)] < heap[k] ) {
            return;
        }
        swap(k, k0);
        siftUp(k0);
    }

    private void siftDown(int k, int x) {
        if (2 * x + 1 > k) {
            return;
        }

        int x1 = 2 * x + 2;
        if ((x1 > k) || (heap[x1 - 1] < heap[x1])) {
            x1--;
        }

        if (heap[x] > heap[x1]) {
            swap(x, x1);
        }

        siftDown(k, x1);
    }

    public void sort (int[] arr) {
        for (int i = 0; i < k; i++) {
            heap[i] = arr[i];
            siftUp(i);
        }
        for (int i = 0; i < k; i++) {
            arr[i] = heap[0];
            heap[0] = heap[k - 1 - i];
            siftDown(k - 2 - i, 0);
        }
    }
}
