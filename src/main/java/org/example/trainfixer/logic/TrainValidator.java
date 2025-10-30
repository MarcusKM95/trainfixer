package org.example.trainfixer.logic;

import org.example.trainfixer.train.Car;
import org.example.trainfixer.train.CarCategory;
import org.example.trainfixer.train.PassengerSubtype;
import org.example.trainfixer.train.Train;


public class TrainValidator {

    public boolean isValid(Train train) {
        if (train == null || train.isEmpty()) return false;

        int n = 0;
        int countL = 0, countSeat = 0, countSleeper = 0, countDining = 0, countCargo = 0;

        boolean seenCargo = false;
        boolean seenFirstSleeper = false;
        boolean seatOrDiningAfterSleeper = false;
        boolean diningAfterSleeper = false;

        boolean firstIsLoco = false;
        boolean lastIsLoco = false;
        boolean anySeen = false;

        int idx = 0;
        for (Car c : train) {
            anySeen = true;
            n++;

            // first / last flags
            if (idx == 0) firstIsLoco = (c.getCategory() == CarCategory.LOCO);

            CarCategory cat = c.getCategory();
            if (cat == CarCategory.LOCO) {
                countL++;
            } else if (cat == CarCategory.CARGO) {
                countCargo++;
                seenCargo = true;
            } else { // PASSENGER
                PassengerSubtype st = c.getSubtype();
                if (st == PassengerSubtype.SEAT) {
                    countSeat++;
                    if (seenFirstSleeper) seatOrDiningAfterSleeper = true;
                } else if (st == PassengerSubtype.SLEEPER) {
                    countSleeper++;
                    if (!seenFirstSleeper) seenFirstSleeper = true;
                } else if (st == PassengerSubtype.DINING) {
                    countDining++;
                    if (seenFirstSleeper) {
                        // Dining after first sleeper breaks "reach dining from seat without crossing sleeper"
                        diningAfterSleeper = true;
                        seatOrDiningAfterSleeper = true;
                    }
                }
                if (seenCargo) {
                    // Passenger after cargo is invalid
                    return false;
                }
            }

            idx++;
        }

        if (!anySeen) return false; // defensive

        // last car check
        int i = 0;
        for (Car c : train) {
            if (i == (n - 1)) {
                lastIsLoco = (c.getCategory() == CarCategory.LOCO);
            }
            i++;
        }

        // Sleeper contiguity & dining constraint
        if (seatOrDiningAfterSleeper) return false;
        if (diningAfterSleeper) return false;

        // Locomotive placement rules
        if (n <= 10) {
            // exactly one loco and it must be at the very front
            if (countL != 1) return false;
            if (!firstIsLoco) return false;
            if (lastIsLoco && n > 1) return false; // rear loco not allowed when length>1
        } else {
            if (countL != 2) return false;
            if (!(firstIsLoco && lastIsLoco)) return false;
        }

        return true;
    }
}
