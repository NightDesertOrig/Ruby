//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.rules;

import org.junit.runners.model.*;
import org.junit.runner.*;
import java.util.*;

public class RunRules extends Statement
{
    private final Statement statement;
    
    public RunRules(final Statement base, final Iterable<TestRule> rules, final Description description) {
        this.statement = applyAll(base, rules, description);
    }
    
    @Override
    public void evaluate() throws Throwable {
        this.statement.evaluate();
    }
    
    private static Statement applyAll(Statement result, final Iterable<TestRule> rules, final Description description) {
        for (final TestRule each : rules) {
            result = each.apply(result, description);
        }
        return result;
    }
}
