package org.bh.utility;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComparisonTest {


    @ParameterizedTest
    @CsvSource({",", "test,test"})
    void testValidComparisons(String t1, String t2) {
        assertTrue(Comparison.compareStrings(t1, t2));
    }

    @ParameterizedTest
    @CsvSource({",test", "test,", "test,test1", "test1,test"})
    void testInvalidComparisons(String t1, String t2) {
        assertFalse(Comparison.compareStrings(t1, t2));
    }

    @Test
    void testValidNullComparisons() {
        assertTrue(Comparison.compareStrings(null, null));
    }

    @Test
    void testInvalidNullComparisons() {
        assertFalse(Comparison.compareStrings(null, ""));
        assertFalse(Comparison.compareStrings("", null));
    }
}
