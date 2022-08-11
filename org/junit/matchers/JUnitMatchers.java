//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.matchers;

import org.hamcrest.*;
import org.junit.internal.matchers.*;

public class JUnitMatchers
{
    public static <T> Matcher<Iterable<T>> hasItem(final T element) {
        return (Matcher<Iterable<T>>)IsCollectionContaining.hasItem((Object)element);
    }
    
    public static <T> Matcher<Iterable<T>> hasItem(final Matcher<? extends T> elementMatcher) {
        return (Matcher<Iterable<T>>)IsCollectionContaining.hasItem((Matcher)elementMatcher);
    }
    
    public static <T> Matcher<Iterable<T>> hasItems(final T... elements) {
        return (Matcher<Iterable<T>>)IsCollectionContaining.hasItems((Object[])elements);
    }
    
    public static <T> Matcher<Iterable<T>> hasItems(final Matcher<? extends T>... elementMatchers) {
        return (Matcher<Iterable<T>>)IsCollectionContaining.hasItems((Matcher[])elementMatchers);
    }
    
    public static <T> Matcher<Iterable<T>> everyItem(final Matcher<T> elementMatcher) {
        return (Matcher<Iterable<T>>)Each.each((Matcher)elementMatcher);
    }
    
    public static Matcher<String> containsString(final String substring) {
        return (Matcher<String>)StringContains.containsString(substring);
    }
    
    public static <T> CombinableMatcher<T> both(final Matcher<T> matcher) {
        return (CombinableMatcher<T>)new CombinableMatcher((Matcher)matcher);
    }
    
    public static <T> CombinableMatcher<T> either(final Matcher<T> matcher) {
        return (CombinableMatcher<T>)new CombinableMatcher((Matcher)matcher);
    }
}
