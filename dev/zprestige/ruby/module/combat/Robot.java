//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.combat;

import dev.zprestige.ruby.module.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import dev.zprestige.ruby.module.movement.*;
import dev.zprestige.ruby.*;
import dev.zprestige.ruby.manager.*;
import java.util.function.*;
import dev.zprestige.ruby.util.*;
import net.minecraft.client.network.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import dev.zprestige.ruby.module.player.*;
import java.util.*;
import java.util.stream.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.*;
import dev.zprestige.ruby.events.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;
import dev.zprestige.ruby.eventbus.annotation.*;
import net.minecraft.network.play.server.*;

public class Robot extends Module
{
    public double prevHealth;
    public double totalDamagePerSecond1;
    public double prevPosX;
    public double prevPosZ;
    public int prevGapples;
    public int packets;
    public int targetObsidian;
    public int targetExpPercent;
    public int prevChorus;
    public int xt;
    public int zt;
    public int st;
    public int bowTicks;
    public boolean isEating;
    public boolean isSafe;
    public boolean isExping;
    public boolean enabledFastExp;
    public boolean handOnly;
    public boolean hasMined;
    public boolean enabledPacketMine;
    public boolean enabledAura;
    public boolean isMoving;
    public boolean enabledStep;
    public boolean enabledTrap;
    public boolean value;
    public boolean didChorus;
    public boolean switchedToSword;
    public boolean switchedPickaxe;
    public boolean preGappled;
    public boolean needsUnSneak;
    public boolean needsOnGround;
    public boolean dropped;
    public boolean landed;
    public boolean forcedHole;
    public boolean cantStep;
    public String mode;
    public String triggerMode;
    public HoleOperation holeOperation;
    public Timer mineTimer;
    public Timer announceTimer;
    public Timer holeTimer;
    public BlockPos minedPos;
    public BlockPos lastHole;
    public BlockPos nextHole;
    HashMap<Long, Double> damagePerSecond;
    
    public Robot() {
        this.prevHealth = 0.0;
        this.totalDamagePerSecond1 = 0.0;
        this.prevPosX = 0.0;
        this.prevPosZ = 0.0;
        this.prevGapples = 0;
        this.packets = 1;
        this.targetObsidian = 11;
        this.prevChorus = 0;
        this.xt = 0;
        this.zt = 0;
        this.st = 0;
        this.bowTicks = 0;
        this.isEating = false;
        this.isSafe = false;
        this.enabledFastExp = false;
        this.handOnly = false;
        this.hasMined = false;
        this.enabledPacketMine = false;
        this.enabledAura = false;
        this.isMoving = false;
        this.enabledStep = false;
        this.enabledTrap = false;
        this.value = false;
        this.didChorus = false;
        this.switchedToSword = false;
        this.switchedPickaxe = false;
        this.preGappled = false;
        this.needsUnSneak = false;
        this.needsOnGround = false;
        this.dropped = false;
        this.landed = false;
        this.forcedHole = false;
        this.cantStep = false;
        this.mode = "";
        this.triggerMode = "";
        this.holeOperation = null;
        this.mineTimer = new Timer();
        this.announceTimer = new Timer();
        this.holeTimer = new Timer();
        this.minedPos = null;
        this.lastHole = null;
        this.nextHole = null;
        this.damagePerSecond = new HashMap<Long, Double>();
    }
    
