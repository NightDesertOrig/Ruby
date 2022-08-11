//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.experimental.results;

import org.junit.runner.*;
import java.util.*;
import org.junit.runner.notification.*;
import java.io.*;
import org.junit.internal.*;

public class PrintableResult
{
    private Result result;
    
    public static PrintableResult testResult(final Class<?> type) {
        return testResult(Request.aClass(type));
    }
    
    public static PrintableResult testResult(final Request request) {
        return new PrintableResult(new JUnitCore().run(request));
    }
    
    public PrintableResult(final List<Failure> failures) {
        this(new FailureList((List)failures).result());
    }
    
    private PrintableResult(final Result result) {
        this.result = result;
    }
    
    @Override
    public String toString() {
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        new TextListener(new PrintStream(stream)).testRunFinished(this.result);
        return stream.toString();
    }
    
    public int failureCount() {
        return this.result.getFailures().size();
    }
}
