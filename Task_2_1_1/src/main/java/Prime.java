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

    public boolean hasPrimesConcurrent(int[] arr) {
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

    public boolean hasPrimesParallelThreads(int[] arr, int threadsAmount) {
        long time0 = System.currentTimeMillis();
        AtomicBoolean result = new AtomicBoolean();
        int len = arr.length;
        for (int i = 0; i < threadsAmount; i++) {
            int threadNumber = i;
            Runnable runnable = () -> {
                boolean res = false;
                for (int j = threadNumber; j < len; j = j + threadsAmount) {
                    if(res) {
                        break;
                    }
                    res = isNotPrime(arr[j]);
                }
                result.set(result.get() || res);
            };
            Thread thread = new Thread(runnable);
            thread.start();
        }

        System.out.println((System.currentTimeMillis() - time0) + "ms");
        return result.get();
    }

}
