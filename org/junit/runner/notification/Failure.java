//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.runner.notification;

import org.junit.runner.*;
import java.io.*;

public class Failure implements Serializable
{
    private static final long serialVersionUID = 1L;
    private final Description fDescription;
    private final Throwable fThrownException;
    
    public Failure(final Description description, final Throwable thrownException) {
        this.fThrownException = thrownException;
        this.fDescription = description;
    }
    
    public String getTestHeader() {
        return this.fDescription.getDisplayName();
    }
    
    public Description getDescription() {
        return this.fDescription;
    }
    
    public Throwable getException() {
        return this.fThrownException;
    }
    
    @Override
    public String toString() {
        final StringBuffer buffer = new StringBuffer();
        buffer.append(this.getTestHeader() + ": " + this.fThrownException.getMessage());
        return buffer.toString();
    }
    
    public String getTrace() {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter);
        this.getException().printStackTrace(writer);
        final StringBuffer buffer = stringWriter.getBuffer();
        return buffer.toString();
    }
    
    public String getMessage() {
        return this.getException().getMessage();
    }
}
