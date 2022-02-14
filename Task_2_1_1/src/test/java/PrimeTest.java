import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PrimeTest {

    public static void main(String[] args) {
        Prime prime = new Prime();
        int[] myArr = {6997901, 6997927, 6997937, 6997967, 6998009, 6998029, 6998039, 6998051, 6998053};
        System.out.println(prime.hasPrimesConcurrent(myArr));
        System.out.println(prime.hasPrimesParallelThreads(myArr, 9));
    }

}