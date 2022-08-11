//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.manager;

import dev.zprestige.ruby.util.*;
import java.util.*;
import dev.zprestige.ruby.module.*;
import java.util.stream.*;
import java.lang.reflect.*;

public class ModuleManager
{
    public ArrayList<Module> moduleList;
    
    public ModuleManager() {
        this.moduleList = new ArrayList<Module>();
        this.addModules("client");
        this.addModules("combat");
        this.addModules("exploit");
        this.addModules("misc");
        this.addModules("movement");
        this.addModules("player");
        this.addModules("visual");
    }
    
    public void addModules(final String folder) {
        try {
            final List<Class<?>> classes = ClassFinder.from("dev.zprestige.ruby.module." + folder);
            if (classes == null) {
                return;
            }
            for (final Class<?> clazz : classes) {
                if (!Modifier.isAbstract(clazz.getModifiers()) && Module.class.isAssignableFrom(clazz)) {
                    for (final Constructor<?> constructor : clazz.getConstructors()) {
                        final String moduleName = clazz.getName().split("\\.")[5];
                        final Module instance = ((Module)constructor.newInstance(new Object[0])).withSuper(moduleName, this.getCategoryByName(folder));
                        Arrays.stream(instance.getClass().getDeclaredFields()).filter(field -> !field.isAccessible()).forEach(field -> field.setAccessible(true));
                        this.moduleList.add(instance);
                    }
                }
            }
        }
        catch (Exception ex) {}
    }
    
    public List<Module> getModulesInCategory(final Category category) {
        return this.moduleList.stream().filter(module -> module.getCategory().equals(category)).collect((Collector<? super Object, ?, List<Module>>)Collectors.toList());
    }
    
    public Category getCategoryByName(final String name) {
        return Arrays.stream(Category.values()).filter(category -> category.toString().toLowerCase().equals(name)).findFirst().orElse(null);
    }
    
    public List<Category> getCategories() {
        return Arrays.asList(Category.values());
    }
}
