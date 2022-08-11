//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.experimental.results;

import org.junit.runner.*;
import org.junit.runner.notification.*;
import java.util.*;

class FailureList
{
    private final List<Failure> failures;
    
    public FailureList(final List<Failure> failures) {
        this.failures = failures;
    }
    
    public Result result() {
        final Result result = new Result();
        final RunListener listener = result.createListener();
        for (final Failure failure : this.failures) {
            try {
                listener.testFailure(failure);
            }
            catch (Exception e) {
                throw new RuntimeException("I can't believe this happened");
            }
        }
        return result;
    }
}
