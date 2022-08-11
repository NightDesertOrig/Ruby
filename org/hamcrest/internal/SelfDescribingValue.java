//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.hamcrest.internal;

import org.hamcrest.*;

public class SelfDescribingValue<T> implements SelfDescribing
{
    private T value;
    
    public SelfDescribingValue(final T value) {
        this.value = value;
    }
    
    public void describeTo(final Description description) {
        description.appendValue(this.value);
    }
}
