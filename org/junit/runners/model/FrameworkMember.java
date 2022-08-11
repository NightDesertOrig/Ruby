//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.runners.model;

import java.lang.annotation.*;
import java.util.*;

abstract class FrameworkMember<T extends FrameworkMember<T>>
{
    abstract Annotation[] getAnnotations();
    
    abstract boolean isShadowedBy(final T p0);
    
    boolean isShadowedBy(final List<T> members) {
        for (final T each : members) {
            if (this.isShadowedBy(each)) {
                return true;
            }
        }
        return false;
    }
}
