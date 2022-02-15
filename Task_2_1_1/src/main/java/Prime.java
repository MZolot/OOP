import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Prime {

    private boolean isNotPrime(int i) {
        if (i < 2) {
            return true;
        }
        for (int j = 2; j < Math.sqrt(i) + 2; j++) {
            if (i % j == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean hasPrimesConcurrent(Integer[] arr) {
        System.out.print("Concurrent --  ");
        long time0 = System.currentTimeMillis();
        for (int i : arr) {
            if (isNotPrime(i)) {
                System.out.println((System.currentTimeMillis() - time0) + "ms");
                return true;
            }
        }
        System.out.println((System.currentTimeMillis() - time0) + "ms");
        return false;
    }

    public boolean hasPrimesParallelThreads(Integer[] arr, int threadsAmount) throws InterruptedException {
        System.out.print(threadsAmount + " threads  --  ");
        long time0 = System.currentTimeMillis();
        AtomicBoolean result = new AtomicBoolean();
        int len = arr.length;

        List<Thread> threads = new ArrayList<>(threadsAmount);
        for (int i = 0; i < threadsAmount; i++) {
            int threadNumber = i;
            Runnable runnable = () -> {
                boolean res;
                for (int j = threadNumber; j < len; j = j + threadsAmount) {
                    res = isNotPrime(arr[j]);
                    if (res || result.get()) {
                        result.set(true);
                        break;
                    }
                }
            };
            Thread thread = new Thread(runnable);
            threads.add(thread);
            thread.start();
        }
        for (Thread t : threads) {
            t.join();
        }

        System.out.println((System.currentTimeMillis() - time0) + "ms");
        return result.get();
    }

    public boolean hasPrimesParallelStream(Integer[] arr) {
        Collection<Integer> collection = Arrays.asList(arr);
        System.out.print("Stream     --  ");
        long time0 = System.currentTimeMillis();
        boolean res = collection.parallelStream().anyMatch(this::isNotPrime);
        System.out.println((System.currentTimeMillis() - time0) + "ms");
        return res;
    }

}
