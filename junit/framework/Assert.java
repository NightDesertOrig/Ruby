//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package junit.framework;

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
            throw new AssertionFailedError();
        }
        throw new AssertionFailedError(message);
    }
    
    public static void fail() {
        fail(null);
    }
    
    public static void assertEquals(final String message, final Object expected, final Object actual) {
        if (expected == null && actual == null) {
            return;
        }
        if (expected != null && expected.equals(actual)) {
            return;
        }
        failNotEquals(message, expected, actual);
    }
    
    public static void assertEquals(final Object expected, final Object actual) {
        assertEquals(null, expected, actual);
    }
    
    public static void assertEquals(final String message, final String expected, final String actual) {
        if (expected == null && actual == null) {
            return;
        }
        if (expected != null && expected.equals(actual)) {
            return;
        }
        final String cleanMessage = (message == null) ? "" : message;
        throw new ComparisonFailure(cleanMessage, expected, actual);
    }
    
    public static void assertEquals(final String expected, final String actual) {
        assertEquals(null, expected, actual);
    }
    
    public static void assertEquals(final String message, final double expected, final double actual, final double delta) {
        if (Double.compare(expected, actual) == 0) {
            return;
        }
        if (Math.abs(expected - actual) > delta) {
            failNotEquals(message, new Double(expected), new Double(actual));
        }
    }
    
    public static void assertEquals(final double expected, final double actual, final double delta) {
        assertEquals(null, expected, actual, delta);
    }
    
    public static void assertEquals(final String message, final float expected, final float actual, final float delta) {
        if (Float.compare(expected, actual) == 0) {
            return;
        }
        if (Math.abs(expected - actual) > delta) {
            failNotEquals(message, new Float(expected), new Float(actual));
        }
    }
    
    public static void assertEquals(final float expected, final float actual, final float delta) {
        assertEquals(null, expected, actual, delta);
    }
    
    public static void assertEquals(final String message, final long expected, final long actual) {
        assertEquals(message, new Long(expected), new Long(actual));
    }
    
    public static void assertEquals(final long expected, final long actual) {
        assertEquals(null, expected, actual);
    }
    
    public static void assertEquals(final String message, final boolean expected, final boolean actual) {
        assertEquals(message, expected, (Object)actual);
    }
    
    public static void assertEquals(final boolean expected, final boolean actual) {
        assertEquals(null, expected, actual);
    }
    
    public static void assertEquals(final String message, final byte expected, final byte actual) {
        assertEquals(message, new Byte(expected), new Byte(actual));
    }
    
    public static void assertEquals(final byte expected, final byte actual) {
        assertEquals(null, expected, actual);
    }
    
    public static void assertEquals(final String message, final char expected, final char actual) {
        assertEquals(message, new Character(expected), new Character(actual));
    }
    
    public static void assertEquals(final char expected, final char actual) {
        assertEquals(null, expected, actual);
    }
    
    public static void assertEquals(final String message, final short expected, final short actual) {
        assertEquals(message, new Short(expected), new Short(actual));
    }
    
    public static void assertEquals(final short expected, final short actual) {
        assertEquals(null, expected, actual);
    }
    
    public static void assertEquals(final String message, final int expected, final int actual) {
        assertEquals(message, new Integer(expected), new Integer(actual));
    }
    
    public static void assertEquals(final int expected, final int actual) {
        assertEquals(null, expected, actual);
    }
    
    public static void assertNotNull(final Object object) {
        assertNotNull(null, object);
    }
    
    public static void assertNotNull(final String message, final Object object) {
        assertTrue(message, object != null);
    }
    
    public static void assertNull(final Object object) {
        final String message = "Expected: <null> but was: " + String.valueOf(object);
        assertNull(message, object);
    }
    
    public static void assertNull(final String message, final Object object) {
        assertTrue(message, object == null);
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
    
    public static void assertNotSame(final String message, final Object expected, final Object actual) {
        if (expected == actual) {
            failSame(message);
        }
    }
    
    public static void assertNotSame(final Object expected, final Object actual) {
        assertNotSame(null, expected, actual);
    }
    
    public static void failSame(final String message) {
        String formatted = "";
        if (message != null) {
            formatted = message + " ";
        }
        fail(formatted + "expected not same");
    }
    
    public static void failNotSame(final String message, final Object expected, final Object actual) {
        String formatted = "";
        if (message != null) {
            formatted = message + " ";
        }
        fail(formatted + "expected same:<" + expected + "> was not:<" + actual + ">");
    }
    
    public static void failNotEquals(final String message, final Object expected, final Object actual) {
        fail(format(message, expected, actual));
    }
    
    public static String format(final String message, final Object expected, final Object actual) {
        String formatted = "";
        if (message != null && message.length() > 0) {
            formatted = message + " ";
        }
        return formatted + "expected:<" + expected + "> but was:<" + actual + ">";
    }
}