    @Override
    public void onEnable() {
        this.prevHealth = this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount();
        this.totalDamagePerSecond1 = 0.0;
        this.prevPosX = 0.0;
        this.prevPosZ = 0.0;
        this.prevGapples = this.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.GOLDEN_APPLE).mapToInt(ItemStack::getCount).sum() + ((this.mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE) ? this.mc.player.getHeldItemOffhand().getCount() : 0);
        this.isEating = false;
        this.isSafe = false;
        this.isExping = false;
        this.enabledFastExp = false;
        this.handOnly = false;
        this.hasMined = false;
        this.enabledPacketMine = false;
        this.isMoving = false;
        this.enabledStep = false;
        this.enabledTrap = false;
        this.didChorus = false;
        this.switchedToSword = false;
        this.switchedPickaxe = false;
        this.preGappled = false;
        this.needsUnSneak = false;
        this.needsOnGround = false;
        this.dropped = false;
        this.landed = false;
        this.forcedHole = false;
        this.cantStep = false;
        this.mode = "";
        this.triggerMode = "";
        this.damagePerSecond.clear();
        this.packets = 1;
        this.targetObsidian = 11;
        this.prevChorus = 0;
        this.xt = 0;
        this.zt = 0;
        this.st = 0;
        this.bowTicks = 0;
        this.mineTimer.setTime(0);
        this.announceTimer.setTime(0);
        this.holeTimer.setTime(0);
        this.minedPos = null;
        this.lastHole = null;
        this.nextHole = null;
        final NoSlow noSlow = NoSlow.Instance;
        this.value = noSlow.guiMove.GetSwitch();
        noSlow.guiMove.setValue(false);
        if (!SimpleCa.Instance.isEnabled()) {
            SimpleCa.Instance.enableModule();
        }
    }
    
    @Override
    public void onDisable() {
        this.mc.gameSettings.keyBindUseItem.pressed = false;
        this.resetMovement();
        NoSlow.Instance.guiMove.setValue(this.value);
        Filler.Instance.disableModule();
        if (SimpleCa.Instance.isEnabled()) {
            SimpleCa.Instance.disableModule();
        }
    }
    
    @Override
    public void onTick() {
        this.mc.player.rotationYaw = 180.0f;
        this.mc.player.rotationPitch = 0.0f;
        if (this.mc.player.ticksExisted > 20 && this.mc.player.posY >= 122.0) {
            if (this.mc.player.getHeldItemMainhand().getItem().equals(Items.AIR)) {
                this.mc.player.sendChatMessage("/kit bot");
            }
            if (this.mc.player.posZ > -5.4) {
                this.mc.gameSettings.keyBindForward.pressed = true;
            }
            else {
                this.mc.gameSettings.keyBindForward.pressed = false;
                if (this.mc.player.collidedHorizontally && !Step.Instance.isEnabled()) {
                    Step.Instance.enableModule();
                }
                this.mc.gameSettings.keyBindRight.pressed = true;
                if (this.mc.world.getBlockState(BlockUtil.getPlayerPos().down()).getBlock().equals(Blocks.AIR)) {
                    this.dropped = true;
                }
            }
            return;
        }
        if (this.dropped) {
            this.resetMovement();
            if (this.mc.player.onGround) {
                this.landed = true;
            }
        }
        if (this.landed) {
            if (this.mc.player.onGround) {
                this.moveToCenter();
            }
            this.toggleFeetPlace();
            this.landed = false;
            this.dropped = false;
        }
        if (this.needsUnSneak) {
            ++this.st;
            if (this.st >= 10) {
                this.mc.gameSettings.keyBindSneak.pressed = false;
                this.needsUnSneak = false;
            }
        }
        this.cantStep = (FeetPlace.Instance.isEnabled() && this.holeOperation != null && !this.holeOperation.equals(HoleOperation.RunOut));
        this.isSafe = BlockUtil.isPlayerSafe2((EntityPlayer)this.mc.player);
        final double health = this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount();
        final EntityPlayer entityPlayer = EntityUtil.getTarget(200.0f);
        this.handleDamage();
        if (this.isEating) {
            this.setUseItemUnpressedIfNeeded();
        }
        if (entityPlayer == null) {
            this.resetMovement();
            return;
        }
        if (this.mc.player.getDistance((Entity)entityPlayer) > 7.0f) {
            this.preGappled = false;
        }
        this.handleEat();
        this.totalDamagePerSecond1 = this.damagePerSecond.values().stream().mapToDouble(aDouble -> aDouble).sum();
        if (this.isMoving) {
            if (!Step.Instance.isEnabled() && !this.cantStep) {
                Step.Instance.enableModule();
                this.enabledStep = true;
            }
        }
        else if (this.enabledStep) {
            Step.Instance.disableModule();
            this.enabledStep = false;
        }
        final boolean needsToGoIntoHole = (this.mc.player.getDistance((Entity)entityPlayer) < 10.0f && !BlockUtil.isPlayerSafe2((EntityPlayer)this.mc.player) && health < 17.5) || this.totalDamagePerSecond1 > 10.0;
        final boolean isTowering = this.isTowering(entityPlayer);
        if (this.isSafe) {
            this.forcedHole = false;
            this.xt = 0;
            this.zt = 0;
            this.holeOperation = this.getHoleOperation(entityPlayer);
            this.lastHole = BlockUtil.getPlayerPos();
            this.performHoleOperation(this.holeOperation, entityPlayer);
        }
        else {
            if (this.prevPosX != 0.0 && this.prevPosZ != 0.0) {
                int i = 0;
                if (this.mc.player.posX == this.prevPosX) {
                    ++this.xt;
                    ++i;
                }
                if (this.mc.player.posZ == this.prevPosZ) {
                    ++this.zt;
                    ++i;
                }
                if (i == 2 && this.xt >= 5 && this.zt >= 5) {
                    this.toggleFeetPlace();
                    this.xt = 0;
                    this.zt = 0;
                }
            }
            this.holeTimer.setTime(0);
            final Aura aura = Aura.Instance;
            final Trap trap = Trap.Instance;
            if (aura.isEnabled() && this.enabledAura) {
                aura.disableModule();
                this.enabledAura = false;
            }
            if (trap.isEnabled() && this.enabledTrap) {
                trap.disableModule();
                this.enabledTrap = false;
            }
            if (Filler.Instance.isEnabled()) {
                Filler.Instance.disableModule();
            }
            this.needsOnGround = (health <= 17.5);
            if (this.needsOnGround) {
                Speed.Instance.speedMode.setValue("OnGround");
            }
            final boolean isFilled = this.nextHole != null && !this.mc.world.getBlockState(this.nextHole).getBlock().equals(Blocks.AIR);
            this.nextHole = this.getNextHole(entityPlayer, false, 0.0);
            if (needsToGoIntoHole || this.forcedHole || isTowering) {
                final BlockPos newNextHole = this.getNextHole(entityPlayer, true, 10.0);
                if (newNextHole != null) {
                    this.nextHole = newNextHole;
                    this.forcedHole = true;
                }
            }
            if (this.nextHole != null) {
                if (isFilled || this.nextHole.equals((Object)BlockUtil.getPlayerPos())) {
                    this.toggleFeetPlace();
                    this.resetMovement();
                }
                else {
                    this.moveToNextHole(this.nextHole);
                }
            }
        }
        this.prevPosX = this.mc.player.posX;
        this.prevPosZ = this.mc.player.posZ;
    }
    
    public void toggleFeetPlace() {
        Speed.Instance.speedMode.setValue("OnGround");
        this.mc.player.setVelocity(0.0, this.mc.player.motionY, 0.0);
        Step.Instance.disableModule();
        if (this.mc.player.onGround && !FeetPlace.Instance.isEnabled()) {
            FeetPlace.Instance.enableModule();
            this.resetMovement();
        }
    }
    
    public void resetMovement() {
        this.mc.gameSettings.keyBindForward.pressed = false;
        this.mc.gameSettings.keyBindBack.pressed = false;
        this.mc.gameSettings.keyBindRight.pressed = false;
        this.mc.gameSettings.keyBindLeft.pressed = false;
    }
    
    public void moveToNextHole(final BlockPos pos1) {
        final BlockPos pos2 = pos1.up();
        final Speed speed = Speed.Instance;
        if (!speed.isEnabled()) {
            speed.enableModule();
        }
        final BlockPos excludeYPos = new BlockPos((double)pos2.getX(), this.mc.player.posY, (double)pos2.getZ());
        if (this.mc.player.getDistanceSq(excludeYPos) < 6.0) {
            speed.speedMode.setValue("OnGround");
        }
        else if (!this.needsOnGround) {
            speed.speedMode.setValue("Strafe");
        }
        final AxisAlignedBB bb = new AxisAlignedBB(pos2).shrink(0.5);
        if (this.mc.player.getDistanceSq(excludeYPos) < 4.0 && Step.Instance.isEnabled()) {
            Step.Instance.disableModule();
        }
        if (this.mc.player.posZ > bb.minZ + 0.125) {
            this.mc.gameSettings.keyBindForward.pressed = true;
            this.isMoving = true;
        }
        else {
            this.mc.gameSettings.keyBindForward.pressed = false;
        }
        if (this.mc.player.posZ < bb.minZ - 0.125) {
            this.mc.gameSettings.keyBindBack.pressed = true;
            this.isMoving = true;
        }
        else {
            this.mc.gameSettings.keyBindBack.pressed = false;
        }
        if (this.mc.player.posX > bb.minX + 0.125) {
            this.mc.gameSettings.keyBindLeft.pressed = true;
            this.isMoving = true;
        }
        else {
            this.mc.gameSettings.keyBindLeft.pressed = false;
        }
        if (this.mc.player.posX < bb.minX - 0.125) {
            this.mc.gameSettings.keyBindRight.pressed = true;
            this.isMoving = true;
        }
        else {
            this.mc.gameSettings.keyBindRight.pressed = false;
        }
    }
    
    public boolean moveOutHole() {
        final BlockPos lastHole = BlockUtil.getPlayerPos().up();
        if (this.canEnter(lastHole.down())) {
            if ((this.isAir(lastHole.up()) && this.isAir(lastHole.north()) && this.isAir(lastHole.north().up())) || (this.isAir(lastHole.up()) && !this.isAir(lastHole.north()) && this.isAir(lastHole.north().up()) && this.isAir(lastHole.north().up().up()) && this.isAir(lastHole.up().up()))) {
                return this.mc.gameSettings.keyBindForward.pressed = true;
            }
            if ((this.isAir(lastHole.up()) && this.isAir(lastHole.east()) && this.isAir(lastHole.east().up())) || (this.isAir(lastHole.up()) && !this.isAir(lastHole.east()) && this.isAir(lastHole.east().up()) && this.isAir(lastHole.east().up().up()) && this.isAir(lastHole.up().up()))) {
                return this.mc.gameSettings.keyBindRight.pressed = true;
            }
            if ((this.isAir(lastHole.up()) && this.isAir(lastHole.south()) && this.isAir(lastHole.south().up())) || (this.isAir(lastHole.up()) && !this.isAir(lastHole.south()) && this.isAir(lastHole.south().up()) && this.isAir(lastHole.south().up().up()) && this.isAir(lastHole.up().up()))) {
                return this.mc.gameSettings.keyBindBack.pressed = true;
            }
            if ((this.isAir(lastHole.up()) && this.isAir(lastHole.west()) && this.isAir(lastHole.west().up())) || (this.isAir(lastHole.up()) && !this.isAir(lastHole.west()) && this.isAir(lastHole.west().up()) && this.isAir(lastHole.west().up().up()) && this.isAir(lastHole.up().up()))) {
                return this.mc.gameSettings.keyBindLeft.pressed = true;
            }
        }
        return false;
    }
    
    public Vec3d getCenter(final double posX, final double posY, final double posZ) {
        final double x = Math.floor(posX) + 0.5;
        final double y = Math.floor(posY);
        final double z = Math.floor(posZ) + 0.5;
        return new Vec3d(x, y, z);
    }
    
    public void moveToCenter() {
        if (this.mc.player.onGround) {
            final Vec3d center = this.getCenter(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ);
            if (this.mc.player.getDistanceSq(new BlockPos(center.x, center.y, center.z)) > 0.10000000149011612) {
                this.mc.player.setPosition(center.x, center.y, center.z);
            }
        }
    }
    
    public BlockPos getNextHole(final EntityPlayer entityPlayer, final boolean force, final double forceRadius) {
        final ArrayList<HoleManager.HolePos> allHoles = (ArrayList<HoleManager.HolePos>)Ruby.holeManager.holes;
        if (!allHoles.isEmpty()) {
            TreeMap<Double, BlockPos> posTreeMap;
            if (force) {
                posTreeMap = allHoles.stream().filter(holePos -> this.canEnter(holePos.pos) && this.lastHole != null && !this.lastHole.equals((Object)holePos.pos) && this.mc.player.getDistanceSq(holePos.pos) < forceRadius * forceRadius).collect((Collector<? super Object, ?, TreeMap<Double, BlockPos>>)Collectors.toMap(holePos -> this.mc.player.getDistanceSq(holePos.pos), holePos -> holePos.pos, (a, b) -> b, (Supplier<R>)TreeMap::new));
            }
            else {
                posTreeMap = allHoles.stream().filter(holePos -> this.canEnter(holePos.pos) && this.lastHole != null && !this.lastHole.equals((Object)holePos.pos) && entityPlayer.getDistanceSq(holePos.pos) > 4.0 && entityPlayer.getDistanceSq(holePos.pos) < 25.0).collect((Collector<? super Object, ?, TreeMap<Double, BlockPos>>)Collectors.toMap(holePos -> entityPlayer.getDistanceSq(holePos.pos), holePos -> holePos.pos, (a, b) -> b, (Supplier<R>)TreeMap::new));
            }
            if (!posTreeMap.isEmpty()) {
                return posTreeMap.firstEntry().getValue();
            }
        }
        return null;
    }
    
    public void performHoleOperation(final HoleOperation holeOperation, final EntityPlayer entityPlayer) {
        if (!FeetPlace.Instance.isEnabled()) {
            FeetPlace.Instance.enableModule();
        }
        final Aura aura = Aura.Instance;
        final FastExp fastExp = FastExp.Instance;
        final Trap trap = Trap.Instance;
        if (this.isExping && !holeOperation.equals(HoleOperation.Exp)) {
            this.mc.gameSettings.keyBindUseItem.pressed = false;
            this.isExping = false;
            fastExp.mode.setValue(this.mode);
            fastExp.triggerMode.setValue(this.triggerMode);
            fastExp.handOnly.setValue(this.handOnly);
            fastExp.packets.setValue((float)this.packets);
            if (this.enabledFastExp) {
                fastExp.disableModule();
            }
        }
        if (!holeOperation.equals(HoleOperation.RunOut)) {
            this.resetMovement();
            if (this.enabledStep) {
                Step.Instance.disableModule();
            }
            if (this.mc.player.onGround) {
                final Vec3d center = this.getCenter(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ);
                if (this.mc.player.getDistanceSq(new BlockPos(center.x, center.y, center.z)) > 0.10000000149011612) {
                    this.mc.player.setPosition(center.x, center.y, center.z);
                }
                this.isMoving = false;
            }
        }
        if (!holeOperation.equals(HoleOperation.MineEchest)) {
            this.targetObsidian = 11;
        }
        if (!holeOperation.equals(HoleOperation.Exp)) {
            this.targetExpPercent = 50;
        }
        if (!holeOperation.equals(HoleOperation.Sword) || !this.mc.player.getHeldItemMainhand().getItem().equals(Items.DIAMOND_SWORD)) {
            this.switchedToSword = false;
        }
        if (!holeOperation.equals(HoleOperation.Sword)) {
            if (this.enabledTrap) {
                trap.disableModule();
                this.enabledTrap = false;
            }
            if (this.enabledAura) {
                aura.disableModule();
                this.enabledAura = false;
            }
        }
        if (!Filler.Instance.isEnabled()) {
            Filler.Instance.enableModule();
        }
        if (this.isBeingCevBreakered()) {
            final BlockPos pos = BlockUtil.getPlayerPos().up().up().up();
            this.mc.world.loadedEntityList.stream().filter(entity -> entity.getDistanceSq(pos) < 1.0).forEach(this::breakCrystal);
        }
        final EnumFacing isBeingRussianed = this.isBeingRussianed();
        if (isBeingRussianed != null) {
            BlockPos pos2 = BlockUtil.getPlayerPos().up();
            boolean newPos = false;
            switch (isBeingRussianed) {
                case NORTH: {
                    pos2 = pos2.north();
                    newPos = true;
                    break;
                }
                case EAST: {
                    pos2 = pos2.east();
                    newPos = true;
                    break;
                }
                case SOUTH: {
                    pos2 = pos2.south();
                    newPos = true;
                    break;
                }
                case WEST: {
                    pos2 = pos2.west();
                    newPos = true;
                    break;
                }
            }
            if (newPos) {
                final BlockPos finalPos = pos2;
                this.mc.world.loadedEntityList.stream().filter(entity -> entity.getDistanceSq(finalPos) < 1.0).forEach(this::breakCrystal);
            }
        }
        switch (holeOperation) {
            case Exp: {
                if (this.isEating) {
                    return;
                }
                final int expSlot = InventoryUtil.getItemFromHotbar(Items.EXPERIENCE_BOTTLE);
                if (expSlot != -1) {
                    InventoryUtil.switchToSlot(expSlot);
                    this.mode = fastExp.mode.GetCombo();
                    this.triggerMode = fastExp.triggerMode.GetCombo();
                    this.handOnly = fastExp.handOnly.GetSwitch();
                    this.packets = (int)fastExp.packets.GetSlider();
                    fastExp.mode.setValue("Packet");
                    fastExp.triggerMode.setValue("RightClick");
                    fastExp.handOnly.setValue(true);
                    fastExp.packets.setValue(2.0f);
                    if (!fastExp.isEnabled()) {
                        fastExp.enableModule();
                        this.enabledFastExp = true;
                    }
                    this.mc.gameSettings.keyBindUseItem.pressed = true;
                    this.mc.player.rotationPitch = 90.0f;
                    this.isExping = true;
                    this.targetExpPercent = 80;
                    break;
                }
                break;
            }
            case MineEchest: {
                if (!this.isEating) {
                    this.handleMineEchest();
                    break;
                }
                break;
            }
            case Sword: {
                final int swordSlot = InventoryUtil.getItemFromHotbar(Items.DIAMOND_SWORD);
                if (swordSlot != -1 && !this.switchedToSword && !this.isEating) {
                    InventoryUtil.switchToSlot(swordSlot);
                    if (!aura.isEnabled()) {
                        aura.enableModule();
                        this.enabledAura = true;
                    }
                    this.switchedToSword = true;
                }
                if (!this.isEnemyInSameHole(entityPlayer) && !this.isTrapped(entityPlayer) && !trap.isEnabled()) {
                    trap.enableModule();
                    this.enabledTrap = true;
                    break;
                }
                break;
            }
            case Quiver: {
                this.doQuiver();
                break;
            }
            case RunOut: {
                if (this.holeTimer.getTimeSub(500L)) {
                    return;
                }
                final double health = this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount();
                if (health >= 20.0) {
                    Speed.Instance.speedMode.setValue("OnGround");
                    if (this.moveOutHole() && !this.needsObsidian() && !this.needsMending() && !this.needsEffect(entityPlayer)) {
                        this.moveOutHole();
                        this.isMoving = true;
                        break;
                    }
                    if (this.isEnemyInSameHole(entityPlayer) && !trap.isEnabled()) {
                        trap.enableModule();
                        this.enabledTrap = true;
                    }
                    this.handleChorus();
                    break;
                }
                else {
                    if (!this.isEating) {
                        this.doEat();
                        break;
                    }
                    break;
                }
                break;
            }
            case CounterTower: {
                this.handleTower();
                break;
            }
        }
    }
    
    public void breakCrystal(final Entity entity) {
        boolean switched = false;
        int currentItem = -1;
        final PotionEffect weakness = this.mc.player.getActivePotionEffect(MobEffects.WEAKNESS);
        if (weakness != null && !this.mc.player.getHeldItemMainhand().getItem().equals(Items.DIAMOND_SWORD)) {
            final int swordSlot = InventoryUtil.getItemFromHotbar(Items.DIAMOND_SWORD);
            currentItem = this.mc.player.inventory.currentItem;
            InventoryUtil.switchToSlot(swordSlot);
            switched = true;
        }
        Objects.requireNonNull(this.mc.getConnection()).sendPacket((Packet)new CPacketUseEntity(entity));
        if (switched) {
            this.mc.player.inventory.currentItem = currentItem;
            this.mc.playerController.updateController();
        }
    }
    
    public void doQuiver() {
        final int slot = InventoryUtil.getItemFromHotbar((Item)Items.BOW);
        if (slot != -1 && this.bowTicks == 0) {
            this.mc.player.rotationPitch = -90.0f;
            if (!this.mc.player.getHeldItemMainhand().getItem().equals(Items.BOW)) {
                InventoryUtil.switchToSlot(slot);
            }
            this.mc.gameSettings.keyBindUseItem.pressed = true;
            if (this.mc.player.getItemInUseMaxCount() >= 3) {
                this.mc.gameSettings.keyBindUseItem.pressed = false;
                this.bowTicks = 20;
            }
            if (this.bowTicks > 0) {
                --this.bowTicks;
            }
        }
    }
    
    public void handleTower() {
        final BlockPos pos = BlockUtil.getPlayerPos().up();
        final BlockPos pos2 = BlockUtil.getPlayerPos().up().up();
        final int slot = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.OBSIDIAN));
        if (slot != -1) {
            if (!this.hasTowered()) {
                final EnumFacing face = this.getTowerSide();
                if (face != null) {
                    BlockPos p = pos;
                    switch (this.getTowerSide()) {
                        case NORTH: {
                            p = pos.north();
                            break;
                        }
                        case EAST: {
                            p = pos.east();
                            break;
                        }
                        case SOUTH: {
                            p = pos.south();
                            break;
                        }
                        case WEST: {
                            p = pos.west();
                            break;
                        }
                    }
                    if (this.isAir(p)) {
                        BlockUtil.placeBlockWithSwitch(p, EnumHand.MAIN_HAND, false, true, slot);
                        return;
                    }
                    if (this.isAir(p.up())) {
                        BlockUtil.placeBlockWithSwitch(p.up(), EnumHand.MAIN_HAND, false, true, slot);
                    }
                }
            }
            else {
                if (this.isAir(pos2.north().east())) {
                    BlockUtil.placeBlockWithSwitch(pos2.north().east(), EnumHand.MAIN_HAND, false, true, slot);
                    return;
                }
                if (this.isAir(pos2.north())) {
                    BlockUtil.placeBlockWithSwitch(pos2.north(), EnumHand.MAIN_HAND, false, true, slot);
                    return;
                }
                if (this.isAir(pos2.north().west())) {
                    BlockUtil.placeBlockWithSwitch(pos2.north().west(), EnumHand.MAIN_HAND, false, true, slot);
                    return;
                }
                if (this.isAir(pos2)) {
                    BlockUtil.placeBlockWithSwitch(pos2, EnumHand.MAIN_HAND, false, true, slot);
                    return;
                }
                if (this.isAir(pos2.east())) {
                    BlockUtil.placeBlockWithSwitch(pos2.east(), EnumHand.MAIN_HAND, false, true, slot);
                    return;
                }
                if (this.isAir(pos2.west())) {
                    BlockUtil.placeBlockWithSwitch(pos2.west(), EnumHand.MAIN_HAND, false, true, slot);
                    return;
                }
                if (this.isAir(pos2.south())) {
                    BlockUtil.placeBlockWithSwitch(pos2.south(), EnumHand.MAIN_HAND, false, true, slot);
                    return;
                }
                if (this.isAir(pos2.south().east())) {
                    BlockUtil.placeBlockWithSwitch(pos2.south().east(), EnumHand.MAIN_HAND, false, true, slot);
                    return;
                }
                if (this.isAir(pos2.south().west())) {
                    BlockUtil.placeBlockWithSwitch(pos2.south().west(), EnumHand.MAIN_HAND, false, true, slot);
                }
            }
        }
    }
    
    public boolean hasTowered() {
        final BlockPos pos = BlockUtil.getPlayerPos().up();
        return (this.gud(pos.north()) && this.gud(pos.north().up())) || (this.gud(pos.east()) && this.gud(pos.east().up())) || (this.gud(pos.south()) && this.gud(pos.south().up())) || (this.gud(pos.west()) && this.gud(pos.west().up()));
    }
    
    public boolean gud(final BlockPos pos) {
        return this.mc.world.getBlockState(pos).getBlock().equals(Blocks.OBSIDIAN) || this.mc.world.getBlockState(pos).getBlock().equals(Blocks.BEDROCK);
    }
    
    public EnumFacing getTowerSide() {
        final BlockPos pos = BlockUtil.getPlayerPos().up();
        if (this.isGoodForTower(pos.north()) && this.isGoodForTower(pos.north().up())) {
            return EnumFacing.NORTH;
        }
        if (this.isGoodForTower(pos.east()) && this.isGoodForTower(pos.east().up())) {
            return EnumFacing.EAST;
        }
        if (this.isGoodForTower(pos.south()) && this.isGoodForTower(pos.south().up())) {
            return EnumFacing.SOUTH;
        }
        if (this.isGoodForTower(pos.west()) && this.isGoodForTower(pos.west().up())) {
            return EnumFacing.WEST;
        }
        return null;
    }
    
    public boolean isGoodForTower(final BlockPos pos) {
        return this.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR) || this.mc.world.getBlockState(pos).getBlock().equals(Blocks.BEDROCK) || this.mc.world.getBlockState(pos).getBlock().equals(Blocks.OBSIDIAN);
    }
    
    public boolean isTrapped(final EntityPlayer entityPlayer) {
        final BlockPos pos = EntityUtil.getPlayerPos(entityPlayer).up();
        return !this.isAir(pos.north()) && !this.isAir(pos.east()) && !this.isAir(pos.south()) && !this.isAir(pos.west()) && !this.isAir(pos.up()) && !this.isAir(pos.up().up());
    }
    
    public boolean isntAirAndEchest(final BlockPos pos) {
        return !this.isAir(pos) && this.isntEchest(pos);
    }
    
    public void handleMineEchest() {
        final BlockPos pos = BlockUtil.getPlayerPos().up();
        if (this.isntAirAndEchest(pos.north()) && this.isntAirAndEchest(pos.east()) && this.isntAirAndEchest(pos.south()) && this.isntAirAndEchest(pos.west())) {
            this.mineBlockForEchest(pos, true);
            return;
        }
        if (this.isntEchest(pos.north()) && this.isntEchest(pos.east()) && this.isntEchest(pos.south()) && this.isntEchest(pos.west())) {
            this.placeEchest(pos);
            return;
        }
        this.mineBlockForEchest(pos, false);
    }
    
    public void mineBlockForEchest(final BlockPos pos, final boolean obsidian) {
        final PacketMine packetMine = PacketMine.Instance;
        final int pickaxeSlot = InventoryUtil.getItemFromHotbar(Items.DIAMOND_PICKAXE);
        if (this.hasMined) {
            if (this.mineTimer.getTime(obsidian ? 2000L : 1000L)) {
                if (!this.mc.player.getHeldItemMainhand().getItem().equals(Items.DIAMOND_PICKAXE) && !this.isEating && pickaxeSlot != -1) {
                    InventoryUtil.switchToSlot(pickaxeSlot);
                }
                this.mc.playerController.onPlayerDamageBlock(this.minedPos, this.mc.player.getHorizontalFacing());
                EntityUtil.swingArm(EntityUtil.SwingType.MainHand);
                this.hasMined = false;
                if (packetMine.isEnabled() && this.enabledPacketMine) {
                    packetMine.disableModule();
                    this.enabledPacketMine = false;
                }
            }
            return;
        }
        if (pickaxeSlot != -1 && !this.isEating) {
            final BlockPos pos2 = obsidian ? (this.mc.world.getBlockState(pos.north()).getBlock().equals(Blocks.OBSIDIAN) ? pos.north() : (this.mc.world.getBlockState(pos.east()).getBlock().equals(Blocks.OBSIDIAN) ? pos.east() : (this.mc.world.getBlockState(pos.south()).getBlock().equals(Blocks.OBSIDIAN) ? pos.south() : pos.west()))) : this.findEchest(pos);
            if (pos2 == null) {
                return;
            }
            final EnumFacing enumFacing = BlockUtil.getClosestEnumFacing(pos2);
            if (enumFacing == null) {
                return;
            }
            if (!packetMine.isEnabled()) {
                packetMine.enableModule();
                this.enabledPacketMine = true;
            }
            InventoryUtil.switchToSlot(pickaxeSlot);
            this.mc.playerController.onPlayerDamageBlock(pos2, this.mc.player.getHorizontalFacing());
            EntityUtil.swingArm(EntityUtil.SwingType.MainHand);
            this.minedPos = pos2;
            this.mineTimer.setTime(0);
            this.targetObsidian = 48;
            this.hasMined = true;
        }
    }
    
    public BlockPos findEchest(final BlockPos pos) {
        if (!this.isntEchest(pos.north())) {
            return pos.north();
        }
        if (!this.isntEchest(pos.east())) {
            return pos.east();
        }
        if (!this.isntEchest(pos.south())) {
            return pos.south();
        }
        if (!this.isntEchest(pos.west())) {
            return pos.west();
        }
        return null;
    }
    
    public void placeEchest(final BlockPos pos) {
        final int echestSlot = InventoryUtil.getItemFromHotbar(Item.getItemFromBlock(Blocks.ENDER_CHEST));
        if (echestSlot == -1) {
            return;
        }
        if (this.isAir(pos.north())) {
            BlockUtil.placeBlockWithSwitch(pos.north(), EnumHand.MAIN_HAND, false, true, echestSlot);
            return;
        }
        if (this.isAir(pos.east())) {
            BlockUtil.placeBlockWithSwitch(pos.east(), EnumHand.MAIN_HAND, false, true, echestSlot);
            return;
        }
        if (this.isAir(pos.south())) {
            BlockUtil.placeBlockWithSwitch(pos.south(), EnumHand.MAIN_HAND, false, true, echestSlot);
            return;
        }
        if (this.isAir(pos.west())) {
            BlockUtil.placeBlockWithSwitch(pos.west(), EnumHand.MAIN_HAND, false, true, echestSlot);
        }
    }
    
    public boolean canEnter(final BlockPos pos) {
        return this.isAir(pos.up()) && this.isAir(pos.up().up()) && this.isAir(pos.up().up().up()) && this.isAir(pos.up().up().up().up());
    }
    
    public boolean isAir(final BlockPos pos) {
        return this.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR);
    }
    
    public boolean isntEchest(final BlockPos pos) {
        return !this.mc.world.getBlockState(pos).getBlock().equals(Blocks.ENDER_CHEST);
    }
    
    public HoleOperation getHoleOperation(final EntityPlayer entityPlayer) {
        final boolean isEnemyInSameHole = this.isEnemyInSameHole(entityPlayer);
        if (this.isTowering(entityPlayer)) {
            return HoleOperation.CounterTower;
        }
        if (!isEnemyInSameHole) {
            if (this.needsMending()) {
                return HoleOperation.Exp;
            }
            if (this.needsObsidian()) {
                return HoleOperation.MineEchest;
            }
            if (this.needsEffect(entityPlayer)) {
                return HoleOperation.Quiver;
            }
        }
        if (isEnemyInSameHole) {
            if (this.needsObsidian() || this.needsMending() || this.needsEffect(entityPlayer)) {
                return HoleOperation.RunOut;
            }
            if (this.mc.player.getDistance((Entity)entityPlayer) < this.mc.playerController.getBlockReachDistance()) {
                return HoleOperation.Sword;
            }
        }
        if (BlockUtil.isPlayerSafe2(entityPlayer) && this.mc.player.getDistance((Entity)entityPlayer) < this.mc.playerController.getBlockReachDistance()) {
            return HoleOperation.Sword;
        }
        if (BlockUtil.isPlayerSafe2(entityPlayer)) {
            return HoleOperation.RunOut;
        }
        if (!BlockUtil.isPlayerSafe2(entityPlayer) && this.mc.player.getDistance((Entity)entityPlayer) > this.mc.playerController.getBlockReachDistance()) {
            return HoleOperation.RunOut;
        }
        if (!BlockUtil.isPlayerSafe2(entityPlayer) && SimpleCa.Instance.cantPlace && this.mc.player.getDistance((Entity)entityPlayer) < this.mc.playerController.getBlockReachDistance()) {
            return HoleOperation.Sword;
        }
        return HoleOperation.Await;
    }
    
    public void setUseItemUnpressedIfNeeded() {
        final int currentChorus = this.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.CHORUS_FRUIT).mapToInt(ItemStack::getCount).sum() + ((this.mc.player.getHeldItemOffhand().getItem() == Items.CHORUS_FRUIT) ? this.mc.player.getHeldItemOffhand().getCount() : 0);
        final int currentGapples = this.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.GOLDEN_APPLE).mapToInt(ItemStack::getCount).sum() + ((this.mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE) ? this.mc.player.getHeldItemOffhand().getCount() : 0);
        final Item mainhandItem = this.mc.player.getHeldItemMainhand().getItem();
        if (currentGapples < this.prevGapples || currentChorus < this.prevChorus || (!mainhandItem.equals(Items.CHORUS_FRUIT) && !mainhandItem.equals(Items.GOLDEN_APPLE))) {
            this.mc.gameSettings.keyBindUseItem.pressed = false;
            this.isEating = false;
        }
    }
    
    public void handleEat() {
        final double totalDamagePerSecond = this.damagePerSecond.values().stream().mapToDouble(aDouble -> aDouble).sum();
        final double currentHealth = this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount();
        if (!this.isEating && (!BlockUtil.isPlayerSafe2((EntityPlayer)this.mc.player) || totalDamagePerSecond >= 10.0 || currentHealth < (this.isSafe ? (this.isBeingCevBreakered() ? 20.0f : 10.0f) : 15.0f))) {
            this.doEat();
        }
    }
    
    public void handleChorus() {
        if (!this.isEating) {
            this.doChorus();
        }
    }
    
    public void doChorus() {
        final int chorusSlot = InventoryUtil.getItemFromHotbar(Items.CHORUS_FRUIT);
        if (chorusSlot != -1) {
            InventoryUtil.switchToSlot(chorusSlot);
            this.mc.gameSettings.keyBindUseItem.pressed = true;
            this.prevChorus = this.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.CHORUS_FRUIT).mapToInt(ItemStack::getCount).sum() + ((this.mc.player.getHeldItemOffhand().getItem() == Items.CHORUS_FRUIT) ? this.mc.player.getHeldItemOffhand().getCount() : 0);
            this.isEating = true;
        }
    }
    
    public boolean needsMending() {
        return this.mc.player.inventory.armorInventory.stream().filter(is -> InventoryUtil.getItemFromHotbar(Items.EXPERIENCE_BOTTLE) != -1 && !is.isEmpty()).mapToInt(is -> 100 - (int)((1.0f - (is.getMaxDamage() - (float)is.getItemDamage()) / is.getMaxDamage()) * 100.0f)).anyMatch(percentage -> percentage < this.targetExpPercent);
    }
    
    public boolean needsObsidian() {
        return this.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Item.getItemFromBlock(Blocks.OBSIDIAN)).mapToInt(ItemStack::getCount).sum() + ((this.mc.player.getHeldItemOffhand().getItem() == Item.getItemFromBlock(Blocks.OBSIDIAN)) ? this.mc.player.getHeldItemOffhand().getCount() : 0) < this.targetObsidian;
    }
    
    public void doEat() {
        final int gappleSlot = InventoryUtil.getItemFromHotbar(Items.GOLDEN_APPLE);
        if (gappleSlot != -1) {
            InventoryUtil.switchToSlot(gappleSlot);
            this.mc.gameSettings.keyBindUseItem.pressed = true;
            this.prevGapples = this.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.GOLDEN_APPLE).mapToInt(ItemStack::getCount).sum() + ((this.mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE) ? this.mc.player.getHeldItemOffhand().getCount() : 0);
            this.isEating = true;
        }
    }
    
    public void handleDamage() {
        final double currentHealth = this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount();
        final long currentTimeMillis = System.currentTimeMillis();
        for (final Map.Entry<Long, Double> entry : this.damagePerSecond.entrySet()) {
            if (entry.getKey() < currentTimeMillis) {
                this.damagePerSecond.remove(entry.getKey());
                return;
            }
        }
        if (currentHealth < this.prevHealth) {
            this.damagePerSecond.put(currentTimeMillis + 1000L, this.prevHealth - currentHealth);
        }
        this.prevHealth = this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount();
    }
    
    public boolean isEnemyInSameHole(final EntityPlayer entityPlayer) {
        return this.isSafe && BlockUtil.isPlayerSafe2(entityPlayer) && this.mc.player.getDistance((Entity)entityPlayer) < 1.0f;
    }
    
    public boolean isBeingCevBreakered() {
        final BlockPos pos = BlockUtil.getPlayerPos();
        return !this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos.up().up().up())).isEmpty();
    }
    
    public boolean needsEffect(final EntityPlayer entityPlayer) {
        return this.mc.world.getBlockState(BlockUtil.getPlayerPos().up().up()).getBlock().equals(Blocks.AIR) && this.mc.world.getBlockState(BlockUtil.getPlayerPos().up().up().up()).getBlock().equals(Blocks.AIR) && !this.isEnemyInSameHole(entityPlayer) && this.canQuiver() && !this.mc.player.isPotionActive(MobEffects.STRENGTH);
    }
    
    public boolean canQuiver() {
        return InventoryUtil.getItemFromHotbar((Item)Items.BOW) != -1 && IntStream.range(9, 45).filter(i -> ((ItemStack)this.mc.player.inventoryContainer.getInventory().get(i)).getItem() instanceof ItemTippedArrow).mapToObj(i -> (ItemStack)this.mc.player.inventoryContainer.getInventory().get(i)).filter(arrow -> PotionUtils.getPotionFromItem(arrow).equals(PotionTypes.STRENGTH) || PotionUtils.getPotionFromItem(arrow).equals(PotionTypes.STRONG_STRENGTH) || PotionUtils.getPotionFromItem(arrow).equals(PotionTypes.LONG_STRENGTH)).anyMatch(arrow -> !this.mc.player.isPotionActive(MobEffects.STRENGTH));
    }
    
    public boolean isTowering(final EntityPlayer entityPlayer) {
        return entityPlayer.posY - this.mc.player.posY > 15.0;
    }
    
    public EnumFacing isBeingRussianed() {
        if (!BlockUtil.isPlayerSafe2((EntityPlayer)this.mc.player)) {
            return null;
        }
        for (final Map.Entry<Integer, DestroyBlockProgress> entry : this.mc.renderGlobal.damagedBlocks.entrySet()) {
            final BlockPos pos = entry.getValue().getPosition();
            if (this.isObsidian(pos.north()) && this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos.north().up())).isEmpty()) {
                return EnumFacing.NORTH;
            }
            if (this.isObsidian(pos.east()) && this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos.east().up())).isEmpty()) {
                return EnumFacing.EAST;
            }
            if (this.isObsidian(pos.south()) && this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos.south().up())).isEmpty()) {
                return EnumFacing.SOUTH;
            }
            if (this.isObsidian(pos.west()) && this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos.west().up())).isEmpty()) {
                return EnumFacing.WEST;
            }
        }
        return null;
    }
    
    public boolean isObsidian(final BlockPos pos) {
        return this.mc.world.getBlockState(pos).getBlock().equals(Blocks.OBSIDIAN);
    }
    
    @RegisterListener
    public void onPacketSend(final PacketEvent.PacketSendEvent event) {
        if (this.nullCheck() || !this.isEnabled()) {
            return;
        }
        try {
            if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock && (this.mc.player.getHeldItemMainhand().getItem().equals(Items.GOLDEN_APPLE) || this.mc.player.getHeldItemMainhand().getItem().equals(Items.CHORUS_FRUIT))) {
                final RayTraceResult rayTraceResult = this.mc.objectMouseOver;
                if (rayTraceResult == null || !this.mc.gameSettings.keyBindUseItem.isKeyDown() || !this.mc.world.getBlockState(rayTraceResult.getBlockPos()).getBlock().equals(Blocks.ENDER_CHEST)) {
                    return;
                }
                event.setCancelled(true);
                Objects.requireNonNull(this.mc.getConnection()).sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            }
        }
        catch (Exception ex) {}
    }
    
    @RegisterListener
    public void onPacketReceive(final PacketEvent.PacketReceiveEvent event) {
        if (this.nullCheck() || !this.isEnabled() || !(event.getPacket() instanceof SPacketPlayerPosLook)) {
            return;
        }
        this.mc.gameSettings.keyBindSneak.pressed = true;
        this.needsUnSneak = true;
    }
    
    public enum HoleOperation
    {
        Sword, 
        Exp, 
        Await, 
        MineEchest, 
        RunOut, 
        Quiver, 
        CounterTower, 
        City;
    }
}
