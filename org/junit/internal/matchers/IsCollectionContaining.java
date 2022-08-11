//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.internal.matchers;

import org.hamcrest.*;
import org.hamcrest.core.*;
import java.util.*;

public class IsCollectionContaining<T> extends TypeSafeMatcher<Iterable<T>>
{
    private final Matcher<? extends T> elementMatcher;
    
    public IsCollectionContaining(final Matcher<? extends T> elementMatcher) {
        this.elementMatcher = elementMatcher;
    }
    
    @Override
    public boolean matchesSafely(final Iterable<T> collection) {
        for (final T item : collection) {
            if (this.elementMatcher.matches(item)) {
                return true;
            }
        }
        return false;
    }
    
    public void describeTo(final Description description) {
        description.appendText("a collection containing ").appendDescriptionOf(this.elementMatcher);
    }
    
    @Factory
    public static <T> Matcher<Iterable<T>> hasItem(final Matcher<? extends T> elementMatcher) {
        return (Matcher<Iterable<T>>)new IsCollectionContaining(elementMatcher);
    }
    
    @Factory
    public static <T> Matcher<Iterable<T>> hasItem(final T element) {
        return hasItem((Matcher<? extends T>)IsEqual.equalTo((T)element));
    }
    
    @Factory
    public static <T> Matcher<Iterable<T>> hasItems(final Matcher<? extends T>... elementMatchers) {
        final Collection<Matcher<? extends Iterable<T>>> all = new ArrayList<Matcher<? extends Iterable<T>>>(elementMatchers.length);
        for (final Matcher<? extends T> elementMatcher : elementMatchers) {
            all.add((Matcher<? extends Iterable<T>>)hasItem((Matcher<?>)elementMatcher));
        }
        return AllOf.allOf((Iterable<Matcher<? extends Iterable<T>>>)all);
    }
    
    @Factory
    public static <T> Matcher<Iterable<T>> hasItems(final T... elements) {
        final Collection<Matcher<? extends Iterable<T>>> all = new ArrayList<Matcher<? extends Iterable<T>>>(elements.length);
        for (final T element : elements) {
            all.add(hasItem(element));
        }
        return AllOf.allOf((Iterable<Matcher<? extends Iterable<T>>>)all);
    }
}
