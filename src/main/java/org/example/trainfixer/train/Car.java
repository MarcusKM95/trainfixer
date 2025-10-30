package org.example.trainfixer.train;

import java.util.Objects;

/**
 * Immutable value object for a wagon (car).
 * subtype is only meaningful if category == PASSENGER.
 */
public final class Car {

    private final CarCategory category;
    private final PassengerSubtype subtype; // null unless PASSENGER
    private final String label;

    public Car(CarCategory category, PassengerSubtype subtype, String label) {
        this.category = Objects.requireNonNull(category, "category");
        if (category == CarCategory.PASSENGER) {
            if (subtype == null) {
                throw new IllegalArgumentException("PASSENGER car must have a PassengerSubtype");
            }
        } else {
            // non-passenger must not have a subtype
            if (subtype != null) {
                throw new IllegalArgumentException("Only PASSENGER cars may have a PassengerSubtype");
            }
        }
        this.subtype = subtype;
        this.label = label;
    }

    public CarCategory getCategory() { return category; }
    public PassengerSubtype getSubtype() { return subtype; }
    public String getLabel() { return label; }

    @Override
    public String toString() {
        String base;
        switch (category) {
            case LOCO -> base = "L";
            case CARGO -> base = "G";
            case PASSENGER -> {
                if (subtype == PassengerSubtype.SEAT) base = "S";
                else if (subtype == PassengerSubtype.SLEEPER) base = "SL";
                else base = "D";
            }
            default -> base = "?";
        }
        return label == null ? base : base + "(" + label + ")";
    }
}
