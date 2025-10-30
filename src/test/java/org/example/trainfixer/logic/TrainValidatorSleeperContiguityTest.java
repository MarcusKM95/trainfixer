package org.example.trainfixer.logic;

import org.example.trainfixer.train.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrainValidatorSleeperContiguityTest {

    private static Car L(String id) { return new Car(CarCategory.LOCO, null, id); }
    private static Car S(String id) { return new Car(CarCategory.PASSENGER, PassengerSubtype.SEAT, id); }
    private static Car SL(String id){ return new Car(CarCategory.PASSENGER, PassengerSubtype.SLEEPER, id); }
    private static Car D(String id) { return new Car(CarCategory.PASSENGER, PassengerSubtype.DINING, id); }
    private static Car G(String id) { return new Car(CarCategory.CARGO, null, id); }

    private final TrainValidator validator = new TrainValidator();

    @Test
    void positive_zeroSleepers() {
        Train t = Train.of(L("1"), S("a"), D("d"), G("x"));
        assertTrue(validator.isValid(t));
    }

    @Test
    void positive_oneSleeper() {
        Train t = Train.of(L("1"), S("a"), SL("sl"), G("x"));
        assertTrue(validator.isValid(t));
    }

    @Test
    void positive_twoContiguousSleepers() {
        Train t = Train.of(L("1"), S("a"), D("d"), SL("sl1"), SL("sl2"), G("x"));
        assertTrue(validator.isValid(t));
    }

    @Test
    void negative_twoSleepersSeparated() {
        Train t = Train.of(L("1"), SL("sl1"), S("a"), SL("sl2"), G("x"));
        assertFalse(validator.isValid(t));
    }

    @Test
    void negative_threeSleepersOneSeparated() {
        Train t = Train.of(L("1"), SL("sl1"), SL("sl2"), S("a"), SL("sl3"), G("x"));
        assertFalse(validator.isValid(t));
    }
}
