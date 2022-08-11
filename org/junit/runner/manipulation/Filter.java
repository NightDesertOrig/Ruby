//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.runner.manipulation;

import org.junit.runner.*;
import java.util.*;

public abstract class Filter
{
    public static Filter ALL;
    
    public static Filter matchMethodDescription(final Description desiredDescription) {
        return new Filter() {
            @Override
            public boolean shouldRun(final Description description) {
                if (description.isTest()) {
                    return desiredDescription.equals((Object)description);
                }
                for (final Description each : description.getChildren()) {
                    if (this.shouldRun(each)) {
                        return true;
                    }
                }
                return false;
            }
            
            @Override
            public String describe() {
                return String.format("Method %s", desiredDescription.getDisplayName());
            }
        };
    }
    
    public abstract boolean shouldRun(final Description p0);
    
    public abstract String describe();
    
    public void apply(final Object child) throws NoTestsRemainException {
        if (!(child instanceof Filterable)) {
            return;
        }
        final Filterable filterable = (Filterable)child;
        filterable.filter(this);
    }
    
    public Filter intersect(final Filter second) {
        if (second == this || second == Filter.ALL) {
            return this;
        }
        final Filter first = this;
        return new Filter() {
            @Override
            public boolean shouldRun(final Description description) {
                return first.shouldRun(description) && second.shouldRun(description);
            }
            
            @Override
            public String describe() {
                return first.describe() + " and " + second.describe();
            }
        };
    }
    
    static {
        Filter.ALL = new Filter() {
            @Override
            public boolean shouldRun(final Description description) {
                return true;
            }
            
            @Override
            public String describe() {
                return "all tests";
            }
            
            @Override
            public void apply(final Object child) throws NoTestsRemainException {
            }
            
            @Override
            public Filter intersect(final Filter second) {
                return second;
            }
        };
    }
}
