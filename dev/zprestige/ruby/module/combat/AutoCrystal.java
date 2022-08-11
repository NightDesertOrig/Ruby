//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.module.combat;

import dev.zprestige.ruby.module.*;
import dev.zprestige.ruby.settings.impl.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import dev.zprestige.ruby.module.misc.*;
import dev.zprestige.ruby.*;
import net.minecraft.client.network.*;
import net.minecraft.network.*;
import net.minecraft.potion.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.item.*;
import java.util.stream.*;
import java.util.function.*;
import dev.zprestige.ruby.events.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.network.play.server.*;
import java.util.*;
import dev.zprestige.ruby.eventbus.annotation.*;
import net.minecraft.network.play.client.*;
import net.minecraft.world.*;
import dev.zprestige.ruby.util.*;
import java.awt.*;

public class AutoCrystal extends Module
{
    public final Parent targets;
    public final Slider targetRange;
    public final Parent others;
    public final ComboBox enumFacing;
    public final Switch rotations;
    public final Switch antiSelfHolePush;
    public final Parent placing;
    public final Slider placeRange;
    public final Slider placeWallRange;
    public final Slider placeDelay;
    public final Slider placeCalcDelay;
    public final Slider placeMinimumDamage;
    public final Slider placeMaximumSelfDamage;
    public final Switch placeIncludeMinOffset;
    public final Slider placeMinOffset;
    public final Switch placeIncludeMaxOffset;
    public final Slider placeMaxOffset;
    public final Switch placeSilentSwitch;
    public final Switch placeAntiSuicide;
    public final Switch placePacket;
    public final Switch placeOnePointThirteen;
    public final ComboBox placeCalculations;
    public final ComboBox placeSyncCalc;
    public final Switch placeFastCalc;
    public final Slider placeFastCalcSpeed;
    public final Switch placeMotionPredict;
    public final Slider placeMotionPredictAmount;
    public final Switch placeSwing;
    public final ComboBox placeSwingHand;
    public final Parent exploding;
    public final Slider explodeRange;
    public final Slider explodeWallRange;
    public final Slider explodeDelay;
    public final Slider explodeCalcDelay;
    public final Slider explodeMinimumDamage;
    public final Switch explodeIgnoreMinimumDamageAndTakeHighestDamageValueWhenever;
    public final Slider explodeMaximumSelfDamage;
    public final Switch explodeAntiStuck;
    public final Slider explodeAntiStuckThreshold;
    public final Switch explodeAntiSuicide;
    public final Switch explodePacket;
    public final Switch explodeInhibit;
    public final Switch breakMotionPredict;
    public final Slider breakMotionPredictAmount;
    public final Switch explodeAntiWeakness;
    public final Switch explodeSwing;
    public final ComboBox explodeSwingHand;
    public final Parent facePlacing;
    public final Slider facePlaceHp;
    public final Switch runDetectFacePlace;
    public final Switch facePlaceSlowOnCrouch;
    public final Parent predicting;
    public final Switch predict;
    public final Slider predictDelay;
    public final Switch predictSetDead;
    public final ComboBox predictSetDeadMode;
    public final Parent rendering;
    public final ComboBox renderMode;
    public final Slider fadeSpeed;
    public final Slider shrinkSpeed;
    public final Slider moveSpeed;
    public final ColorSwitch placeBox;
    public final ColorSwitch placeOutline;
    public final Slider placeLineWidth;
    public final Switch placeText;
    public PlacePosition placePosition;
    public ExplodePosition explodePosition;
    public EntityPlayer target;
    public Timer placeTimer;
    public Timer explodeTimer;
    public Timer predictTimer;
    public Timer placeCalcTimer;
    public Timer explodeCalcTimer;
    public Timer fastCalcTimer;
    public HashMap<BlockPos, Integer> fadePosses;
    public TreeMap<Float, PlacePosition> syncPossesDamage;
    public TreeMap<Float, PlacePosition> syncPossesDistance;
    public HashMap<Integer, Integer> antiStuckHashMap;
    public ArrayList<Entity> inhibitCrystal;
    public BlockPos pos;
    public float yaw;
    public float pitch;
    public boolean needsRotations;
    public AxisAlignedBB bb;
    public AxisAlignedBB chorusBB;
    public double cx;
    public double cy;
    public double cz;
    protected final Vec3i[] offsets;
    
