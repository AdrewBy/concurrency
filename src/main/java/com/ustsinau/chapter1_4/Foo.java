package com.ustsinau.chapter1_4;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

public class Foo {
    private CountDownLatch cdl1 = new CountDownLatch(1);
    private CountDownLatch cdl2 = new CountDownLatch(1);


    public static void main(String[] args) {


        Foo foo = new Foo();
        for (int i = 0; i < 100; i++) {
            CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
                foo.second(new MyThread("B"));
            });

            CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
                foo.first(new MyThread("A"));
            });

            CompletableFuture<Void> future3 = CompletableFuture.runAsync(() -> {
                foo.third(new MyThread("C"));
            });


            CompletableFuture.allOf(future1, future2, future3).join();
            System.out.println("");

            foo.cdl1 = new CountDownLatch(1);
            foo.cdl2 = new CountDownLatch(1);
        }
    }

    public void first(MyThread thread) {

        System.out.print("first");
        cdl1.countDown();
    }

    public void second(MyThread thread) {
        try {
            cdl1.await();
            System.out.print("second");
            cdl2.countDown();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void third(MyThread thread) {
        try {
            cdl2.await();
            System.out.print("third");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}
