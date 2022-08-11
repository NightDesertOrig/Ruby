//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit;

import org.junit.internal.*;
import org.hamcrest.*;

public class Assert
{
    protected Assert() {
    }
    
    public static void assertTrue(final String message, final boolean condition) {
        if (!condition) {
            fail(message);
        }
    }
    
    public static void assertTrue(final boolean condition) {
        assertTrue(null, condition);
    }
    
    public static void assertFalse(final String message, final boolean condition) {
        assertTrue(message, !condition);
    }
    
    public static void assertFalse(final boolean condition) {
        assertFalse(null, condition);
    }
    
    public static void fail(final String message) {
        if (message == null) {
            throw new AssertionError();
        }
        throw new AssertionError((Object)message);
    }
    
    public static void fail() {
        fail(null);
    }
    
    public static void assertEquals(final String message, final Object expected, final Object actual) {
        if (expected == null && actual == null) {
            return;
        }
        if (expected != null && isEquals(expected, actual)) {
            return;
        }
        if (expected instanceof String && actual instanceof String) {
            final String cleanMessage = (message == null) ? "" : message;
            throw new ComparisonFailure(cleanMessage, (String)expected, (String)actual);
        }
        failNotEquals(message, expected, actual);
    }
    
    private static boolean isEquals(final Object expected, final Object actual) {
        return expected.equals(actual);
    }
    
    public static void assertEquals(final Object expected, final Object actual) {
        assertEquals(null, expected, actual);
    }
    
    public static void assertArrayEquals(final String message, final Object[] expecteds, final Object[] actuals) throws ArrayComparisonFailure {
        internalArrayEquals(message, expecteds, actuals);
    }
    
    public static void assertArrayEquals(final Object[] expecteds, final Object[] actuals) {
        assertArrayEquals(null, expecteds, actuals);
    }
    
    public static void assertArrayEquals(final String message, final byte[] expecteds, final byte[] actuals) throws ArrayComparisonFailure {
        internalArrayEquals(message, expecteds, actuals);
    }
    
    public static void assertArrayEquals(final byte[] expecteds, final byte[] actuals) {
        assertArrayEquals(null, expecteds, actuals);
    }
    
    public static void assertArrayEquals(final String message, final char[] expecteds, final char[] actuals) throws ArrayComparisonFailure {
        internalArrayEquals(message, expecteds, actuals);
    }
    
    public static void assertArrayEquals(final char[] expecteds, final char[] actuals) {
        assertArrayEquals(null, expecteds, actuals);
    }
    
    public static void assertArrayEquals(final String message, final short[] expecteds, final short[] actuals) throws ArrayComparisonFailure {
        internalArrayEquals(message, expecteds, actuals);
    }
    
    public static void assertArrayEquals(final short[] expecteds, final short[] actuals) {
        assertArrayEquals(null, expecteds, actuals);
    }
    
    public static void assertArrayEquals(final String message, final int[] expecteds, final int[] actuals) throws ArrayComparisonFailure {
        internalArrayEquals(message, expecteds, actuals);
    }
    
    public static void assertArrayEquals(final int[] expecteds, final int[] actuals) {
        assertArrayEquals(null, expecteds, actuals);
    }
    
    public static void assertArrayEquals(final String message, final long[] expecteds, final long[] actuals) throws ArrayComparisonFailure {
        internalArrayEquals(message, expecteds, actuals);
    }
    
    public static void assertArrayEquals(final long[] expecteds, final long[] actuals) {
        assertArrayEquals(null, expecteds, actuals);
    }
    
    public static void assertArrayEquals(final String message, final double[] expecteds, final double[] actuals, final double delta) throws ArrayComparisonFailure {
        new InexactComparisonCriteria(delta).arrayEquals(message, expecteds, actuals);
    }
    
    public static void assertArrayEquals(final double[] expecteds, final double[] actuals, final double delta) {
        assertArrayEquals(null, expecteds, actuals, delta);
    }
    
    public static void assertArrayEquals(final String message, final float[] expecteds, final float[] actuals, final float delta) throws ArrayComparisonFailure {
        new InexactComparisonCriteria(delta).arrayEquals(message, expecteds, actuals);
    }
    
    public static void assertArrayEquals(final float[] expecteds, final float[] actuals, final float delta) {
        assertArrayEquals(null, expecteds, actuals, delta);
    }
    
