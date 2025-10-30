package org.example.trainfixer.logic;

import org.example.trainfixer.train.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrainValidatorPassengerCargoTest {

    private static Car L(String id) { return new Car(CarCategory.LOCO, null, id); }
    private static Car S(String id) { return new Car(CarCategory.PASSENGER, PassengerSubtype.SEAT, id); }
    private static Car SL(String id){ return new Car(CarCategory.PASSENGER, PassengerSubtype.SLEEPER, id); }
    private static Car D(String id) { return new Car(CarCategory.PASSENGER, PassengerSubtype.DINING, id); }
    private static Car G(String id) { return new Car(CarCategory.CARGO, null, id); }

    private final TrainValidator validator = new TrainValidator();

    @Test
    void positive_passengersBeforeCargo() {
        Train t = Train.of(L("1"), S("a"), D("d"), SL("sl"), G("x"), G("y"));
        assertTrue(validator.isValid(t));
    }

    @Test
    void negative_passengerAfterCargo() {
        Train t = Train.of(L("1"), S("a"), G("x"), SL("sl"));
        assertFalse(validator.isValid(t));
    }

    @Test
    void edge_allPassengerNoCargo_validIfLocoRuleOk() {
        Train t = Train.of(L("1"), S("a"), D("d"));
        assertTrue(validator.isValid(t));
    }

    @Test
    void edge_allCargoNoPassenger_validIfLocoRuleOk() {
        Train t = Train.of(L("1"), G("x"), G("y"));
        assertTrue(validator.isValid(t));
    }
}
