//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.runner;

import java.io.*;
import java.lang.annotation.*;
import java.util.*;
import java.util.regex.*;

public class Description implements Serializable
{
    private static final long serialVersionUID = 1L;
    public static final Description EMPTY;
    public static final Description TEST_MECHANISM;
    private final ArrayList<Description> fChildren;
    private final String fDisplayName;
    private final Annotation[] fAnnotations;
    
    public static Description createSuiteDescription(final String name, final Annotation... annotations) {
        if (name.length() == 0) {
            throw new IllegalArgumentException("name must have non-zero length");
        }
        return new Description(name, annotations);
    }
    
    public static Description createTestDescription(final Class<?> clazz, final String name, final Annotation... annotations) {
        return new Description(String.format("%s(%s)", name, clazz.getName()), annotations);
    }
    
    public static Description createTestDescription(final Class<?> clazz, final String name) {
        return createTestDescription(clazz, name, new Annotation[0]);
    }
    
    public static Description createSuiteDescription(final Class<?> testClass) {
        return new Description(testClass.getName(), testClass.getAnnotations());
    }
    
    private Description(final String displayName, final Annotation... annotations) {
        this.fChildren = new ArrayList<Description>();
        this.fDisplayName = displayName;
        this.fAnnotations = annotations;
    }
    
    public String getDisplayName() {
        return this.fDisplayName;
    }
    
    public void addChild(final Description description) {
        this.getChildren().add(description);
    }
    
    public ArrayList<Description> getChildren() {
        return this.fChildren;
    }
    
    public boolean isSuite() {
        return !this.isTest();
    }
    
    public boolean isTest() {
        return this.getChildren().isEmpty();
    }
    
    public int testCount() {
        if (this.isTest()) {
            return 1;
        }
        int result = 0;
        for (final Description child : this.getChildren()) {
            result += child.testCount();
        }
        return result;
    }
    
    @Override
    public int hashCode() {
        return this.getDisplayName().hashCode();
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Description)) {
            return false;
        }
        final Description d = (Description)obj;
        return this.getDisplayName().equals(d.getDisplayName());
    }
    
    @Override
    public String toString() {
        return this.getDisplayName();
    }
    
    public boolean isEmpty() {
        return this.equals(Description.EMPTY);
    }
    
    public Description childlessCopy() {
        return new Description(this.fDisplayName, this.fAnnotations);
    }
    
    public <T extends Annotation> T getAnnotation(final Class<T> annotationType) {
        for (final Annotation each : this.fAnnotations) {
            if (each.annotationType().equals(annotationType)) {
                return annotationType.cast(each);
            }
        }
        return null;
    }
    
    public Collection<Annotation> getAnnotations() {
        return Arrays.asList(this.fAnnotations);
    }
    
    public Class<?> getTestClass() {
        final String name = this.getClassName();
        if (name == null) {
            return null;
        }
        try {
            return Class.forName(name);
        }
        catch (ClassNotFoundException e) {
            return null;
        }
    }
    
    public String getClassName() {
        final Matcher matcher = this.methodStringMatcher();
        return matcher.matches() ? matcher.group(2) : this.toString();
    }
    
    public String getMethodName() {
        return this.parseMethod();
    }
    
    private String parseMethod() {
        final Matcher matcher = this.methodStringMatcher();
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return null;
    }
    
    private Matcher methodStringMatcher() {
        return Pattern.compile("(.*)\\((.*)\\)").matcher(this.toString());
    }
    
    static {
        EMPTY = new Description("No Tests", new Annotation[0]);
        TEST_MECHANISM = new Description("Test mechanism", new Annotation[0]);
    }
}
