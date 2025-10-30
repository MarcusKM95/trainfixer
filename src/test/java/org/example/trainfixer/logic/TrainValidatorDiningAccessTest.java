package org.example.trainfixer.logic;

import org.example.trainfixer.train.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrainValidatorDiningAccessTest {

    private static Car L(String id) { return new Car(CarCategory.LOCO, null, id); }
    private static Car S(String id) { return new Car(CarCategory.PASSENGER, PassengerSubtype.SEAT, id); }
    private static Car SL(String id){ return new Car(CarCategory.PASSENGER, PassengerSubtype.SLEEPER, id); }
    private static Car D(String id) { return new Car(CarCategory.PASSENGER, PassengerSubtype.DINING, id); }
    private static Car G(String id) { return new Car(CarCategory.CARGO, null, id); }

    private final TrainValidator validator = new TrainValidator();

    @Test
    void positive_diningBeforeSleepers() {
        Train t = Train.of(L("1"), S("a"), S("b"), D("d"), SL("sl1"), SL("sl2"), G("x"));
        assertTrue(validator.isValid(t));
    }

    @Test
    void positive_noSleepers() {
        Train t = Train.of(L("1"), S("a"), D("d"), S("b"), G("x"));
        assertTrue(validator.isValid(t));
    }

    @Test
    void negative_diningAfterSleeper() {
        Train t = Train.of(L("1"), S("a"), SL("sl"), D("d"), G("x"));
        assertFalse(validator.isValid(t));
    }

    @Test
    void negative_seatAfterSleeper() {
        Train t = Train.of(L("1"), D("d"), SL("sl"), S("a"), G("x"));
        assertFalse(validator.isValid(t));
    }
}
