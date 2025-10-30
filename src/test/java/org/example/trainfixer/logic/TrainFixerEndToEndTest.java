package org.example.trainfixer.logic;

import org.example.trainfixer.train.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrainFixerEndToEndTest {

    private static Car L(String id) { return new Car(CarCategory.LOCO, null, id); }
    private static Car S(String id) { return new Car(CarCategory.PASSENGER, PassengerSubtype.SEAT, id); }
    private static Car SL(String id){ return new Car(CarCategory.PASSENGER, PassengerSubtype.SLEEPER, id); }
    private static Car D(String id) { return new Car(CarCategory.PASSENGER, PassengerSubtype.DINING, id); }
    private static Car G(String id) { return new Car(CarCategory.CARGO, null, id); }

    private final TrainValidator validator = new TrainValidator();

    @Test
    void overLocomotion_underOrEqual10_extrasRemoved() {
        Train input = Train.of(L("A"), S("s1"), L("B"), D("d1"), SL("sl1"), G("g1"), L("C"));
        TrainFixer fixer = new TrainFixer();
        Train fixed = fixer.fix(input);

        // Expect: [L, S, D, SL, G] (one front L only)
        assertTrue(validator.isValid(fixed), "Fixed train should be valid");
    }

    @Test
    void missingDining_oneAddedAndPlacedBeforeSleepers() {
        Train input = Train.of(L("A"), S("s1"), SL("sl1"), G("g1"));
        TrainFixer fixer = new TrainFixer();
        Train fixed = fixer.fix(input);

        assertTrue(validator.isValid(fixed), "Fixed train should be valid");

    }

    @Test
    void nOver10_onlyFrontLoco_keptFront_noRearAddedBySpec() {
        // Build 11 cars with only one locomotive originally
        Train input = new Train();
        input.addLast(L("A"));
        for (int i=0;i<10;i++) input.addLast(S("s"+i));

        TrainFixer fixer = new TrainFixer();
        Train fixed = fixer.fix(input);


        boolean valid = validator.isValid(fixed);
        assertFalse(valid, "With >10 cars and only one locomotive available, result remains invalid per spec (no loco added).");
    }

    @Test
    void cargoInMiddle_andDiningAfterSleepers_bothCorrected() {
        Train input = Train.of(L("A"), S("s1"), SL("sl1"), D("d1"), G("g1"), S("s2"));
        TrainFixer fixer = new TrainFixer();
        Train fixed = fixer.fix(input);

        assertTrue(validator.isValid(fixed), "Fixed train should be valid");
    }
}
