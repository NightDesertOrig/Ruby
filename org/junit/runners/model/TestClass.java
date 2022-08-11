//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.runners.model;

import java.lang.annotation.*;
import java.util.*;
import org.junit.*;
import java.lang.reflect.*;

public class TestClass
{
    private final Class<?> fClass;
    private Map<Class<?>, List<FrameworkMethod>> fMethodsForAnnotations;
    private Map<Class<?>, List<FrameworkField>> fFieldsForAnnotations;
    
    public TestClass(final Class<?> klass) {
        this.fMethodsForAnnotations = new HashMap<Class<?>, List<FrameworkMethod>>();
        this.fFieldsForAnnotations = new HashMap<Class<?>, List<FrameworkField>>();
        this.fClass = klass;
        if (klass != null && klass.getConstructors().length > 1) {
            throw new IllegalArgumentException("Test class can only have one constructor");
        }
        for (final Class<?> eachClass : this.getSuperClasses(this.fClass)) {
            for (final Method eachMethod : eachClass.getDeclaredMethods()) {
                this.addToAnnotationLists(new FrameworkMethod(eachMethod), this.fMethodsForAnnotations);
            }
            for (final Field eachField : eachClass.getDeclaredFields()) {
                this.addToAnnotationLists(new FrameworkField(eachField), this.fFieldsForAnnotations);
            }
        }
    }
    
    private <T extends FrameworkMember<T>> void addToAnnotationLists(final T member, final Map<Class<?>, List<T>> map) {
        for (final Annotation each : member.getAnnotations()) {
            final Class<? extends Annotation> type = each.annotationType();
            final List<T> members = this.getAnnotatedMembers(map, type);
            if (member.isShadowedBy((List)members)) {
                return;
            }
            if (this.runsTopToBottom(type)) {
                members.add(0, member);
            }
            else {
                members.add(member);
            }
        }
    }
    
    public List<FrameworkMethod> getAnnotatedMethods(final Class<? extends Annotation> annotationClass) {
        return this.getAnnotatedMembers(this.fMethodsForAnnotations, annotationClass);
    }
    
    public List<FrameworkField> getAnnotatedFields(final Class<? extends Annotation> annotationClass) {
        return this.getAnnotatedMembers(this.fFieldsForAnnotations, annotationClass);
    }
    
    private <T> List<T> getAnnotatedMembers(final Map<Class<?>, List<T>> map, final Class<? extends Annotation> type) {
        if (!map.containsKey(type)) {
            map.put(type, new ArrayList<T>());
        }
        return map.get(type);
    }
    
    private boolean runsTopToBottom(final Class<? extends Annotation> annotation) {
        return annotation.equals(Before.class) || annotation.equals(BeforeClass.class);
    }
    
    private List<Class<?>> getSuperClasses(final Class<?> testClass) {
        final ArrayList<Class<?>> results = new ArrayList<Class<?>>();
        for (Class<?> current = testClass; current != null; current = current.getSuperclass()) {
            results.add(current);
        }
        return results;
    }
    
    public Class<?> getJavaClass() {
        return this.fClass;
    }
    
    public String getName() {
        if (this.fClass == null) {
            return "null";
        }
        return this.fClass.getName();
    }
    
    public Constructor<?> getOnlyConstructor() {
        final Constructor<?>[] constructors = this.fClass.getConstructors();
        Assert.assertEquals(1L, (long)constructors.length);
        return constructors[0];
    }
    
    public Annotation[] getAnnotations() {
        if (this.fClass == null) {
            return new Annotation[0];
        }
        return this.fClass.getAnnotations();
    }
    
    public <T> List<T> getAnnotatedFieldValues(final Object test, final Class<? extends Annotation> annotationClass, final Class<T> valueClass) {
        final List<T> results = new ArrayList<T>();
        for (final FrameworkField each : this.getAnnotatedFields(annotationClass)) {
            try {
                final Object fieldValue = each.get(test);
                if (!valueClass.isInstance(fieldValue)) {
                    continue;
                }
                results.add(valueClass.cast(fieldValue));
            }
            catch (IllegalAccessException e) {
                throw new RuntimeException("How did getFields return a field we couldn't access?", e);
            }
        }
        return results;
    }
    
    public boolean isANonStaticInnerClass() {
        return this.fClass.isMemberClass() && !Modifier.isStatic(this.fClass.getModifiers());
    }
}
