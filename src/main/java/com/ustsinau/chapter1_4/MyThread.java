package com.ustsinau.chapter1_4;

public class MyThread extends Thread {

    String name;

    public MyThread(String name) {
        this.name = name;

        //      new Thread(this).start();
    }

    public void run() {
    }
}
