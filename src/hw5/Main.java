package hw5;

public class Main {
    static final int SIZE = 10_000_000;
    static final int HALF = SIZE / 2;
    static float[] arr = new float[SIZE];

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < SIZE; i++) {
            arr[i] = 1;
        }

        long start = System.currentTimeMillis();

        for (int i = 0; i < SIZE; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        System.out.println(System.currentTimeMillis()-start);


        start = System.currentTimeMillis();

        float[] a1 = new float[HALF];
        float[] a2 = new float[HALF];

        System.arraycopy(arr, 0, a1, 0, HALF);
        System.arraycopy(arr, HALF, a2, 0, HALF);

        Thread t1 = new Thread (() -> {
            for (int i = 0; i < a1.length; i++) {
                a1[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });

        Thread t2 = new Thread (() -> {
            for (int i = 0; i < a2.length; i++) {
                a2[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(a1, 0, arr, 0, HALF);
        System.arraycopy(a2, 0, arr, HALF, HALF);

        System.out.println(System.currentTimeMillis() - start);
    }
}
