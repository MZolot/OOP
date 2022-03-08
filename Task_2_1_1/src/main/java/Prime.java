import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
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

    public boolean hasPrimesSingleThread(Integer[] arr) {
        System.out.print("Single thread --  ");
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

    public boolean hasPrimesParallelThreads(Integer[] arr, int threadsAmount) throws InterruptedException, ExecutionException {
        System.out.print(threadsAmount + " threads     --  ");
        long time0 = System.currentTimeMillis();
        int len = arr.length;
        ExecutorService executorService = Executors.newFixedThreadPool(threadsAmount);
        List<Future<Boolean>> futureList = new ArrayList<>();
        for (int i = 0; i < threadsAmount; i++) {
            int threadNumber = i;
            Callable<Boolean> callable = () -> {
                for (int j = threadNumber; j < len; j = j + threadsAmount) {
                    if (isNotPrime(arr[j])) {
                        return true;
                    }
                }
                return false;
            };
            Future<Boolean> future = executorService.submit(callable);
            futureList.add(future);
        }
        for (Future<Boolean> future : futureList) {
            if (future.get()) {
                executorService.shutdown();
                System.out.println((System.currentTimeMillis() - time0) + "ms");
                return true;
            }
        }
        executorService.shutdown();
        System.out.println((System.currentTimeMillis() - time0) + "ms");
        return false;
    }

    public boolean hasPrimesParallelStream(Integer[] arr) {
        Collection<Integer> collection = Arrays.asList(arr);
        System.out.print("Stream        --  ");
        long time0 = System.currentTimeMillis();
        boolean res = collection.parallelStream().anyMatch(this::isNotPrime);
        System.out.println((System.currentTimeMillis() - time0) + "ms");
        return res;
    }

}
