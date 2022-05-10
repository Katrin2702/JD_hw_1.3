import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class ArraySumTask extends RecursiveTask<Long> {
    private int[] array;

    public ArraySumTask(int[] array) {
        this.array = array;
    }

    @Override
    protected Long compute() {
        long sum = 0;
        if (array.length <= 2) {
            for (int i = 0; i < array.length; i++) {
                sum = sum + array[i];
            }
            return sum;
        }
        ArraySumTask firstHalfArray = new ArraySumTask(Arrays.copyOfRange(array, 0,
                array.length / 2));
        ArraySumTask secondHalfArray = new ArraySumTask(Arrays.copyOfRange(array,
                array.length / 2, array.length));
        firstHalfArray.invoke();
        secondHalfArray.invoke();
        return firstHalfArray.join() + secondHalfArray.join();
    }
}







