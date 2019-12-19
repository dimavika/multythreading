package com.epam.generator;

import com.epam.entity.Passenger;
import com.epam.enums.BusStopName;
import org.ajbrown.namemachine.NameGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PassengerListGenerator {

    public static List<Passenger> generatePassengers() {

        List<Passenger> list = new ArrayList<>();
        Random random = new Random();
        int amount = random.nextInt(30) + 5;
        NameGenerator generator = new NameGenerator();
        for (int i = 0; i < amount; i++) {
            int randomInt = random.nextInt(4);
            String direction = null;
            switch (randomInt) {
                case 0:
                    direction = BusStopName.FIRST_STOP.getName();
                    break;
                case 1:
                    direction = BusStopName.SECOND_STOP.getName();
                    break;
                case 2:
                    direction = BusStopName.THIRD_STOP.getName();
                    break;
                case 3:
                    direction = BusStopName.FOURTH_STOP.getName();
                    break;
            }
            String firstName = generator.generateName().getFirstName();
            Passenger passenger = new Passenger(firstName, direction);
            list.add(passenger);
        }
        return list;
    }
}
