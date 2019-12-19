package com.epam.entity;

import com.epam.enums.BusStopName;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bus implements Runnable{

    private static final Logger LOGGER = Logger.getLogger(Bus.class);
    private static final int MAGIC_NUMBER_FOR_DEBUG = 20;
    private List<BusStop> route;
    private ArrayList<Passenger> passengers = new ArrayList<>(20);
    private static final Lock LOCK = new ReentrantLock();
    private double speed;

    public Bus(List<BusStop> busStops, double speed) {
        this.route = busStops;
        this.speed = speed;
    }

    @Override
    public void run() {

        String currentThreadName = Thread.currentThread().getName();

        for (int i = 0; i < route.size() - 1; i++) {
            BusStop currentBusStop = route.get(i + 1);
            List<Passenger> passengersOnStation = currentBusStop.getPassengers();

            LOGGER.info(currentThreadName + " Number of passengers: " + passengersOnStation.size());
            Semaphore semaphore = currentBusStop.getSemaphore();

            try {
                LOGGER.info(currentThreadName + " I am going to the station " + currentBusStop.getName());

                double timeNecessaryForRide = getDistance(route, i, i + 1) / speed;
                TimeUnit.SECONDS.sleep((long)(timeNecessaryForRide * MAGIC_NUMBER_FOR_DEBUG)/1000);

                LOGGER.info(currentThreadName + " I came to station " + currentBusStop.getName());

                semaphore.acquire();

                ArrayList<Bus> busArrayList = currentBusStop.getBusArrayList();
                LOCK.lock();
                if (busArrayList.size() < 2) {
                    busArrayList.add(this);
                }
                LOCK.unlock();

                LOGGER.info(currentThreadName + " i am waiting for passengers");

                TimeUnit.SECONDS.sleep(5);
                LOCK.lock();
                for (int k = 0; k < passengersOnStation.size(); k++) {
                    Passenger passenger = passengersOnStation.get(k);
                    for (int j = i + 1; j < route.size() - 1; j++) {
                        String destination = passenger.getDestinationGoal().toUpperCase();
                        if (BusStopName.valueOf(destination).equals(route.get(j).getName()) && passengers.size() < 20) {
                            passengers.add(passenger);
                            passengersOnStation.remove(passenger);

                            LOGGER.info(currentThreadName + " i just added " + passenger);
                        }
                    }
                }
                LOGGER.info(currentThreadName + " Number of passengers left: " + passengersOnStation.size());

                LOCK.unlock();
                TimeUnit.SECONDS.sleep(3);
                busArrayList.remove(this);
            } catch (InterruptedException e) {
                LOGGER.error("Someone interrupted me ", e);
            }
            semaphore.release();
        }
        LOGGER.info(currentThreadName + " I end my work ");
    }

    private static double getDistance(List<BusStop> route, int prev, int next) {

        BusStop prevBusStop = route.get(prev);
        BusStop nextBusStop = route.get(next);
        Position positionPrev = prevBusStop.getPosition();
        Position positionNext = nextBusStop.getPosition();

        int prevX = positionPrev.getX();
        int prevY = positionPrev.getY();

        int nextX = positionNext.getX();
        int nextY = positionNext.getY();
        return Math.hypot(prevX - nextX, prevY - nextY);
    }
}
