package com.jnape.palatable.lambda.monoid;

import com.jnape.palatable.lambda.semigroup.Semigroup;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class PresentTest {

    private static final Semigroup<Integer> ADDITION = (x, y) -> x + y;

    @Test
    public void appliesTwoPresentValues() {
        assertEquals(Optional.of(3), new Present<>(ADDITION).apply(Optional.of(1), Optional.of(2)));
    }

    @Test
    public void emptyValuesDoNotSubvertSemigroup() {
        assertEquals(Optional.of(1), new Present<>(ADDITION).apply(Optional.of(1), Optional.empty()));
        assertEquals(Optional.of(1), new Present<>(ADDITION).apply(Optional.empty(), Optional.of(1)));
        assertEquals(Optional.empty(), new Present<>(ADDITION).apply(Optional.empty(), Optional.empty()));
    }
}