package com.epam.entity;

import com.epam.enums.BusStopName;
import com.epam.generator.PassengerListGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class BusStop {

    private List<Passenger> passengers;
    private Position position;
    private BusStopName name;
    private final int maxBusesAmount = 2;
    private Semaphore semaphore = new Semaphore(maxBusesAmount,true);
    private ArrayList<Bus> busArrayList = new ArrayList<>(2);

    public BusStop(Position position, BusStopName busStopName) {
        passengers = PassengerListGenerator.generatePassengers();
        this.position = position;
        this.name = busStopName;
    }

    /*package-private*/ Semaphore getSemaphore() {
        return semaphore;
    }

    /*package-private*/ ArrayList<Bus> getBusArrayList() {
        return busArrayList;
    }

    /*package-private*/ Position getPosition() {
        return position;
    }

    /*package-private*/ List<Passenger> getPassengers() {
        return passengers;
    }

    /*package-private*/ BusStopName getName() {
        return name;
    }

    @Override
    public String toString() {
        return "BusStop{" +
                ", position=" + position +
                ", name='" + name + '\'' +
                '}';
    }
}
