package com.epam.reader;

import com.epam.entity.Bus;
import com.epam.entity.BusStop;
import com.epam.entity.Position;
import com.epam.enums.BusStopName;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BusReader {

    public List<Bus> readBuses(String path) {
        Path path1 = Paths.get(path);
        List<String> data = null;
        try {
            data = Files.readAllLines(path1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BusStop busStopFirst = new BusStop(new Position(50, 50), BusStopName.SECOND_STOP);
        BusStop busStopSecond = new BusStop(new Position(1000, 1000), BusStopName.THIRD_STOP);
        BusStop busStopThird = new BusStop(new Position(-500, 300), BusStopName.FOURTH_STOP);
        BusStop busStopFourth = new BusStop(new Position(2000, 2000), BusStopName.FIRST_STOP);

        List<Bus> buses = new ArrayList<>();
        List<BusStop> route = new ArrayList<>();

        route.add(busStopFirst);
        route.add(busStopSecond);
        route.add(busStopThird);
        route.add(busStopFourth);

        if (data != null) {
            for (String line : data) {
                Bus bus = new Bus(route, Integer.parseInt(line));
                buses.add(bus);
            }
        }
        return buses;
    }
}