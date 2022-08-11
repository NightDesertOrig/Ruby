//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.mixins.render;

import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.model.*;
import net.minecraft.inventory.*;
import dev.zprestige.ruby.module.visual.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ LayerBipedArmor.class })
public abstract class MixinRenderArmor
{
    @Shadow
    protected abstract void setModelVisible(final ModelBiped p0);
    
    @Overwrite
    protected void setModelSlotVisible(final ModelBiped p_188359_1_, final EntityEquipmentSlot slotIn) {
        this.setModelVisible(p_188359_1_);
        if (NoRender.Instance.isEnabled()) {
            switch (slotIn) {
                case HEAD: {
                    p_188359_1_.bipedHead.showModel = !NoRender.Instance.armor.GetSwitch();
                    p_188359_1_.bipedHeadwear.showModel = !NoRender.Instance.armor.GetSwitch();
                    break;
                }
                case CHEST: {
                    p_188359_1_.bipedBody.showModel = !NoRender.Instance.armor.GetSwitch();
                    p_188359_1_.bipedRightArm.showModel = !NoRender.Instance.armor.GetSwitch();
                    p_188359_1_.bipedLeftArm.showModel = !NoRender.Instance.armor.GetSwitch();
                    break;
                }
                case LEGS: {
                    p_188359_1_.bipedBody.showModel = !NoRender.Instance.armor.GetSwitch();
                    p_188359_1_.bipedRightLeg.showModel = !NoRender.Instance.armor.GetSwitch();
                    p_188359_1_.bipedLeftLeg.showModel = !NoRender.Instance.armor.GetSwitch();
                    break;
                }
                case FEET: {
                    p_188359_1_.bipedRightLeg.showModel = !NoRender.Instance.armor.GetSwitch();
                    p_188359_1_.bipedLeftLeg.showModel = !NoRender.Instance.armor.GetSwitch();
                    break;
                }
            }
        }
    }
}