    public AutoCrystal() {
        this.targets = this.Menu.Parent("Targets");
        this.targetRange = this.Menu.Slider("Target Range", 0.0f, 15.0f).parent(this.targets);
        this.others = this.Menu.Parent("Misc");
        this.enumFacing = this.Menu.ComboBox("Enum Facing", new String[] { "Force Up", "Closest" }).parent(this.others);
        this.rotations = this.Menu.Switch("Rotations").parent(this.others);
        this.antiSelfHolePush = this.Menu.Switch("Anti Self Hole Push").parent(this.others);
        this.placing = this.Menu.Parent("Placing");
        this.placeRange = this.Menu.Slider("Place Range", 0.0f, 6.0f).parent(this.placing);
        this.placeWallRange = this.Menu.Slider("Place Wall Range", 0.0f, 6.0f).parent(this.placing);
        this.placeDelay = this.Menu.Slider("Place Delay", 0, 500).parent(this.placing);
        this.placeCalcDelay = this.Menu.Slider("Place Calc Delay", 0, 500).parent(this.placing);
        this.placeMinimumDamage = this.Menu.Slider("Place Minimum Damage", 0.0f, 36.0f).parent(this.placing);
        this.placeMaximumSelfDamage = this.Menu.Slider("Place Maximum Self Damage", 0.0f, 36.0f).parent(this.placing);
        this.placeIncludeMinOffset = this.Menu.Switch("Include Min Offset").parent(this.placing);
        this.placeMinOffset = this.Menu.Slider("Place Min Offset", 0.0f, 15.0f).parent(this.placing);
        this.placeIncludeMaxOffset = this.Menu.Switch("Include Max Offset").parent(this.placing);
        this.placeMaxOffset = this.Menu.Slider("Place Max Offset", 0.0f, 15.0f).parent(this.placing);
        this.placeSilentSwitch = this.Menu.Switch("Place Silent Switch").parent(this.placing);
        this.placeAntiSuicide = this.Menu.Switch("Place Anti Suicide").parent(this.placing);
        this.placePacket = this.Menu.Switch("Place Packet").parent(this.placing);
        this.placeOnePointThirteen = this.Menu.Switch("One Point Thirteen").parent(this.placing);
        this.placeCalculations = this.Menu.ComboBox("Place Calculations", new String[] { "Sync", "HighestEnemyDamage", "LowestSelfDamage", "HighestSelfDistance", "LowestEnemyDistance", "New-TargetBased", "New-SelfBased" }).parent(this.placing);
        this.placeSyncCalc = this.Menu.ComboBox("Place Sync Calc", new String[] { "Autonomic", "Target" }).parent(this.placing);
        this.placeFastCalc = this.Menu.Switch("Place Fast Calc").parent(this.placing);
        this.placeFastCalcSpeed = this.Menu.Slider("Place Fast Calc Speed", 0, 100).parent(this.placing);
        this.placeMotionPredict = this.Menu.Switch("Place Motion Predict").parent(this.placing);
        this.placeMotionPredictAmount = this.Menu.Slider("Place Motion Predict Amount", 0, 5).parent(this.placing);
        this.placeSwing = this.Menu.Switch("Place Swing").parent(this.placing);
        this.placeSwingHand = this.Menu.ComboBox("Place Swing Hand", new String[] { "Mainhand", "Offhand", "Packet" }).parent(this.placing);
        this.exploding = this.Menu.Parent("Exploding");
        this.explodeRange = this.Menu.Slider("Explode Range", 0.0f, 6.0f).parent(this.exploding);
        this.explodeWallRange = this.Menu.Slider("Explode Wall Range", 0.0f, 6.0f).parent(this.exploding);
        this.explodeDelay = this.Menu.Slider("Explode Delay", 0, 500).parent(this.exploding);
        this.explodeCalcDelay = this.Menu.Slider("Explode Calc Delay", 0, 500).parent(this.exploding);
        this.explodeMinimumDamage = this.Menu.Slider("Explode Minimum Damage", 0.0f, 36.0f).parent(this.exploding);
        this.explodeIgnoreMinimumDamageAndTakeHighestDamageValueWhenever = this.Menu.Switch("Explode Ignore Minimum Damage And Take Highest Damage Value Whenever").parent(this.exploding);
        this.explodeMaximumSelfDamage = this.Menu.Slider("Explode Maximum Self Damage", 0.0f, 36.0f).parent(this.exploding);
        this.explodeAntiStuck = this.Menu.Switch("Explode Anti Stuck").parent(this.exploding);
        this.explodeAntiStuckThreshold = this.Menu.Slider("Explode Anti Stuck Threshold", 1, 10).parent(this.exploding);
        this.explodeAntiSuicide = this.Menu.Switch("Explode Anti Suicide").parent(this.exploding);
        this.explodePacket = this.Menu.Switch("Explode Packet").parent(this.exploding);
        this.explodeInhibit = this.Menu.Switch("Explode Inhibit").parent(this.exploding);
        this.breakMotionPredict = this.Menu.Switch("Break Motion Predict").parent(this.exploding);
        this.breakMotionPredictAmount = this.Menu.Slider("Break Motion Predict Amount", 0, 5).parent(this.exploding);
        this.explodeAntiWeakness = this.Menu.Switch("Explode Anti Weakness").parent(this.exploding);
        this.explodeSwing = this.Menu.Switch("Explode Swing").parent(this.exploding);
        this.explodeSwingHand = this.Menu.ComboBox("Explode Swing Hand", new String[] { "Mainhand", "Offhand", "Packet" }).parent(this.exploding);
        this.facePlacing = this.Menu.Parent("Face Placing");
        this.facePlaceHp = this.Menu.Slider("Face Place HP", 0.0f, 36.0f).parent(this.facePlacing);
        this.runDetectFacePlace = this.Menu.Switch("Run Detect Face Place").parent(this.facePlacing);
        this.facePlaceSlowOnCrouch = this.Menu.Switch("Face Place Slow On Crouch").parent(this.facePlacing);
        this.predicting = this.Menu.Parent("Predicting");
        this.predict = this.Menu.Switch("Predict").parent(this.predicting);
        this.predictDelay = this.Menu.Slider("Predict Delay", 0, 500).parent(this.predicting);
        this.predictSetDead = this.Menu.Switch("Predict Set Dead").parent(this.predicting);
        this.predictSetDeadMode = this.Menu.ComboBox("Predict Set Dead Mode", new String[] { "Post-Confirm", "Packet-Confirm" }).parent(this.predicting);
        this.rendering = this.Menu.Parent("Rendering");
        this.renderMode = this.Menu.ComboBox("Render Mode", new String[] { "Static", "Fade", "Shrink", "Moving" }).parent(this.rendering);
        this.fadeSpeed = this.Menu.Slider("Fade Speed", 100, 1000).parent(this.rendering);
        this.shrinkSpeed = this.Menu.Slider("Shrink Speed", 1, 100).parent(this.rendering);
        this.moveSpeed = this.Menu.Slider("Move Speed", 1, 100).parent(this.rendering);
        this.placeBox = this.Menu.ColorSwitch("Place Box").parent(this.rendering);
        this.placeOutline = this.Menu.ColorSwitch("Place Outline").parent(this.rendering);
        this.placeLineWidth = this.Menu.Slider("Place Line Width", 0.0f, 5.0f).parent(this.rendering);
        this.placeText = this.Menu.Switch("Place Text").parent(this.rendering);
        this.placePosition = new PlacePosition(null, 0.0f);
        this.explodePosition = new ExplodePosition(null, 0.0f);
        this.placeTimer = new Timer();
        this.explodeTimer = new Timer();
        this.predictTimer = new Timer();
        this.placeCalcTimer = new Timer();
        this.explodeCalcTimer = new Timer();
        this.fastCalcTimer = new Timer();
        this.fadePosses = new HashMap<BlockPos, Integer>();
        this.syncPossesDamage = new TreeMap<Float, PlacePosition>();
        this.syncPossesDistance = new TreeMap<Float, PlacePosition>();
        this.antiStuckHashMap = new HashMap<Integer, Integer>();
        this.inhibitCrystal = new ArrayList<Entity>();
        this.pos = null;
        this.yaw = 0.0f;
        this.pitch = 0.0f;
        this.needsRotations = false;
        this.bb = null;
        this.chorusBB = null;
        this.offsets = new Vec3i[] { new Vec3i(0, 1, 1), new Vec3i(0, 1, -1), new Vec3i(1, 1, 0), new Vec3i(-1, 1, 0) };
    }
    
