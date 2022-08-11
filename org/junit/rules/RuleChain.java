//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.rules;

import org.junit.runners.model.*;
import org.junit.runner.*;
import java.util.*;

public class RuleChain implements TestRule
{
    private static final RuleChain EMPTY_CHAIN;
    private List<TestRule> rulesStartingWithInnerMost;
    
    public static RuleChain emptyRuleChain() {
        return RuleChain.EMPTY_CHAIN;
    }
    
    public static RuleChain outerRule(final TestRule outerRule) {
        return emptyRuleChain().around(outerRule);
    }
    
    private RuleChain(final List<TestRule> rules) {
        this.rulesStartingWithInnerMost = rules;
    }
    
    public RuleChain around(final TestRule enclosedRule) {
        final List<TestRule> rulesOfNewChain = new ArrayList<TestRule>();
        rulesOfNewChain.add(enclosedRule);
        rulesOfNewChain.addAll(this.rulesStartingWithInnerMost);
        return new RuleChain(rulesOfNewChain);
    }
    
    public Statement apply(Statement base, final Description description) {
        for (final TestRule each : this.rulesStartingWithInnerMost) {
            base = each.apply(base, description);
        }
        return base;
    }
    
    static {
        EMPTY_CHAIN = new RuleChain(Collections.emptyList());
    }
}
