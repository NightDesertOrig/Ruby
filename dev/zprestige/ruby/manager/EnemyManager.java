//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.manager;

import java.util.*;

public class EnemyManager
{
    public ArrayList<EnemyPlayer> enemyList;
    
    public EnemyManager() {
        this.enemyList = new ArrayList<EnemyPlayer>();
    }
    
    public void addEnemy(final String name) {
        if (!this.isEnemy(name)) {
            this.enemyList.add(new EnemyPlayer(name));
        }
    }
    
    public void removeEnemy(final String name) {
        this.enemyList.removeIf(player -> player.getName().equals(name));
    }
    
    public ArrayList<EnemyPlayer> getEnemyList() {
        return this.enemyList;
    }
    
    public boolean isEnemy(final String name) {
        return this.enemyList.stream().anyMatch(player -> player.getName().equals(name));
    }
    
    public static class EnemyPlayer
    {
        String name;
        
        public EnemyPlayer(final String name) {
            this.name = name;
        }
        
        public String getName() {
            return this.name;
        }
    }
}
