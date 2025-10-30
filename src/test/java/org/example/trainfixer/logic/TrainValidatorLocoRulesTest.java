package org.example.trainfixer.logic;

import org.example.trainfixer.train.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrainValidatorLocoRulesTest {

    private static Car L(String id) { return new Car(CarCategory.LOCO, null, id); }
    private static Car S(String id) { return new Car(CarCategory.PASSENGER, PassengerSubtype.SEAT, id); }
    private static Car SL(String id){ return new Car(CarCategory.PASSENGER, PassengerSubtype.SLEEPER, id); }
    private static Car D(String id) { return new Car(CarCategory.PASSENGER, PassengerSubtype.DINING, id); }
    private static Car G(String id) { return new Car(CarCategory.CARGO, null, id); }

    private final TrainValidator validator = new TrainValidator();

    @Test
    void positive_underOrEqual10_frontLocoOnly() {
        Train t = Train.of(L("1"), S("a"), G("x"));
        assertTrue(validator.isValid(t));
    }

    @Test
    void negative_underOrEqual10_missingFrontLoco() {
        Train t = Train.of(S("a"), G("x"));
        assertFalse(validator.isValid(t));
    }

    @Test
    void negative_underOrEqual10_rearLocoNotAllowed() {
        Train t = Train.of(L("1"), S("a"), L("2"));
        assertFalse(validator.isValid(t));
    }

    @Test
    void positive_over10_frontAndRearLoco() {
        Train t = new Train();
        t.addLast(L("1"));
        for (int i=0;i<9;i++) t.addLast(S("s"+i)); // now 10 incl first loco
        t.addLast(G("g"));
        t.addLast(L("2")); // total 12 cars
        assertTrue(validator.isValid(t));
    }

    @Test
    void negative_over10_onlyOneLoco() {
        Train t = new Train();
        t.addLast(L("1"));
        for (int i=0;i<10;i++) t.addLast(S("s"+i)); // total 11 cars, only front loco
        assertFalse(validator.isValid(t));
    }

    @Test
    void negative_over10_extraLocoInMiddle() {
        Train t = new Train();
        t.addLast(L("1"));
        for (int i=0;i<8;i++) t.addLast(S("s"+i));
        t.addLast(L("X")); // illegal middle loco
        t.addLast(G("g"));
        t.addLast(L("2"));
        assertFalse(validator.isValid(t));
    }
}
