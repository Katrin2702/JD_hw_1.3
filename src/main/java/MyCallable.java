import java.util.Arrays;
import java.util.concurrent.Callable;

class MyCallable implements Callable<Integer> {

    private int[] arr;

    public MyCallable(int[] arr) {
        this.arr = arr;
    }

    @Override

    public Integer call() {
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum = sum + arr[i];
        }
        return sum;
    }
}