package com.epam.entity;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BusPark {

    private final static int POOL_SIZE = 4;
    private Semaphore semaphore = new Semaphore(POOL_SIZE);
    private Queue<Bus> buses = new LinkedList<>();
    private static BusPark busPark;
    private final static Lock LOCK = new ReentrantLock();

    private BusPark() {
    }

    public Queue<Bus> getBuses() {
        return buses;
    }

    public static BusPark getInstance() {
        if (busPark == null){
            LOCK.tryLock();
            try {
                if (busPark == null){
                    busPark = new BusPark();
                }
            } finally {
                LOCK.unlock();
            }
        }
        return busPark;
    }

    public Bus rideBus(){
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return buses.poll();
    }

    public void returnBus(Bus bus){
        buses.add(bus);
        semaphore.release();
    }

    public void fillBuses(List<Bus> newBuses) {
        buses.addAll(newBuses);
    }
}
