package com.codeandboo.security.multithreading;

public class JavaSynchronization {
    public static int counter1 = 0;
    public static int counter2 = 0;
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static  void increment1() {
        synchronized (lock1) {
            counter1++;
        }
    }

    public static  void increment2() {
        synchronized (lock2) {
            counter2++;
        }
    }

    public static void process() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                increment1();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                increment2();
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

        System.out.println("Counter is: " + counter1);
        System.out.println("Counter is: " + counter2);
    }

    public static void main(String[] args) {
        process();
    }
}
