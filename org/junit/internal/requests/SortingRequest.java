//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.internal.requests;

import java.util.*;
import org.junit.runner.*;
import org.junit.runner.manipulation.*;

public class SortingRequest extends Request
{
    private final Request fRequest;
    private final Comparator<Description> fComparator;
    
    public SortingRequest(final Request request, final Comparator<Description> comparator) {
        this.fRequest = request;
        this.fComparator = comparator;
    }
    
    @Override
    public Runner getRunner() {
        final Runner runner = this.fRequest.getRunner();
        new Sorter(this.fComparator).apply(runner);
        return runner;
    }
}
