import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

class PrimeTest {

    @Test
    void testFromTaskSingleThread() {
        Prime prime = new Prime();
        Integer[] arr = {6997901, 6997927, 6997937, 6997967, 6998009, 6998029, 6998039, 6998051, 6998053};
        Assertions.assertFalse(prime.hasPrimesSingleThread(arr));
    }

    @Test
    void testFromTaskParallelThreads() throws InterruptedException, ExecutionException {
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
        String[] strArr = reader.readLine().split(" ");
        Integer[] arr = new Integer[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            arr[i] = Integer.parseInt(strArr[i]);
        }
        Prime prime = new Prime();
        Assertions.assertFalse(prime.hasPrimesSingleThread(arr));
    }

    @Test
    void hasPrimes2ParallelThreads() throws IOException, InterruptedException, ExecutionException {
        BufferedReader reader = new BufferedReader(new FileReader("test_numbers.txt"));
        String[] strArr = reader.readLine().split(" ");
        Integer[] arr = new Integer[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            arr[i] = Integer.parseInt(strArr[i]);
        }
        Prime prime = new Prime();
        Assertions.assertFalse(prime.hasPrimesParallelThreads(arr, 2));
    }

    @Test
    void hasPrimes8ParallelThreads() throws IOException, InterruptedException, ExecutionException {
        BufferedReader reader = new BufferedReader(new FileReader("test_numbers.txt"));
        String[] strArr = reader.readLine().split(" ");
        Integer[] arr = new Integer[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            arr[i] = Integer.parseInt(strArr[i]);
        }
        Prime prime = new Prime();
        Assertions.assertFalse(prime.hasPrimesParallelThreads(arr, 8));
    }

    @Test
    void hasPrimesParallelStream() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("test_numbers.txt"));
        String[] strArr = reader.readLine().split(" ");
        Integer[] arr = new Integer[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            arr[i] = Integer.parseInt(strArr[i]);
        }
        Prime prime = new Prime();
        Assertions.assertFalse(prime.hasPrimesParallelStream(arr));
    }
}