    @Override
    public void onEnable() {
        this.antiStuckHashMap.clear();
    }
    
    @Override
    public void onTick() {
        this.setup();
        if (this.target == null) {
            return;
        }
        if (this.target.getDistanceSq(this.cx, this.cy, this.cz) < 4.0) {
            this.chorusBB = null;
        }
        if (this.placePosition != null && this.placeTimer.getTime((long)this.placeDelay.GetSlider())) {
            this.placeCrystal(this.placePosition.getBlockPos());
        }
        if (this.explodePosition != null && this.explodeTimer.getTime((this.facePlaceSlowOnCrouch.GetSwitch() && this.mc.gameSettings.keyBindSneak.isKeyDown()) ? 500L : ((long)this.explodeDelay.GetSlider()))) {
            this.explodeCrystal();
        }
        if (this.runDetectFacePlace.GetSwitch() && !RunDetect.Instance.isEnabled()) {
            Ruby.chatManager.sendMessage("Run Detect Face Place turned off, RunDetect needs to be enabled!");
            this.runDetectFacePlace.setValue(false);
        }
    }
    
    public void setup() {
        if (this.nullCheck()) {
            return;
        }
        this.target = EntityUtil.getTarget(this.targetRange.GetSlider());
        if (this.target == null) {
            return;
        }
        BlockPos currPos = null;
        if (this.placePosition != null) {
            currPos = this.placePosition.getBlockPos();
        }
        BlockPos prevPos = null;
        if (this.placePosition != null && this.placePosition.getBlockPos() != null) {
            prevPos = this.placePosition.getBlockPos();
        }
        if (this.placeCalcTimer.getTime((long)this.placeCalcDelay.GetSlider())) {
            this.placePosition = this.searchPosition(prevPos);
            this.placeCalcTimer.setTime(0);
        }
        if (this.explodeCalcTimer.getTime((long)this.explodeCalcDelay.GetSlider())) {
            this.explodePosition = this.searchCrystal();
            this.explodeCalcTimer.setTime(0);
        }
        if (this.placePosition != null && this.placePosition.getBlockPos() != currPos && !this.fadePosses.containsKey(this.placePosition.getBlockPos())) {
            this.fadePosses.put(this.placePosition.getBlockPos(), this.placeBox.GetColor().getAlpha());
        }
        if (this.placePosition != null && this.pos == null) {
            this.pos = this.placePosition.getBlockPos();
        }
        if (this.placePosition != null && this.placePosition.getBlockPos() != null && this.bb == null) {
            this.bb = new AxisAlignedBB(this.placePosition.getBlockPos());
        }
    }
    
    public void explodeCrystal() {
        if (this.explodeInhibit.GetSwitch() && this.inhibitCrystal.contains(this.explodePosition.getEntity())) {
            return;
        }
        boolean switched = false;
        int currentItem = -1;
        if (this.explodeAntiWeakness.GetSwitch()) {
            final PotionEffect weakness = this.mc.player.getActivePotionEffect(MobEffects.WEAKNESS);
            if (weakness != null && !this.mc.player.getHeldItemMainhand().getItem().equals(Items.DIAMOND_SWORD)) {
                final int swordSlot = InventoryUtil.getItemFromHotbar(Items.DIAMOND_SWORD);
                currentItem = this.mc.player.inventory.currentItem;
                InventoryUtil.switchToSlot(swordSlot);
                switched = true;
            }
        }
        if (this.rotations.GetSwitch()) {
            this.entityRotate(this.explodePosition.entity);
        }
        if (this.predictSetDead.GetSwitch() && this.predictSetDeadMode.GetCombo().equals("Pre-Confirm")) {
            this.explodePosition.getEntity().setDead();
        }
        if (this.explodePacket.GetSwitch()) {
            Objects.requireNonNull(this.mc.getConnection()).sendPacket((Packet)new CPacketUseEntity(this.explodePosition.getEntity()));
        }
        else {
            this.mc.playerController.attackEntity((EntityPlayer)this.mc.player, this.explodePosition.getEntity());
        }
        if (this.explodeAntiStuck.GetSwitch()) {
            int i = 1;
            i += this.antiStuckHashMap.entrySet().stream().filter(entry -> entry.getKey().equals(this.explodePosition.getEntity().entityId)).mapToInt(Map.Entry::getValue).sum();
            this.antiStuckHashMap.put(this.explodePosition.getEntity().entityId, i);
        }
        if (this.predictSetDead.GetSwitch() && this.predictSetDeadMode.GetCombo().equals("Post-Confirm")) {
            this.explodePosition.getEntity().setDead();
        }
        if (switched) {
            this.mc.player.inventory.currentItem = currentItem;
            this.mc.playerController.updateController();
        }
        if (this.explodeSwing.GetSwitch()) {
            EntityUtil.swingArm(this.explodeSwingHand.GetCombo().equals("Mainhand") ? EntityUtil.SwingType.MainHand : (this.explodeSwingHand.GetCombo().equals("Offhand") ? EntityUtil.SwingType.OffHand : EntityUtil.SwingType.Packet));
        }
        this.explodeTimer.setTime(0);
        if (this.explodeInhibit.GetSwitch()) {
            this.inhibitCrystal.add(this.explodePosition.getEntity());
        }
    }
    
