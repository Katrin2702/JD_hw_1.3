import java.util.Random;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        int sum1Case1 = 0;
        long sum2Case1 = 0;
        long startTimeSum1Case1 = 0;
        long endTimeSum1Case1 = 0;
        long startTimeSum2Case1 = 0;
        long endTimeSum2Case1 = 0;
        int [] array1 = createArray (20);
        int [] array2 = createArray (100000000);

        //решение в одном потоке
        Callable<Integer> callable1 = new MyCallable(array1);
        Callable<Integer> callable2 = new MyCallable(array2);

        ExecutorService threadPool = Executors.newFixedThreadPool(1);
        try {
            startTimeSum1Case1 = System.currentTimeMillis();
            Future<Integer> resultOfTask1 = threadPool.submit(callable1);
            sum1Case1 = resultOfTask1.get();
            endTimeSum1Case1 = System.currentTimeMillis();
            startTimeSum2Case1 = System.currentTimeMillis();
            Future<Integer> resultOfTask2 = threadPool.submit(callable2);
            sum2Case1 = resultOfTask2.get();
            endTimeSum2Case1 = System.currentTimeMillis();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.out.printf("Однопоточный метод:\nсумма элементов массива 1 = %s; среднее значение = %s; " +
                        "время на операцию, мс = %s\nсумма элементов массива 2 = %s; среднее значение = %s;" +
                        "время на операцию, мс = %s\n", sum1Case1, sum1Case1/array1.length,
                endTimeSum1Case1-startTimeSum1Case1,  sum2Case1, sum2Case1/array2.length,
                endTimeSum2Case1-startTimeSum2Case1);


        // многопоточное решение с использованием ForkJoinPoll
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        long startTimeSum1Case2 = System.currentTimeMillis();
        ArraySumTask task1 = new ArraySumTask(array1);
        long sum1Case2 = forkJoinPool.invoke(task1);
        long endTimeSum1Case2 = System.currentTimeMillis();
        long startTimeSum2Case2 = System.currentTimeMillis();
        ArraySumTask task2 = new ArraySumTask(array2);
        long sum2Case2 = forkJoinPool.invoke(task2);
        long endTimeSum2Case2 = System.currentTimeMillis();

        System.out.printf("Однопоточный метод:\nсумма элементов массива 1 = %s; среднее значение = %s; " +
                        "время на операцию, мс = %s\nсумма элементов массива 2 = %s; среднее значение = %s;" +
                        "время на операцию, мс = %s\n", sum1Case2, sum1Case2/array1.length,
                endTimeSum1Case2-startTimeSum1Case2, sum2Case2, sum2Case2/array2.length,
                endTimeSum2Case2-startTimeSum2Case2);

    }

    public static int[] createArray(int size) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array [i] = random.nextInt(1000);
        }
        return  array;
    }

}
