package com.epam;

import com.epam.entity.Bus;
import com.epam.entity.BusPark;
import com.epam.reader.BusReader;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    private final static ExecutorService SERVICE = Executors.newFixedThreadPool(4);
    private final static String PATH = "./src/main/resources/buses.txt";
    private static List<Future> futures = new ArrayList<>();
    private final static BusPark BUS_PARK = BusPark.getInstance();

    public static void main(String[] args) {
        BusReader reader = new BusReader();
        BUS_PARK.fillBuses(reader.readBuses(PATH));
        Queue<Bus> buses = new LinkedList<>();

        int busParkSize = BUS_PARK.getBuses().size();
        for(int i = 0; i < busParkSize; i++){
            Bus bus = BUS_PARK.rideBus();
            buses.add(bus);
            futures.add(SERVICE.submit(bus));
        }

        for (Future future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        for (Bus bus : buses) {
            BUS_PARK.returnBus(bus);
        }

        SERVICE.shutdown();
    }
}
