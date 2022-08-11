//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.runner.manipulation;

import java.util.*;
import org.junit.runner.*;

public class Sorter implements Comparator<Description>
{
    public static Sorter NULL;
    private final Comparator<Description> fComparator;
    
    public Sorter(final Comparator<Description> comparator) {
        this.fComparator = comparator;
    }
    
    public void apply(final Object object) {
        if (object instanceof Sortable) {
            final Sortable sortable = (Sortable)object;
            sortable.sort(this);
        }
    }
    
    public int compare(final Description o1, final Description o2) {
        return this.fComparator.compare(o1, o2);
    }
    
    static {
        Sorter.NULL = new Sorter(new Comparator<Description>() {
            public int compare(final Description o1, final Description o2) {
                return 0;
            }
        });
    }
}
