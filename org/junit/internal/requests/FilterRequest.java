//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.internal.requests;

import org.junit.runner.*;
import org.junit.internal.runners.*;
import org.junit.runner.manipulation.*;

public final class FilterRequest extends Request
{
    private final Request fRequest;
    private final Filter fFilter;
    
    public FilterRequest(final Request classRequest, final Filter filter) {
        this.fRequest = classRequest;
        this.fFilter = filter;
    }
    
    @Override
    public Runner getRunner() {
        try {
            final Runner runner = this.fRequest.getRunner();
            this.fFilter.apply(runner);
            return runner;
        }
        catch (NoTestsRemainException e) {
            return new ErrorReportingRunner(Filter.class, new Exception(String.format("No tests found matching %s from %s", this.fFilter.describe(), this.fRequest.toString())));
        }
    }
}
