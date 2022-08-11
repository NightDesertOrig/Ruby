//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.manager;

import java.util.*;

public class FriendManager
{
    public ArrayList<FriendPlayer> friendList;
    
    public FriendManager() {
        this.friendList = new ArrayList<FriendPlayer>();
    }
    
    public void addFriend(final String name) {
        if (!this.isFriend(name)) {
            this.friendList.add(new FriendPlayer(name));
        }
    }
    
    public void removeFriend(final String name) {
        this.friendList.removeIf(player -> player.getName().equals(name));
    }
    
    public ArrayList<FriendPlayer> getFriendList() {
        return this.friendList;
    }
    
    public boolean isFriend(final String name) {
        return this.friendList.stream().anyMatch(player -> player.getName().equals(name));
    }
    
    public static class FriendPlayer
    {
        String name;
        
        public FriendPlayer(final String name) {
            this.name = name;
        }
        
        public String getName() {
            return this.name;
        }
    }
}
