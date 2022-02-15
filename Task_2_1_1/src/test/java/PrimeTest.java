import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PrimeTest {

    public static void main(String[] args) throws InterruptedException {
        Prime prime = new Prime();
        Integer[] myArr = {6997901, 6997927, 6997937, 6997967, 6998009, 6998029, 6998039, 6998051, 6998053, 2};
        System.out.println(prime.hasPrimesConcurrent(myArr));
        System.out.println(prime.hasPrimesParallelThreads(myArr, 5));
        System.out.println(prime.hasPrimesParallelThreads(myArr, 2));
        System.out.println(prime.hasPrimesParallelThreads(myArr, 3));
        System.out.println(prime.hasPrimesParallelThreads(myArr, 5));
        System.out.println(prime.hasPrimesParallelThreads(myArr, 9));
        System.out.println(prime.hasPrimesParallelStream(myArr));
    }

}