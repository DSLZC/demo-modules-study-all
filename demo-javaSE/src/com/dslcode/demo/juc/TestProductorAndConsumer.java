package com.dslcode.demo.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestProductorAndConsumer {
    public static void main(String[] args) {
        Clerk clerk=new Clerk();

        Productor pro=new Productor(clerk);
        Consumer cus=new Consumer(clerk);

        new Thread(pro,"生产者 A").start();
        new Thread(cus,"消费者 B").start();
        new Thread(pro,"生产者 C").start();
        new Thread(cus,"消费者 D").start();
    }
}

class Productor implements Runnable{
    private Clerk clerk;
    public Productor(Clerk clerk){
        this.clerk=clerk;
    }
    @Override
    public void run() {
        for(int i=0;i<10;i++){
            try{
                Thread.sleep(100);
            }catch(InterruptedException e){}
            clerk.get();
        }
    }
}

class Consumer implements Runnable{
    private Clerk clerk;
    public Consumer(Clerk clerk){
        this.clerk=clerk;
    }
    @Override
    public void run() {
        for(int i=0;i<10;i++){
            clerk.sale();
        }
    }
}

class Clerk{
    private int product=0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    //进货
    public void get(){
        lock.lock();
        try{
            while(product >= 1) {
                System.out.println("产品已满！");
                try {
                    condition.await();
                } catch (InterruptedException e) {}
            }
            System.out.println(Thread.currentThread().getName()
                    + " : " + ++product);
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }

    //卖货
    public void sale(){
        lock.lock();
        try {
            while (product<=0){
                System.out.println("缺货！");
                try {
                    condition.await();
                } catch (InterruptedException e) {}
            }
            System.out.println(Thread.currentThread().getName()
                    + " : " + --product);
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }
}