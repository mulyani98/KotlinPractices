package com.mououoo.featurecalculator;

import org.junit.Test;

public class CoroutineTestJava {

    public void threadForJava() throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println(
                    "Running on thread: " + Thread.currentThread().getName()
            );
        });

        thread.start();
        thread.join();
    }

    @Test
    public void testThread() throws InterruptedException {
        threadForJava();
    }


}