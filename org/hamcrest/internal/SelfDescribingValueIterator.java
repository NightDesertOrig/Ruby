//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.hamcrest.internal;

import java.util.*;
import org.hamcrest.*;

public class SelfDescribingValueIterator<T> implements Iterator<SelfDescribing>
{
    private Iterator<T> values;
    
    public SelfDescribingValueIterator(final Iterator<T> values) {
        this.values = values;
    }
    
    public boolean hasNext() {
        return this.values.hasNext();
    }
    
    public SelfDescribing next() {
        return new SelfDescribingValue<Object>(this.values.next());
    }
    
    public void remove() {
        this.values.remove();
    }
}
