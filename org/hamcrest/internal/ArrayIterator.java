//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.hamcrest.internal;

import java.util.*;
import java.lang.reflect.*;

public class ArrayIterator implements Iterator<Object>
{
    private final Object array;
    private int currentIndex;
    
    public ArrayIterator(final Object array) {
        this.currentIndex = 0;
        if (!array.getClass().isArray()) {
            throw new IllegalArgumentException("not an array");
        }
        this.array = array;
    }
    
    public boolean hasNext() {
        return this.currentIndex < Array.getLength(this.array);
    }
    
    public Object next() {
        return Array.get(this.array, this.currentIndex++);
    }
    
    public void remove() {
        throw new UnsupportedOperationException("cannot remove items from an array");
    }
}
