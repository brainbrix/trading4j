package de.voidnode.trading4j.domain.timeframe;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;

import static java.time.ZoneOffset.UTC;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Checks if the {@link TimeFrame} {@link M30} works as expected.
 * 
 * @author Raik Bieniek
 */
public class M30Test {

    /**
     * Two instants are in the same {@link M30} time frame when all fields higher than minutes are the same and both
     * instants are between the last minute that has <code>m % 30 = 0</code> inclusive and <code>m + 30</code>
     * exclusive.
     *
     * <p>
     * Time frames start at minutes 0, and 30.
     * </p>
     */
    @Test
    public void m30InstantsAreInSameTimeFrameWhenTheyAreInTheSame30MinuteFrame() {
        final M30 cut = new M30();

        assertThat(cut.areInSameTimeFrame(LocalDate.of(2041, Month.FEBRUARY, 7).atTime(0, 29, 59, 99999).toInstant(UTC),
                LocalDate.of(2041, Month.FEBRUARY, 7).atTime(0, 0, 0, 0).toInstant(UTC))).isTrue();
        assertThat(
                cut.areInSameTimeFrame(LocalDate.of(1752, Month.NOVEMBER, 15).atTime(21, 53, 59, 9231).toInstant(UTC),
                        LocalDate.of(1752, Month.NOVEMBER, 15).atTime(21, 35, 34, 1234).toInstant(UTC))).isTrue();

        assertThat(cut.areInSameTimeFrame(LocalDate.of(2041, Month.FEBRUARY, 7).atTime(0, 0, 0, 0).toInstant(UTC),
                LocalDate.of(2041, Month.FEBRUARY, 7).atTime(0, 30, 0, 0).toInstant(UTC))).isFalse();

        assertThat(cut.areInSameTimeFrame(LocalDate.of(2570, Month.NOVEMBER, 24).atTime(15, 47, 0, 0).toInstant(UTC),
                LocalDate.of(2570, Month.NOVEMBER, 25).atTime(15, 47, 0, 0).toInstant(UTC))).isFalse();
        assertThat(cut.areInSameTimeFrame(LocalDate.of(2501, Month.JANUARY, 24).atTime(20, 29, 0, 0).toInstant(UTC),
                LocalDate.of(1892, Month.JANUARY, 24).atTime(20, 29, 0, 0).toInstant(UTC))).isFalse();
        assertThat(cut.areInSameTimeFrame(LocalDate.of(2013, Month.JANUARY, 10).atTime(9, 0, 57, 0).toInstant(UTC),
                LocalDate.of(2013, Month.OCTOBER, 10).atTime(9, 0, 57, 0).toInstant(UTC))).isFalse();
    }

    /**
     * {@link M30#instantOfNextFrame(Instant)} should return the instant with the minutes set to the earliest possible
     * {@link Instant} that is later than the {@link Instant} past and thats minutes are a multiple of 30.
     */
    @Test
    public void m30InstantOfNextFrameShouldReturnTheNextInstantWhereTheMinutesAreAMultipleOfThirty() {
        final M30 cut = new M30();

        assertThat(cut.instantOfNextFrame(LocalDate.of(7821, Month.NOVEMBER, 24).atTime(4, 59, 0, 0).toInstant(UTC)))
                .isEqualTo(LocalDate.of(7821, Month.NOVEMBER, 24).atTime(5, 0, 0, 0).toInstant(UTC));
        assertThat(cut.instantOfNextFrame(LocalDate.of(1975, Month.FEBRUARY, 10).atTime(14, 59, 0, 0).toInstant(UTC)))
                .isEqualTo(LocalDate.of(1975, Month.FEBRUARY, 10).atTime(15, 00, 0, 0).toInstant(UTC));
        assertThat(cut.instantOfNextFrame(LocalDate.of(2047, Month.MARCH, 7).atTime(16, 29, 0, 0).toInstant(UTC)))
                .isEqualTo(LocalDate.of(2047, Month.MARCH, 7).atTime(16, 30, 0, 0).toInstant(UTC));
        assertThat(cut.instantOfNextFrame(LocalDate.of(2014, Month.SEPTEMBER, 16).atTime(21, 29, 0, 0).toInstant(UTC)))
                .isEqualTo(LocalDate.of(2014, Month.SEPTEMBER, 16).atTime(21, 30, 0, 0).toInstant(UTC));

        assertThat(cut.instantOfNextFrame(LocalDate.of(1257, Month.MAY, 24).atTime(10, 37, 0, 0).toInstant(UTC)))
                .isEqualTo(LocalDate.of(1257, Month.MAY, 24).atTime(11, 0, 0, 0).toInstant(UTC));
        assertThat(cut.instantOfNextFrame(LocalDate.of(2047, Month.NOVEMBER, 24).atTime(4, 30, 0, 0).toInstant(UTC)))
                .isEqualTo(LocalDate.of(2047, Month.NOVEMBER, 24).atTime(5, 0, 0, 0).toInstant(UTC));
        assertThat(cut.instantOfNextFrame(LocalDate.of(2104, Month.OCTOBER, 10).atTime(3, 29, 47, 0).toInstant(UTC)))
                .isEqualTo(LocalDate.of(2104, Month.OCTOBER, 10).atTime(3, 30, 0, 0).toInstant(UTC));
        assertThat(
                cut.instantOfNextFrame(LocalDate.of(2004, Month.FEBRUARY, 10).atTime(17, 29, 0, 67832).toInstant(UTC)))
                .isEqualTo(LocalDate.of(2004, Month.FEBRUARY, 10).atTime(17, 30, 0, 0).toInstant(UTC));
        assertThat(cut.instantOfNextFrame(LocalDate.of(1947, Month.AUGUST, 10).atTime(13, 59, 25, 4578).toInstant(UTC)))
                .isEqualTo(LocalDate.of(1947, Month.AUGUST, 10).atTime(14, 0, 0, 0).toInstant(UTC));
    }
}
