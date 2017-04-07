package com.dslcode.demo.juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by dongsilin on 2017/4/6.
 */
public class TestAtomicDemo {

    public static void main(String[] args) {
        AtomicDemo ad = new AtomicDemo();

        for (int i = 0; i < 10; i++) {
            new Thread(ad).start();
        }
    }

}

class AtomicDemo implements Runnable{

//  private volatile int serialNumber = 0;

    private AtomicInteger serialNumber = new AtomicInteger(0);

    @Override
    public void run() {

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }

        System.out.println(getSerialNumber());
    }

    public int getSerialNumber(){
        return serialNumber.getAndIncrement();//i++ 实际是int temp=i;i=i+1;i=temp; 需要原子性操作
    }
}
