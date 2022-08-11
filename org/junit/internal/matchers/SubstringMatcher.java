//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.internal.matchers;

import org.hamcrest.*;

public abstract class SubstringMatcher extends TypeSafeMatcher<String>
{
    protected final String substring;
    
    protected SubstringMatcher(final String substring) {
        this.substring = substring;
    }
    
    @Override
    public boolean matchesSafely(final String item) {
        return this.evalSubstringOf(item);
    }
    
    public void describeTo(final Description description) {
        description.appendText("a string ").appendText(this.relationship()).appendText(" ").appendValue(this.substring);
    }
    
    protected abstract boolean evalSubstringOf(final String p0);
    
    protected abstract String relationship();
}
