package org.example.trainfixer.train;

import org.example.trainfixer.list.LinkedList;

import java.util.Iterator;


public class Train implements Iterable<Car> {

    private final LinkedList<Car> cars = new LinkedList<>();

    public void addLast(Car car) {
        cars.addLast(car);
    }

    public int length() {
        return cars.size();
    }

    public boolean isEmpty() {
        return cars.isEmpty();
    }

    @Override
    public Iterator<Car> iterator() {
        return cars.iterator();
    }

    public static Train of(Car... cs) {
        Train t = new Train();
        if (cs != null) {
            for (Car c : cs) t.addLast(c);
        }
        return t;
    }
}