    private static void internalArrayEquals(final String message, final Object expecteds, final Object actuals) throws ArrayComparisonFailure {
        new ExactComparisonCriteria().arrayEquals(message, expecteds, actuals);
    }
    
    public static void assertEquals(final String message, final double expected, final double actual, final double delta) {
        if (Double.compare(expected, actual) == 0) {
            return;
        }
        if (Math.abs(expected - actual) > delta) {
            failNotEquals(message, new Double(expected), new Double(actual));
        }
    }
    
    public static void assertEquals(final long expected, final long actual) {
        assertEquals(null, expected, actual);
    }
    
    public static void assertEquals(final String message, final long expected, final long actual) {
        assertEquals(message, expected, (Object)actual);
    }
    
    @Deprecated
    public static void assertEquals(final double expected, final double actual) {
        assertEquals(null, expected, actual);
    }
    
    @Deprecated
    public static void assertEquals(final String message, final double expected, final double actual) {
        fail("Use assertEquals(expected, actual, delta) to compare floating-point numbers");
    }
    
    public static void assertEquals(final double expected, final double actual, final double delta) {
        assertEquals(null, expected, actual, delta);
    }
    
    public static void assertNotNull(final String message, final Object object) {
        assertTrue(message, object != null);
    }
    
    public static void assertNotNull(final Object object) {
        assertNotNull(null, object);
    }
    
    public static void assertNull(final String message, final Object object) {
        assertTrue(message, object == null);
    }
    
    public static void assertNull(final Object object) {
        assertNull(null, object);
    }
    
    public static void assertSame(final String message, final Object expected, final Object actual) {
        if (expected == actual) {
            return;
        }
        failNotSame(message, expected, actual);
    }
    
    public static void assertSame(final Object expected, final Object actual) {
        assertSame(null, expected, actual);
    }
    
    public static void assertNotSame(final String message, final Object unexpected, final Object actual) {
        if (unexpected == actual) {
            failSame(message);
        }
    }
    
    public static void assertNotSame(final Object unexpected, final Object actual) {
        assertNotSame(null, unexpected, actual);
    }
    
    private static void failSame(final String message) {
        String formatted = "";
        if (message != null) {
            formatted = message + " ";
        }
        fail(formatted + "expected not same");
    }
    
    private static void failNotSame(final String message, final Object expected, final Object actual) {
        String formatted = "";
        if (message != null) {
            formatted = message + " ";
        }
        fail(formatted + "expected same:<" + expected + "> was not:<" + actual + ">");
    }
    
    private static void failNotEquals(final String message, final Object expected, final Object actual) {
        fail(format(message, expected, actual));
    }
    
    static String format(final String message, final Object expected, final Object actual) {
        String formatted = "";
        if (message != null && !message.equals("")) {
            formatted = message + " ";
        }
        final String expectedString = String.valueOf(expected);
        final String actualString = String.valueOf(actual);
        if (expectedString.equals(actualString)) {
            return formatted + "expected: " + formatClassAndValue(expected, expectedString) + " but was: " + formatClassAndValue(actual, actualString);
        }
        return formatted + "expected:<" + expectedString + "> but was:<" + actualString + ">";
    }
    
    private static String formatClassAndValue(final Object value, final String valueString) {
        final String className = (value == null) ? "null" : value.getClass().getName();
        return className + "<" + valueString + ">";
    }
    
    @Deprecated
    public static void assertEquals(final String message, final Object[] expecteds, final Object[] actuals) {
        assertArrayEquals(message, expecteds, actuals);
    }
    
    @Deprecated
    public static void assertEquals(final Object[] expecteds, final Object[] actuals) {
        assertArrayEquals(expecteds, actuals);
    }
    
    public static <T> void assertThat(final T actual, final Matcher<T> matcher) {
        assertThat("", actual, matcher);
    }
    
    public static <T> void assertThat(final String reason, final T actual, final Matcher<T> matcher) {
        if (!matcher.matches(actual)) {
            final Description description = new StringDescription();
            description.appendText(reason);
            description.appendText("\nExpected: ");
            description.appendDescriptionOf(matcher);
            description.appendText("\n     got: ");
            description.appendValue(actual);
            description.appendText("\n");
            throw new AssertionError((Object)description.toString());
        }
    }
}
