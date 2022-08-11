//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.experimental.categories;

import org.junit.runners.*;
import org.junit.runners.model.*;
import org.junit.runner.manipulation.*;
import org.junit.runner.*;
import java.util.*;
import java.lang.annotation.*;

public class Categories extends Suite
{
    public Categories(final Class<?> klass, final RunnerBuilder builder) throws InitializationError {
        super(klass, builder);
        try {
            this.filter(new CategoryFilter(this.getIncludedCategory(klass), this.getExcludedCategory(klass)));
        }
        catch (NoTestsRemainException e) {
            throw new InitializationError(e);
        }
        this.assertNoCategorizedDescendentsOfUncategorizeableParents(this.getDescription());
    }
    
    private Class<?> getIncludedCategory(final Class<?> klass) {
        final IncludeCategory annotation = klass.getAnnotation(IncludeCategory.class);
        return (annotation == null) ? null : annotation.value();
    }
    
    private Class<?> getExcludedCategory(final Class<?> klass) {
        final ExcludeCategory annotation = klass.getAnnotation(ExcludeCategory.class);
        return (annotation == null) ? null : annotation.value();
    }
    
    private void assertNoCategorizedDescendentsOfUncategorizeableParents(final Description description) throws InitializationError {
        if (!canHaveCategorizedChildren(description)) {
            this.assertNoDescendantsHaveCategoryAnnotations(description);
        }
        for (final Description each : description.getChildren()) {
            this.assertNoCategorizedDescendentsOfUncategorizeableParents(each);
        }
    }
    
    private void assertNoDescendantsHaveCategoryAnnotations(final Description description) throws InitializationError {
        for (final Description each : description.getChildren()) {
            if (each.getAnnotation(Category.class) != null) {
                throw new InitializationError("Category annotations on Parameterized classes are not supported on individual methods.");
            }
            this.assertNoDescendantsHaveCategoryAnnotations(each);
        }
    }
    
    private static boolean canHaveCategorizedChildren(final Description description) {
        for (final Description each : description.getChildren()) {
            if (each.getTestClass() == null) {
                return false;
            }
        }
        return true;
    }
    
    public static class CategoryFilter extends Filter
    {
        private final Class<?> fIncluded;
        private final Class<?> fExcluded;
        
        public static CategoryFilter include(final Class<?> categoryType) {
            return new CategoryFilter(categoryType, null);
        }
        
        public CategoryFilter(final Class<?> includedCategory, final Class<?> excludedCategory) {
            this.fIncluded = includedCategory;
            this.fExcluded = excludedCategory;
        }
        
        @Override
        public String describe() {
            return "category " + this.fIncluded;
        }
        
        @Override
        public boolean shouldRun(final Description description) {
            if (this.hasCorrectCategoryAnnotation(description)) {
                return true;
            }
            for (final Description each : description.getChildren()) {
                if (this.shouldRun(each)) {
                    return true;
                }
            }
            return false;
        }
        
        private boolean hasCorrectCategoryAnnotation(final Description description) {
            final List<Class<?>> categories = this.categories(description);
            if (categories.isEmpty()) {
                return this.fIncluded == null;
            }
            for (final Class<?> each : categories) {
                if (this.fExcluded != null && this.fExcluded.isAssignableFrom(each)) {
                    return false;
                }
            }
            for (final Class<?> each : categories) {
                if (this.fIncluded == null || this.fIncluded.isAssignableFrom(each)) {
                    return true;
                }
            }
            return false;
        }
        
        private List<Class<?>> categories(final Description description) {
            final ArrayList<Class<?>> categories = new ArrayList<Class<?>>();
            categories.addAll(Arrays.asList(this.directCategories(description)));
            categories.addAll(Arrays.asList(this.directCategories(this.parentDescription(description))));
            return categories;
        }
        
        private Description parentDescription(final Description description) {
            final Class<?> testClass = description.getTestClass();
            if (testClass == null) {
                return null;
            }
            return Description.createSuiteDescription(testClass);
        }
        
        private Class<?>[] directCategories(final Description description) {
            if (description == null) {
                return (Class<?>[])new Class[0];
            }
            final Category annotation = description.getAnnotation(Category.class);
            if (annotation == null) {
                return (Class<?>[])new Class[0];
            }
            return annotation.value();
        }
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ExcludeCategory {
        Class<?> value();
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    public @interface IncludeCategory {
        Class<?> value();
    }
}
