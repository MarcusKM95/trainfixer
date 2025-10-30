package org.example.trainfixer.logic;

import org.example.trainfixer.train.*;

import java.util.ArrayList;
import java.util.List;

/**
 Repairs a train by partitioning cars into buckets and stitching back together.
 - Removes extra locomotives (keeps 1 if n<=10, keeps 2 if n>10).
 - Adds a dining car if none exists.
 - Orders: front loco(s) -> seats+dinings -> sleepers -> cargos -> rear loco (only if n>10).

 * We do not add locomotives if there are too few; we only remove extras and place existing ones correctly.
 */
public class TrainFixer {

    private int fullPassCount = 0;

    public int getFullPassCount() {
        return fullPassCount;
    }

    public Train fix(Train input) {
        if (input == null) return new Train();

        List<Car> locos = new ArrayList<>();
        List<Car> seats = new ArrayList<>();
        List<Car> dinings = new ArrayList<>();
        List<Car> sleepers = new ArrayList<>();
        List<Car> cargos = new ArrayList<>();

        int n = 0;
        for (Car c : input) {
            n++;
            switch (c.getCategory()) {
                case LOCO -> locos.add(c);
                case CARGO -> cargos.add(c);
                case PASSENGER -> {
                    switch (c.getSubtype()) {
                        case SEAT -> seats.add(c);
                        case SLEEPER -> sleepers.add(c);
                        case DINING -> dinings.add(c);
                    }
                }
            }
        }
        fullPassCount++;

        // Normalize dining: if missing, add exactly one dining car
        if (dinings.isEmpty()) {
            dinings.add(new Car(CarCategory.PASSENGER, PassengerSubtype.DINING, "auto"));
        }

        // Normalize locomotives: remove extras, keep needed amount if available
        List<Car> locosFront = new ArrayList<>();
        List<Car> locosRear = new ArrayList<>();
        if (n <= 10) {
            if (!locos.isEmpty()) {
                locosFront.add(locos.get(0)); // keep first as front loco
            }
        } else {
            if (locos.size() >= 2) {
                locosFront.add(locos.get(0));
                locosRear.add(locos.get(locos.size() - 1));
            } else if (locos.size() == 1) {
                // not enough to satisfy >10 rule; keep the single one at the front
                locosFront.add(locos.get(0));
            }
        }


        Train out = new Train();
        // front loco
        for (Car c : locosFront) out.addLast(c);
        // passengers: seats + dinings
        for (Car c : seats) out.addLast(c);
        for (Car c : dinings) out.addLast(c);
        // sleeper block
        for (Car c : sleepers) out.addLast(c);
        // cargos
        for (Car c : cargos) out.addLast(c);
        for (Car c : locosRear) out.addLast(c);

        fullPassCount++;
        return out;
    }
}
