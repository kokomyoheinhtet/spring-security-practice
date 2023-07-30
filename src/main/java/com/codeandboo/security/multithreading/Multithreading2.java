package com.codeandboo.security.multithreading;

import java.util.ArrayList;
import java.util.List;

class Processor {

    private final int LOWER_LIMIT = 0;
    private final int UPPER_LIMIT = 5;
    private List<Integer> list = new ArrayList<>();
    private Object lock = new Object();
    private int value = 0;

    public void producer() throws InterruptedException {
        synchronized (lock) {
            while (true) {
                if (list.size() == UPPER_LIMIT) {
                    printThreadName();
                    System.out.println("Waiting for removing...");
                    lock.wait();
                } else {
                    printThreadName();
                    System.out.println("Adding: " + value);
                    list.add(value);
                    value++;
                    lock.notify();
                }
                Thread.sleep(1000);
            }
        }
    }

    public void consumer() throws InterruptedException {
        synchronized (lock) {
            while (true) {
                if (list.size() == LOWER_LIMIT) {
                    printThreadName();
                    System.out.println("Waiting for adding...");
                    value = 0;
                    lock.wait();
                } else {
                    printThreadName();
                    System.out.println("Removing: " + list.remove(list.size() - 1));
                    lock.notify();
                }
                Thread.sleep(1000);
            }
        }
    }

    public void printThreadName() {
        System.out.print(String.format("%s is ", Thread.currentThread().getName()));
    }
}

public class Multithreading2 {

    public static void main(String[] args) {

        Processor processor = new Processor();

        Thread t1 = new Thread(() -> {
            try {
                processor.producer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                processor.consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();

    }
}
