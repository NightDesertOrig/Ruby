//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.junit.rules;

import java.io.*;

public class TemporaryFolder extends ExternalResource
{
    private File folder;
    
    protected void before() throws Throwable {
        this.create();
    }
    
    protected void after() {
        this.delete();
    }
    
    public void create() throws IOException {
        this.folder = this.newFolder();
    }
    
    public File newFile(final String fileName) throws IOException {
        final File file = new File(this.getRoot(), fileName);
        file.createNewFile();
        return file;
    }
    
    public File newFile() throws IOException {
        return File.createTempFile("junit", null, this.folder);
    }
    
    public File newFolder(final String... folderNames) {
        File file = this.getRoot();
        for (final String folderName : folderNames) {
            file = new File(file, folderName);
            file.mkdir();
        }
        return file;
    }
    
    public File newFolder() throws IOException {
        final File createdFolder = File.createTempFile("junit", "", this.folder);
        createdFolder.delete();
        createdFolder.mkdir();
        return createdFolder;
    }
    
    public File getRoot() {
        if (this.folder == null) {
            throw new IllegalStateException("the temporary folder has not yet been created");
        }
        return this.folder;
    }
    
    public void delete() {
        this.recursiveDelete(this.folder);
    }
    
    private void recursiveDelete(final File file) {
        final File[] files = file.listFiles();
        if (files != null) {
            for (final File each : files) {
                this.recursiveDelete(each);
            }
        }
        file.delete();
    }
}
