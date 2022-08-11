//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.internal.matchers;

import org.hamcrest.*;

public class Each
{
    public static <T> Matcher<Iterable<T>> each(final Matcher<T> individual) {
        final Matcher<Iterable<T>> allItemsAre = CoreMatchers.not(IsCollectionContaining.hasItem((Matcher<? extends T>)CoreMatchers.not((Matcher<? extends T>)individual)));
        return new BaseMatcher<Iterable<T>>() {
            public boolean matches(final Object item) {
                return allItemsAre.matches(item);
            }
            
            public void describeTo(final Description description) {
                description.appendText("each ");
                individual.describeTo(description);
            }
        };
    }
}
