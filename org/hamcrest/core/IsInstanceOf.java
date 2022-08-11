//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.hamcrest.core;

import org.hamcrest.*;

public class IsInstanceOf extends BaseMatcher<Object>
{
    private final Class<?> theClass;
    
    public IsInstanceOf(final Class<?> theClass) {
        this.theClass = theClass;
    }
    
    public boolean matches(final Object item) {
        return this.theClass.isInstance(item);
    }
    
    public void describeTo(final Description description) {
        description.appendText("an instance of ").appendText(this.theClass.getName());
    }
    
    @Factory
    public static Matcher<Object> instanceOf(final Class<?> type) {
        return new IsInstanceOf(type);
    }
}
