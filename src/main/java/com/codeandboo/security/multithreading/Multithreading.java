package com.codeandboo.security.multithreading;

public class Multithreading {
    public static void main(String[] args) {
        Process process = new Process();
        Thread pThread = new Thread(() -> {
            try {
                process.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread cThread = new Thread(() -> {
            try {
                process.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        pThread.start();
        cThread.start();

    }
}

class Process {
    public void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("Running producing...");
            wait();
            System.out.println("Again in producing...");
        }
    }

    public void consume() throws InterruptedException {
        Thread.sleep(1000);
        synchronized (this) {
            System.out.println("Consuming...");
            notify();
            System.out.println("notified...");
//            Thread.sleep(5000);
        }
    }
}