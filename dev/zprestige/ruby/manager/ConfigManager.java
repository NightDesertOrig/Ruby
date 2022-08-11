//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.manager;

import net.minecraft.client.*;
import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.*;
import java.io.*;
import java.util.concurrent.atomic.*;
import dev.zprestige.ruby.settings.*;
import java.awt.*;
import org.lwjgl.input.*;
import java.util.*;
import dev.zprestige.ruby.settings.impl.*;

public class ConfigManager
{
    protected final Minecraft mc;
    protected final String separator;
    protected final ArrayList<Module> moduleList;
    protected File configPath;
    
    public ConfigManager() {
        this.mc = Ruby.mc;
        this.separator = File.separator;
        this.moduleList = Ruby.moduleManager.moduleList;
        this.configPath = new File(this.mc.gameDir + this.separator + "Ruby" + this.separator + "Configs");
        if (!this.configPath.exists()) {
            this.configPath.mkdirs();
        }
    }
    
    public ConfigManager loadFromActiveConfig() {
        final String activeConfig = this.readActiveConfig();
        if (!activeConfig.equals("NONE") && !activeConfig.equals("")) {
            this.configPath = new File(this.mc.gameDir + this.separator + "Ruby" + this.separator + "Configs" + this.separator + activeConfig);
            this.loadModules();
        }
        return this;
    }
    
    public void load(final String folder) {
        this.configPath = new File(this.mc.gameDir + this.separator + "Ruby" + this.separator + "Configs" + this.separator + folder);
        if (this.configPath.exists()) {
            this.loadModules();
            this.saveActiveConfig(folder);
        }
    }
    
    public void save(final String folder) {
        this.configPath = new File(this.mc.gameDir + this.separator + "Ruby" + this.separator + "Configs" + this.separator + folder);
        this.saveModules();
    }
    