    public void placeCrystal(final BlockPos pos) {
        if (!this.placeSilentSwitch.GetSwitch() && !this.mc.player.getHeldItemMainhand().getItem().equals(Items.END_CRYSTAL) && !this.mc.player.getHeldItemOffhand().getItem().equals(Items.END_CRYSTAL)) {
            return;
        }
        final int slot = InventoryUtil.getItemFromHotbar(Items.END_CRYSTAL);
        final int currentItem = this.mc.player.inventory.currentItem;
        if (this.placeSilentSwitch.GetSwitch() && slot != -1 && !this.mc.player.getHeldItemMainhand().getItem().equals(Items.END_CRYSTAL) && !this.mc.player.getHeldItemOffhand().getItem().equals(Items.END_CRYSTAL)) {
            InventoryUtil.switchToSlot(slot);
        }
        if (this.rotations.GetSwitch()) {
            this.posRotate(pos);
        }
        EnumFacing facing = null;
        try {
            if (BlockUtil.hasBlockEnumFacing(pos)) {
                facing = BlockUtil.getFirstFacing(pos);
            }
        }
        catch (Exception ignored) {
            System.out.println("06d is a pedo");
        }
        if (this.placePacket.GetSwitch()) {
            Objects.requireNonNull(this.mc.getConnection()).sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, (this.enumFacing.GetCombo().equals("Closest") && facing != null) ? facing : EnumFacing.UP, (this.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.5f, 0.5f, 0.5f));
        }
        else {
            this.mc.playerController.processRightClickBlock(this.mc.player, this.mc.world, pos, (this.enumFacing.GetCombo().equals("Closest") && facing != null) ? facing : EnumFacing.UP, new Vec3d(this.mc.player.posX, -this.mc.player.posY, -this.mc.player.posZ), this.mc.player.getHeldItemOffhand().getItem().equals(Items.END_CRYSTAL) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
        }
        if (this.placeSilentSwitch.GetSwitch()) {
            this.mc.player.inventory.currentItem = currentItem;
            this.mc.playerController.updateController();
        }
        if (this.placeSwing.GetSwitch()) {
            EntityUtil.swingArm(this.placeSwingHand.GetCombo().equals("Mainhand") ? EntityUtil.SwingType.MainHand : (this.placeSwingHand.GetCombo().equals("Offhand") ? EntityUtil.SwingType.OffHand : EntityUtil.SwingType.Packet));
        }
        this.placeTimer.setTime(0);
    }
    
