package uhk.fim.model;

import uhk.fim.Main;

public class MainThreads2 {

    static int counter = 0;
    public static void main(String[] args) {
        CounterThread thread = new CounterThread();
        CounterThread thread2 = new CounterThread();
        thread.start();
        thread2.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(counter);


    }
}

class CounterThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            synchronized (MainThreads2.class) {
                MainThreads2.counter++;
            }

        }
    }
}