    public void saveSocials() {
        final File file = this.registerPathAndCreate(this.mc.gameDir + this.separator + "Ruby" + this.separator + "Socials");
        final File friends = this.registerFileAndCreate(file + this.separator + "Friends.txt");
        final File enemies = this.registerFileAndCreate(file + this.separator + "Enemies.txt");
        try {
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(friends));
            Ruby.friendManager.getFriendList().forEach(friendPlayer -> this.writeLine(bufferedWriter, friendPlayer.getName()));
            bufferedWriter.close();
            final BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(enemies));
            Ruby.enemyManager.getEnemyList().forEach(enemyPlayer -> this.writeLine(bufferedWriter2, enemyPlayer.getName()));
            bufferedWriter2.close();
        }
        catch (IOException ex) {}
    }
    
    public ConfigManager readAndSetSocials() {
        final File file = this.registerPathAndCreate(this.mc.gameDir + this.separator + "Ruby" + this.separator + "Socials");
        final File friends = this.registerPathAndCreate(file + this.separator + "Friends.txt");
        final File enemies = this.registerPathAndCreate(file + this.separator + "Enemies.txt");
        try {
            final BufferedReader bufferReader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(friends))));
            final BufferedReader bufferReader2 = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(enemies))));
            Ruby.friendManager.getFriendList().clear();
            Ruby.enemyManager.getEnemyList().clear();
            bufferReader.lines().forEach(line -> Ruby.friendManager.addFriend(line));
            bufferReader2.lines().forEach(line -> Ruby.enemyManager.addEnemy(line));
        }
        catch (IOException ex) {}
        return this;
    }
    
    protected String readActiveConfig() {
        final File file = this.registerFileAndCreate(this.mc.gameDir + this.separator + "Ruby" + this.separator + "ActiveConfig.txt");
        if (!file.exists()) {
            return "NONE";
        }
        try {
            final BufferedReader bufferReader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file))));
            final AtomicReference<String> activeConfig = new AtomicReference<String>("");
            bufferReader.lines().forEach(line -> activeConfig.set(line.replace("\"", "")));
            bufferReader.close();
            return activeConfig.get();
        }
        catch (IOException ex) {
            return "NONE";
        }
    }
    
    protected void saveActiveConfig(final String folder) {
        final File file = this.registerFileAndCreate(this.mc.gameDir + this.separator + "Ruby" + this.separator + "ActiveConfig.txt");
        try {
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            this.writeLine(bufferedWriter, "\"" + folder + "\"");
            bufferedWriter.close();
        }
        catch (IOException ex) {}
    }
    
    protected void loadModules() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        dev/zprestige/ruby/manager/ConfigManager.moduleList:Ljava/util/ArrayList;
        //     4: aload_0         /* this */
        //     5: invokedynamic   BootstrapMethod #5, accept:(Ldev/zprestige/ruby/manager/ConfigManager;)Ljava/util/function/Consumer;
        //    10: invokevirtual   java/util/ArrayList.forEach:(Ljava/util/function/Consumer;)V
        //    13: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Could not infer any expression.
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:374)
        //     at com.strobel.decompiler.ast.TypeAnalysis.run(TypeAnalysis.java:96)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:344)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.Decompiler.decompile(Decompiler.java:70)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.decompile(Deobfuscator3000.java:538)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.decompileAndDeobfuscate(Deobfuscator3000.java:552)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.processMod(Deobfuscator3000.java:510)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.lambda$21(Deobfuscator3000.java:329)
        //     at java.base/java.lang.Thread.run(Thread.java:833)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    protected void setValueFromSetting(final Setting setting, final String line, final boolean enabled, final String colorSwitch) {
        if (enabled) {
            final Module module = setting.getModule();
            if (line.equals("true") && !module.isEnabled()) {
                module.enableModule();
            }
            else if (line.equals("false") && module.isEnabled()) {
                module.disableModule();
            }
            return;
        }
        if (setting instanceof ColorBox) {
            ((ColorBox)setting).setValue(new Color(Integer.parseInt(line), true));
            return;
        }
        if (setting instanceof ColorSwitch) {
            ((ColorSwitch)setting).setSwitchValue(Boolean.parseBoolean(line));
            ((ColorSwitch)setting).setColor(new Color(Integer.parseInt(colorSwitch), true));
            return;
        }
        if (setting instanceof ComboBox) {
            ((ComboBox)setting).setValue(line);
            return;
        }
        if (setting instanceof Slider) {
            ((Slider)setting).setValue(Float.parseFloat(line));
            return;
        }
        if (setting instanceof Key) {
            ((Key)setting).setValue(Keyboard.getKeyIndex(line));
            return;
        }
        if (setting instanceof Switch) {
            ((Switch)setting).setValue(Boolean.parseBoolean(line));
        }
    }
    
    protected Setting getSettingByNameAndModule(final Module module, final String name) {
        for (final Setting setting : module.getSettings()) {
            if (setting.getName().replace(" ", "").equals(name.replace(" ", ""))) {
                return setting;
            }
        }
        return null;
    }
    
    protected void saveModules() {
        final File path;
        final File file;
        final BufferedWriter bufferedWriter2;
        BufferedWriter bufferedWriter;
        ColorBox colorBox;
        final BufferedWriter bufferedWriter3;
        ColorSwitch colorSwitch;
        ComboBox comboBox;
        Key key;
        Slider slider;
        Switch aSwitch;
        this.moduleList.forEach(module -> {
            path = this.registerPathAndCreate(this.configPath + this.separator + module.getCategory().toString());
            file = this.registerFileAndCreate(path + this.separator + module.getName() + ".txt");
            try {
                new BufferedWriter(new FileWriter(file));
                bufferedWriter = bufferedWriter2;
                module.getSettings().stream().filter(setting -> !(setting instanceof Parent)).forEach(setting -> {
                    if (setting instanceof ColorBox) {
                        colorBox = (ColorBox)setting;
                        this.writeLine(bufferedWriter3, "\"" + colorBox.getName() + "\": \"" + colorBox.GetColor().getRGB() + "\"");
                    }
                    else if (setting instanceof ColorSwitch) {
                        colorSwitch = (ColorSwitch)setting;
                        this.writeLine(bufferedWriter3, "\"" + colorSwitch.getName() + "\": \"" + colorSwitch.GetSwitch() + "\":\"" + colorSwitch.GetColor().getRGB() + "\"");
                    }
                    else if (setting instanceof ComboBox) {
                        comboBox = (ComboBox)setting;
                        this.writeLine(bufferedWriter3, "\"" + comboBox.getName() + "\": \"" + comboBox.GetCombo() + "\"");
                    }
                    else if (setting instanceof Key) {
                        key = (Key)setting;
                        this.writeLine(bufferedWriter3, "\"" + key.getName() + "\": \"" + Keyboard.getKeyName(key.GetKey()) + "\"");
                    }
                    else if (setting instanceof Slider) {
                        slider = (Slider)setting;
                        this.writeLine(bufferedWriter3, "\"" + slider.getName() + "\": \"" + slider.GetSlider() + "\"");
                    }
                    else if (setting instanceof Switch) {
                        aSwitch = setting;
                        this.writeLine(bufferedWriter3, "\"" + aSwitch.getName() + "\": \"" + aSwitch.GetSwitch() + "\"");
                    }
                    return;
                });
                bufferedWriter.close();
            }
            catch (IOException ex) {}
        });
    }
    
    protected void writeLine(final BufferedWriter bufferedWriter, final String line) {
        try {
            bufferedWriter.write(line + "\r\n");
        }
        catch (IOException ex) {}
    }
    
    protected File registerFileAndCreate(final String file) {
        final File file2 = new File(file);
        try {
            file2.createNewFile();
        }
        catch (IOException ex) {}
        return file2;
    }
    
    protected File registerPathAndCreate(final String file) {
        final File file2 = new File(file);
        file2.mkdirs();
        return file2;
    }
}
