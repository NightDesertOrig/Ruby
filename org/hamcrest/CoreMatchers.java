//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.hamcrest;

import org.hamcrest.core.*;

public class CoreMatchers
{
    public static <T> Matcher<T> is(final Matcher<T> matcher) {
        return Is.is(matcher);
    }
    
    public static <T> Matcher<T> is(final T value) {
        return Is.is(value);
    }
    
    public static Matcher<Object> is(final Class<?> type) {
        return Is.is(type);
    }
    
    public static <T> Matcher<T> not(final Matcher<T> matcher) {
        return IsNot.not(matcher);
    }
    
    public static <T> Matcher<T> not(final T value) {
        return IsNot.not(value);
    }
    
    public static <T> Matcher<T> equalTo(final T operand) {
        return IsEqual.equalTo(operand);
    }
    
    public static Matcher<Object> instanceOf(final Class<?> type) {
        return IsInstanceOf.instanceOf(type);
    }
    
    public static <T> Matcher<T> allOf(final Matcher<? extends T>... matchers) {
        return AllOf.allOf(matchers);
    }
    
    public static <T> Matcher<T> allOf(final Iterable<Matcher<? extends T>> matchers) {
        return AllOf.allOf(matchers);
    }
    
    public static <T> Matcher<T> anyOf(final Matcher<? extends T>... matchers) {
        return AnyOf.anyOf(matchers);
    }
    
    public static <T> Matcher<T> anyOf(final Iterable<Matcher<? extends T>> matchers) {
        return AnyOf.anyOf(matchers);
    }
    
    public static <T> Matcher<T> sameInstance(final T object) {
        return IsSame.sameInstance(object);
    }
    
    public static <T> Matcher<T> anything() {
        return IsAnything.anything();
    }
    
    public static <T> Matcher<T> anything(final String description) {
        return IsAnything.anything(description);
    }
    
    public static <T> Matcher<T> any(final Class<T> type) {
        return IsAnything.any(type);
    }
    
    public static <T> Matcher<T> nullValue() {
        return IsNull.nullValue();
    }
    
    public static <T> Matcher<T> nullValue(final Class<T> type) {
        return IsNull.nullValue(type);
    }
    
    public static <T> Matcher<T> notNullValue() {
        return IsNull.notNullValue();
    }
    
    public static <T> Matcher<T> notNullValue(final Class<T> type) {
        return IsNull.notNullValue(type);
    }
    
    public static <T> Matcher<T> describedAs(final String description, final Matcher<T> matcher, final Object... values) {
        return DescribedAs.describedAs(description, matcher, values);
    }
}
