//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.internal.builders;

import org.junit.runners.model.*;
import org.junit.runner.*;
import org.junit.*;

public class IgnoredBuilder extends RunnerBuilder
{
    @Override
    public Runner runnerForClass(final Class<?> testClass) {
        if (testClass.getAnnotation(Ignore.class) != null) {
            return new IgnoredClassRunner(testClass);
        }
        return null;
    }
}
