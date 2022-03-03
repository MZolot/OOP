import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class PrimeTest {

    @Test
    void testFromTaskSingleThread() {
        Prime prime = new Prime();
        Integer[] arr = {6997901, 6997927, 6997937, 6997967, 6998009, 6998029, 6998039, 6998051, 6998053};
        Assertions.assertFalse(prime.hasPrimesSingleThread(arr));
    }

    @Test
    void testFromTaskParallelThreads() throws InterruptedException {
        Prime prime = new Prime();
        Integer[] arr = {6997901, 6997927, 6997937, 6997967, 6998009, 6998029, 6998039, 6998051, 6998053};
        Assertions.assertFalse(prime.hasPrimesParallelThreads(arr, 2));
    }

    @Test
    void testFromTaskParallelStream() {
        Prime prime = new Prime();
        Integer[] arr = {6997901, 6997927, 6997937, 6997967, 6998009, 6998029, 6998039, 6998051, 6998053};
        Assertions.assertFalse(prime.hasPrimesParallelStream(arr));
    }

    @Test
    void hasPrimesSingleThread() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("test_numbers.txt"));
        String[] strArr = reader.readLine().split(", ");
        Integer[] arr = new Integer[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            arr[i] = Integer.parseInt(strArr[i]);
        }
        Prime prime = new Prime();
        Assertions.assertFalse(prime.hasPrimesSingleThread(arr));
    }

    @Test
    void hasPrimes2ParallelThreads() throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new FileReader("test_numbers.txt"));
        String[] strArr = reader.readLine().split(", ");
        Integer[] arr = new Integer[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            arr[i] = Integer.parseInt(strArr[i]);
        }
        Prime prime = new Prime();
        Assertions.assertFalse(prime.hasPrimesParallelThreads(arr, 2));
    }

    @Test
    void hasPrimes10ParallelThreads() throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new FileReader("test_numbers.txt"));
        String[] strArr = reader.readLine().split(", ");
        Integer[] arr = new Integer[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            arr[i] = Integer.parseInt(strArr[i]);
        }
        Prime prime = new Prime();
        Assertions.assertFalse(prime.hasPrimesParallelThreads(arr, 10));
    }

    @Test
    void hasPrimesParallelStream() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("test_numbers.txt"));
        String[] strArr = reader.readLine().split(", ");
        Integer[] arr = new Integer[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            arr[i] = Integer.parseInt(strArr[i]);
        }
        Prime prime = new Prime();
        Assertions.assertFalse(prime.hasPrimesParallelStream(arr));
    }
}