    protected boolean canPlace(final BlockPos pos) {
        final ArrayList<Entity> intersecting = (ArrayList<Entity>)this.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(pos)).stream().filter(entity -> !(entity instanceof EntityEnderCrystal)).collect(Collectors.toCollection(ArrayList::new));
        return intersecting.isEmpty() && this.mc.player.getDistanceSq(pos) < this.placeRange.GetSlider() * this.placeRange.GetSlider() && (this.mc.world.getBlockState(pos).getBlock().equals(Blocks.OBSIDIAN) || this.mc.world.getBlockState(pos).getBlock().equals(Blocks.BEDROCK)) && this.mc.world.getBlockState(pos.up()).getBlock().equals(Blocks.AIR) && (this.mc.world.getBlockState(pos.up().up()).getBlock().equals(Blocks.AIR) || this.placeOnePointThirteen.GetSwitch());
    }
    
    public PlacePosition searchPosition(final BlockPos previousPos) {
        final TreeMap<Float, PlacePosition> posList = new TreeMap<Float, PlacePosition>();
        final TreeMap<Float, PlacePosition> posListDistance = new TreeMap<Float, PlacePosition>();
        final BlockPos selfPos = BlockUtil.getPlayerPos();
        if (this.antiSelfHolePush.GetSwitch()) {
            for (final Vec3i vec3i : this.offsets) {
                final BlockPos pos = selfPos.add(vec3i);
                if (this.mc.world.getBlockState(pos).getBlock().equals(Blocks.PISTON)) {
                    for (final Vec3i vec3i2 : this.offsets) {
                        final BlockPos pos2 = pos.add(vec3i2);
                        if (this.canPlace(pos2)) {
                            return new PlacePosition(pos2, 1000.0f);
                        }
                    }
                }
            }
        }
        for (final BlockPos pos3 : BlockUtil.getSphereAutoCrystal(this.placeRange.GetSlider(), true)) {
            if (BlockUtil.isPosValidForCrystal(pos3, this.placeOnePointThirteen.GetSwitch())) {
                if (this.placeMotionPredict.GetSwitch()) {
                    final Entity j = EntityUtil.getPredictedPosition((Entity)this.target, this.placeMotionPredictAmount.GetSlider());
                    this.target.setEntityBoundingBox(j.getEntityBoundingBox());
                }
                if (this.chorusBB != null) {
                    this.target.setEntityBoundingBox(this.chorusBB);
                }
                final float targetDamage = EntityUtil.calculatePosDamage(pos3, this.target);
                final float selfDamage = EntityUtil.calculatePosDamage(pos3, (EntityPlayer)this.mc.player);
                final float selfHealth = this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount();
                final float targetHealth = this.target.getHealth() + this.target.getAbsorptionAmount();
                float minimumDamageValue = this.placeMinimumDamage.GetSlider();
                if (this.mc.player.getDistanceSq((double)(pos3.getX() + 0.5f), (double)pos3.getY(), (double)(pos3.getZ() + 0.5f)) > this.placeRange.GetSlider() * this.placeRange.GetSlider()) {
                    continue;
                }
                if (previousPos != null) {
                    if (this.placeIncludeMinOffset.GetSwitch() && pos3.getDistance(previousPos.getX(), previousPos.getY(), previousPos.getZ()) < this.placeMinOffset.GetSlider()) {
                        continue;
                    }
                    if (this.placeIncludeMaxOffset.GetSwitch() && pos3.getDistance(previousPos.getX(), previousPos.getY(), previousPos.getZ()) > this.placeMaxOffset.GetSlider()) {
                        continue;
                    }
                }
                if (this.placeFastCalc.GetSwitch() && this.fastCalcTimer.getTime((long)(1000.0f - this.placeFastCalcSpeed.GetSlider() * 10.0f)) && !this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(new BlockPos(pos3.getX() + 0.5, pos3.getY() + 1.0, pos3.getZ() + 0.5))).isEmpty()) {
                    continue;
                }
                if (BlockUtil.rayTraceCheckPos(new BlockPos((double)(pos3.getX() + 0.5f), (double)pos3.getY(), (double)(pos3.getZ() + 0.5f))) && this.mc.player.getDistance((double)(pos3.getX() + 0.5f), (double)pos3.getY(), (double)(pos3.getZ() + 0.5f)) > this.placeWallRange.GetSlider() * this.placeWallRange.GetSlider()) {
                    continue;
                }
                if (BlockUtil.isPlayerSafe(this.target) && targetHealth < this.facePlaceHp.GetSlider()) {
                    minimumDamageValue = 2.0f;
                }
                if (this.runDetectFacePlace.GetSwitch() && BlockUtil.isPlayerSafe(this.target) && RunDetect.Instance.gappledPreviouslySwordedPotentialRunnerList.contains(this.target)) {
                    minimumDamageValue = 2.0f;
                }
                if (this.facePlaceSlowOnCrouch.GetSwitch() && this.mc.gameSettings.keyBindSneak.isKeyDown()) {
                    minimumDamageValue = 2.0f;
                }
                if (targetDamage < minimumDamageValue) {
                    continue;
                }
                if (selfDamage > this.placeMaximumSelfDamage.GetSlider()) {
                    continue;
                }
                if (this.placeAntiSuicide.GetSwitch() && selfDamage > selfHealth) {
                    continue;
                }
                final String getCombo = this.placeCalculations.GetCombo();
                switch (getCombo) {
                    case "Sync": {
                        posList.put(targetDamage, new PlacePosition(pos3, targetDamage));
                        final String getCombo2 = this.placeSyncCalc.GetCombo();
                        switch (getCombo2) {
                            case "Autonomic": {
                                posListDistance.put((float)(this.mc.player.getDistanceSq((double)(pos3.getX() + 0.5f), (double)pos3.getY(), (double)(pos3.getZ() + 0.5f)) / 2.0), new PlacePosition(pos3, targetDamage));
                                continue;
                            }
                            case "Target": {
                                posListDistance.put((float)(this.target.getDistanceSq((double)(pos3.getX() + 0.5f), (double)pos3.getY(), (double)(pos3.getZ() + 0.5f)) / 2.0), new PlacePosition(pos3, targetDamage));
                                continue;
                            }
                        }
                        continue;
                    }
                    case "HighestEnemyDamage":
                    case "New-TargetBased":
                    case "New-SelfBased": {
                        posList.put(targetDamage, new PlacePosition(pos3, targetDamage));
                        continue;
                    }
                    case "LowestSelfDamage": {
                        posList.put(selfDamage, new PlacePosition(pos3, targetDamage));
                        continue;
                    }
                    case "HighestSelfDistance": {
                        posList.put((float)(this.mc.player.getDistanceSq((double)(pos3.getX() + 0.5f), (double)pos3.getY(), (double)(pos3.getZ() + 0.5f)) / 2.0), new PlacePosition(pos3, targetDamage));
                        continue;
                    }
                    case "LowestEnemyDistance": {
                        posList.put((float)(this.target.getDistanceSq((double)(pos3.getX() + 0.5f), (double)pos3.getY(), (double)(pos3.getZ() + 0.5f)) / 2.0), new PlacePosition(pos3, targetDamage));
                        continue;
                    }
                }
            }
        }
        if (!posList.isEmpty()) {
            final String getCombo3 = this.placeCalculations.GetCombo();
            switch (getCombo3) {
                case "New-TargetBased": {
                    return posList.entrySet().stream().collect((Collector<? super Object, ?, TreeMap<K, PlacePosition>>)Collectors.toMap(entry -> this.target.getDistanceSq(entry.getValue().blockPos), (Function<? super Object, ?>)Map.Entry::getValue, (a, b) -> b, (Supplier<R>)TreeMap::new)).firstEntry().getValue();
                }
                case "New-SelfBased": {
                    return posList.entrySet().stream().collect((Collector<? super Object, ?, TreeMap<K, PlacePosition>>)Collectors.toMap(entry -> this.mc.player.getDistanceSq(entry.getValue().blockPos), (Function<? super Object, ?>)Map.Entry::getValue, (a, b) -> b, (Supplier<R>)TreeMap::new)).lastEntry().getValue();
                }
                case "Sync": {
                    this.syncPossesDamage = posList;
                    this.syncPossesDistance = posListDistance;
                    if (this.placeSyncCalc.GetCombo().equals("Autonomic")) {
                        if (!this.syncPossesDistance.lastEntry().getValue().getBlockPos().equals((Object)this.syncPossesDamage.lastEntry().getValue().getBlockPos())) {
                            return posList.lastEntry().getValue();
                        }
                    }
                    else if (!this.syncPossesDistance.firstEntry().getValue().getBlockPos().equals((Object)this.syncPossesDamage.lastEntry().getValue().getBlockPos())) {
                        return posList.lastEntry().getValue();
                    }
                    return this.syncPossesDamage.lastEntry().getValue();
                }
                case "HighestEnemyDamage":
                case "HighestSelfDistance": {
                    return posList.lastEntry().getValue();
                }
                case "LowestSelfDamage":
                case "LowestEnemyDistance": {
                    return posList.firstEntry().getValue();
                }
            }
        }
        if (this.placeFastCalc.GetSwitch() && this.fastCalcTimer.getTime((long)(1000.0f - this.placeFastCalcSpeed.GetSlider() * 10.0f))) {
            this.fastCalcTimer.setTime(0);
        }
        return null;
    }
    
    public ExplodePosition searchCrystal() {
        final TreeMap<Float, ExplodePosition> crystalList = new TreeMap<Float, ExplodePosition>();
        for (final Entity entity : this.mc.world.loadedEntityList) {
            if (!(entity instanceof EntityEnderCrystal)) {
                continue;
            }
            if (this.breakMotionPredict.GetSwitch()) {
                final Entity j = EntityUtil.getPredictedPosition((Entity)this.target, this.breakMotionPredictAmount.GetSlider());
                this.target.setEntityBoundingBox(j.getEntityBoundingBox());
            }
            if (this.chorusBB != null) {
                this.target.setEntityBoundingBox(this.chorusBB);
            }
            final float selfHealth = this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount();
            final float selfDamage = EntityUtil.calculateEntityDamage((EntityEnderCrystal)entity, (EntityPlayer)this.mc.player);
            final float targetDamage = EntityUtil.calculateEntityDamage((EntityEnderCrystal)entity, this.target);
            final float targetHealth = this.target.getHealth() + this.target.getAbsorptionAmount();
            float minimumDamageValue = this.explodeMinimumDamage.GetSlider();
            if (this.explodeAntiStuck.GetSwitch()) {
                int i = 0;
                for (final Map.Entry<Integer, Integer> entry : this.antiStuckHashMap.entrySet()) {
                    if (entry.getKey().equals(entity.entityId) && entry.getValue() > this.explodeAntiStuckThreshold.GetSlider()) {
                        i = 1;
                    }
                }
                if (i == 1) {
                    continue;
                }
            }
            if (entity.getDistanceSq(EntityUtil.getPlayerPos((EntityPlayer)this.mc.player)) > this.explodeRange.GetSlider() * this.explodeRange.GetSlider()) {
                continue;
            }
            if (BlockUtil.rayTraceCheckPos(new BlockPos(Math.floor(entity.posX), Math.floor(entity.posY), Math.floor(entity.posZ))) && this.mc.player.getDistanceSq(new BlockPos(Math.floor(entity.posX), Math.floor(entity.posY), Math.floor(entity.posZ))) > this.explodeWallRange.GetSlider() * this.explodeWallRange.GetSlider()) {
                continue;
            }
            if (BlockUtil.isPlayerSafe(this.target) && targetHealth < this.facePlaceHp.GetSlider()) {
                minimumDamageValue = 2.0f;
            }
            if (this.runDetectFacePlace.GetSwitch() && BlockUtil.isPlayerSafe(this.target) && RunDetect.Instance.gappledPreviouslySwordedPotentialRunnerList.contains(this.target)) {
                minimumDamageValue = 2.0f;
            }
            if (this.facePlaceSlowOnCrouch.GetSwitch() && this.mc.gameSettings.keyBindSneak.isKeyDown()) {
                minimumDamageValue = 2.0f;
            }
            if (targetDamage < minimumDamageValue && !this.explodeIgnoreMinimumDamageAndTakeHighestDamageValueWhenever.GetSwitch()) {
                continue;
            }
            if (selfDamage > this.explodeMaximumSelfDamage.GetSlider()) {
                continue;
            }
            if (this.explodeAntiSuicide.GetSwitch() && selfDamage > selfHealth) {
                continue;
            }
            crystalList.put(targetDamage, new ExplodePosition(entity, targetDamage));
        }
        if (!crystalList.isEmpty()) {
            return crystalList.lastEntry().getValue();
        }
        return null;
    }
    
    @RegisterListener
    public void onPacketReceive(final PacketEvent.PacketReceiveEvent event) {
        if (this.nullCheck() || !this.isEnabled()) {
            return;
        }
        if (event.getPacket() instanceof SPacketSpawnObject && ((SPacketSpawnObject)event.getPacket()).getType() == 51 && this.predict.GetSwitch() && this.target != null && this.mc.world.getEntityByID(((SPacketSpawnObject)event.getPacket()).getEntityID()) instanceof EntityEnderCrystal) {
            final CPacketUseEntity predict = new CPacketUseEntity();
            predict.entityId = ((SPacketSpawnObject)event.getPacket()).getEntityID();
            predict.action = CPacketUseEntity.Action.ATTACK;
            this.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
            this.mc.player.connection.sendPacket((Packet)predict);
            if (this.explodeInhibit.GetSwitch()) {
                this.inhibitCrystal.add(this.explodePosition.getEntity());
            }
            this.predictTimer.setTime(0);
        }
        if (event.getPacket() instanceof SPacketSoundEffect && this.predict.GetSwitch() && this.predictSetDead.GetSwitch()) {
            final SPacketSoundEffect packet = (SPacketSoundEffect)event.getPacket();
            try {
                if (packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                    final List<Entity> loadedEntityList = (List<Entity>)this.mc.world.loadedEntityList;
                    final SPacketSoundEffect sPacketSoundEffect;
                    loadedEntityList.stream().filter(entity -> entity instanceof EntityEnderCrystal && entity.getDistanceSq(sPacketSoundEffect.getX(), sPacketSoundEffect.getY(), sPacketSoundEffect.getZ()) < this.explodeRange.GetSlider() * this.explodeRange.GetSlider()).forEach(entity -> {
                        Objects.requireNonNull(this.mc.world.getEntityByID(entity.getEntityId())).setDead();
                        this.mc.world.removeEntityFromWorld(entity.entityId);
                        return;
                    });
                }
            }
            catch (Exception ex) {}
        }
        if (event.getPacket() instanceof SPacketExplosion && this.predict.GetSwitch() && this.predictSetDead.GetSwitch()) {
            try {
                final SPacketExplosion packet2 = (SPacketExplosion)event.getPacket();
                final SPacketExplosion sPacketExplosion;
                this.mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityEnderCrystal && entity.getDistanceSq(sPacketExplosion.getX(), sPacketExplosion.getY(), sPacketExplosion.getZ()) < this.explodeRange.GetSlider() * this.explodeRange.GetSlider()).forEach(entity -> {
                    Objects.requireNonNull(this.mc.world.getEntityByID(entity.getEntityId())).setDead();
                    this.mc.world.removeEntityFromWorld(entity.entityId);
                });
            }
            catch (Exception ex2) {}
        }
    }
    
    @RegisterListener
    public void onPacketSend(final PacketEvent.PacketSendEvent event) {
        if (this.nullCheck() || !this.isEnabled()) {
            return;
        }
        if (this.rotations.GetSwitch() && this.needsRotations && event.getPacket() instanceof CPacketPlayer) {
            ((CPacketPlayer)event.getPacket()).yaw = this.yaw;
            ((CPacketPlayer)event.getPacket()).pitch = this.pitch;
            this.needsRotations = false;
        }
        if (event.getPacket() instanceof CPacketUseEntity && this.predict.GetSwitch() && this.predictTimer.getTime((this.facePlaceSlowOnCrouch.GetSwitch() && this.mc.gameSettings.keyBindSneak.isKeyDown()) ? 500L : ((long)this.predictDelay.GetSlider()))) {
            final CPacketUseEntity packet = (CPacketUseEntity)event.getPacket();
            if (packet.getAction() == CPacketUseEntity.Action.ATTACK && packet.getEntityFromWorld((World)this.mc.world) instanceof EntityEnderCrystal) {
                if (this.predictSetDead.GetSwitch() && this.predictSetDeadMode.GetCombo().equals("Packet-Confirm")) {
                    Objects.requireNonNull(packet.getEntityFromWorld((World)this.mc.world)).setDead();
                }
                if (this.placePosition != null) {
                    this.placeCrystal(this.placePosition.getBlockPos());
                }
                this.predictTimer.setTime(0);
            }
        }
    }
    
    @Override
    public void onFrame(final float partialTicks) {
        if (this.target == null) {
            return;
        }
        final String getCombo = this.renderMode.GetCombo();
        switch (getCombo) {
            case "Static": {
                if (this.placePosition != null) {
                    RenderUtil.drawBoxESP(this.placePosition.getBlockPos(), this.placeBox.GetColor(), true, this.placeOutline.GetColor(), this.placeLineWidth.GetSlider(), this.placeOutline.GetSwitch(), this.placeBox.GetSwitch(), this.placeBox.GetColor().getAlpha(), true);
                    final double damage = EntityUtil.calculatePosDamage(this.placePosition.getBlockPos().getX() + 0.5, this.placePosition.getBlockPos().getY() + 1.0, this.placePosition.getBlockPos().getZ() + 0.5, (Entity)this.target);
                    if (this.placeText.GetSwitch()) {
                        RenderUtil.drawText(this.placePosition.getBlockPos(), ((Math.floor(damage) == damage) ? Integer.valueOf((int)damage) : String.format("%.1f", damage)) + "");
                    }
                    break;
                }
                break;
            }
            case "Fade": {
                for (final Map.Entry<BlockPos, Integer> entry : this.fadePosses.entrySet()) {
                    if (this.placePosition != null && this.placePosition.getBlockPos() != null) {
                        if (!this.placePosition.getBlockPos().equals((Object)entry.getKey())) {
                            this.fadePosses.put(entry.getKey(), (int)(entry.getValue() - this.fadeSpeed.GetSlider() / 200.0f));
                        }
                        else {
                            final double damage2 = EntityUtil.calculatePosDamage(this.placePosition.getBlockPos().getX() + 0.5, this.placePosition.getBlockPos().getY() + 1.0, this.placePosition.getBlockPos().getZ() + 0.5, (Entity)this.target);
                            if (this.placeText.GetSwitch()) {
                                RenderUtil.drawText(this.placePosition.getBlockPos(), ((Math.floor(damage2) == damage2) ? Integer.valueOf((int)damage2) : String.format("%.1f", damage2)) + "");
                            }
                        }
                    }
                    else {
                        this.fadePosses.put(entry.getKey(), (int)(entry.getValue() - this.fadeSpeed.GetSlider() / 200.0f));
                    }
                    if (entry.getValue() <= 20) {
                        this.fadePosses.remove(entry.getKey());
                        return;
                    }
                    try {
                        RenderUtil.drawBoxESP(entry.getKey(), new Color(this.placeBox.GetColor().getRed(), this.placeBox.GetColor().getGreen(), this.placeBox.GetColor().getBlue(), entry.getValue()), true, new Color(this.placeOutline.GetColor().getRed(), this.placeOutline.GetColor().getGreen(), this.placeOutline.GetColor().getBlue(), entry.getValue() * 2), this.placeLineWidth.GetSlider(), this.placeOutline.GetSwitch(), this.placeBox.GetSwitch(), entry.getValue(), true);
                    }
                    catch (Exception exception) {
                        Ruby.chatManager.sendRemovableMessage("Alpha parameter out of range (Choose a different Alpha)" + exception, 1);
                    }
                }
                break;
            }
            case "Shrink": {
                for (final Map.Entry<BlockPos, Integer> entry : this.fadePosses.entrySet()) {
                    AxisAlignedBB bb = this.mc.world.getBlockState((BlockPos)entry.getKey()).getSelectedBoundingBox((World)this.mc.world, (BlockPos)entry.getKey());
                    bb = bb.shrink(Math.max(Math.min(RenderUtil.normalize(entry.getValue(), 100.0f - this.shrinkSpeed.GetSlider() / 100.0f), 1.0), 0.0));
                    if (this.placePosition != null && this.placePosition.getBlockPos() != null) {
                        if (!this.placePosition.getBlockPos().equals((Object)entry.getKey())) {
                            this.fadePosses.put(entry.getKey(), (int)(entry.getValue() - Math.max(Math.min(RenderUtil.normalize(entry.getValue(), 100.0f - this.shrinkSpeed.GetSlider() / 200.0f), 1.0), 0.0)));
                        }
                        else {
                            this.fadePosses.put(entry.getKey(), 100);
                            final double damage3 = EntityUtil.calculatePosDamage(this.placePosition.getBlockPos().getX() + 0.5, this.placePosition.getBlockPos().getY() + 1.0, this.placePosition.getBlockPos().getZ() + 0.5, (Entity)this.target);
                            if (this.placeText.GetSwitch()) {
                                RenderUtil.drawText(this.placePosition.getBlockPos(), ((Math.floor(damage3) == damage3) ? Integer.valueOf((int)damage3) : String.format("%.1f", damage3)) + "");
                            }
                        }
                    }
                    else {
                        this.fadePosses.put(entry.getKey(), (int)(entry.getValue() - Math.max(Math.min(RenderUtil.normalize(entry.getValue(), 100.0f - this.shrinkSpeed.GetSlider() / 100.0f), 1.0), 0.0)));
                    }
                    if (entry.getValue() <= 0) {
                        this.fadePosses.remove(entry.getKey());
                        return;
                    }
                    if (this.placeBox.GetSwitch()) {
                        RenderUtil.drawBBBox(bb, this.placeBox.GetColor(), this.placeBox.GetColor().getAlpha());
                    }
                    if (!this.placeOutline.GetSwitch()) {
                        continue;
                    }
                    RenderUtil.drawBlockOutlineBB(bb, this.placeOutline.GetColor(), 1.0f);
                }
                break;
            }
            case "Moving": {
                if (this.bb == null || this.placePosition == null || this.placePosition.getBlockPos() == null) {
                    break;
                }
                final AxisAlignedBB cc = new AxisAlignedBB(this.placePosition.getBlockPos());
                if (this.bb.equals((Object)cc)) {
                    break;
                }
                this.bb = this.bb.offset((this.placePosition.getBlockPos().getX() - this.bb.minX) * (this.moveSpeed.GetSlider() / 1000.0f), (this.placePosition.getBlockPos().getY() - this.bb.minY) * (this.moveSpeed.GetSlider() / 1000.0f), (this.placePosition.getBlockPos().getZ() - this.bb.minZ) * (this.moveSpeed.GetSlider() / 1000.0f));
                if (this.placeBox.GetSwitch()) {
                    RenderUtil.drawBBBox(this.bb, this.placeBox.GetColor(), this.placeBox.GetColor().getAlpha());
                }
                if (this.placeOutline.GetSwitch()) {
                    RenderUtil.drawBlockOutlineBB(this.bb, this.placeOutline.GetColor(), 1.0f);
                    break;
                }
                break;
            }
        }
    }
    
    public void entityRotate(final Entity entity) {
        final float[] angle = BlockUtil.calcAngle(this.mc.player.getPositionEyes(this.mc.getRenderPartialTicks()), entity.getPositionVector());
        this.yaw = angle[0];
        this.pitch = angle[1];
        this.needsRotations = true;
    }
    
    public void posRotate(final BlockPos pos) {
        final float[] angle = BlockUtil.calcAngle(this.mc.player.getPositionEyes(this.mc.getRenderPartialTicks()), new Vec3d((double)(pos.getX() + 0.5f), (double)(pos.getY() - 0.5f), (double)(pos.getZ() + 0.5f)));
        this.yaw = angle[0];
        this.pitch = angle[1];
        this.needsRotations = true;
    }
    
    public static class ExplodePosition
    {
        Entity entity;
        float targetDamage;
        
        public ExplodePosition(final Entity entity, final float targetDamage) {
            this.entity = entity;
            this.targetDamage = targetDamage;
        }
        
        public Entity getEntity() {
            return this.entity;
        }
    }
    
    public static class PlacePosition
    {
        BlockPos blockPos;
        float targetDamage;
        
        public PlacePosition(final BlockPos blockPos, final float targetDamage) {
            this.blockPos = blockPos;
            this.targetDamage = targetDamage;
        }
        
        public BlockPos getBlockPos() {
            return this.blockPos;
        }
    }
}
