//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.zprestige.ruby.mixins.gui;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.item.*;
import dev.zprestige.ruby.module.visual.*;
import org.spongepowered.asm.mixin.injection.*;
import dev.zprestige.ruby.*;
import java.awt.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;
import dev.zprestige.ruby.util.*;
import net.minecraft.nbt.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

@Mixin({ GuiScreen.class })
public class MixinGuiScreen extends Gui
{
    @Inject(method = { "renderToolTip" }, at = { @At("HEAD") }, cancellable = true)
    protected void renderToolTipHook(final ItemStack stack, final int x, final int y, final CallbackInfo info) {
        if (stack.getItem() instanceof ItemShulkerBox && ShulkerPeek.Instance.isEnabled()) {
            this.renderShulkerToolTip(stack, x, y);
            info.cancel();
        }
    }
    
    protected void renderShulkerToolTip(final ItemStack stack, final int x, final int y) {
        final NBTTagCompound tagCompound = stack.getTagCompound();
        if (tagCompound != null && tagCompound.hasKey("BlockEntityTag", 10)) {
            final NBTTagCompound blockEntityTag = tagCompound.getCompoundTag("BlockEntityTag");
            if (blockEntityTag.hasKey("Items", 9)) {
                GlStateManager.enableTexture2D();
                GlStateManager.disableLighting();
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                Ruby.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/shulker_box.png"));
                this.drawTexturedRect(x, y, 0, 16);
                this.drawTexturedRect(x, y + 16, 16, 54);
                this.drawTexturedRect(x, y + 16 + 54, 160, 8);
                GlStateManager.disableDepth();
                Ruby.mc.fontRenderer.drawStringWithShadow(stack.getDisplayName(), (float)(x + 8), (float)(y + 6), new Color(1.0f, 1.0f, 1.0f, 1.0f).getRGB());
                GlStateManager.enableDepth();
                RenderHelper.enableGUIStandardItemLighting();
                GlStateManager.enableRescaleNormal();
                GlStateManager.enableColorMaterial();
                GlStateManager.enableLighting();
                final NonNullList<ItemStack> nonNulls = (NonNullList<ItemStack>)NonNullList.withSize(27, (Object)ItemStack.EMPTY);
                ItemStackHelper.loadAllItems(blockEntityTag, (NonNullList)nonNulls);
                for (int i = 0; i < nonNulls.size(); ++i) {
                    final int iX = x + i % 9 * 18 + 8;
                    final int iY = y + i / 9 * 18 + 18;
                    final ItemStack itemStack = (ItemStack)nonNulls.get(i);
                    Ruby.mc.getRenderItem().zLevel = 501.0f;
                    RenderUtil.itemRender.renderItemAndEffectIntoGUI(itemStack, iX, iY);
                    RenderUtil.itemRender.renderItemOverlayIntoGUI(Ruby.mc.fontRenderer, itemStack, iX, iY, (String)null);
                    Ruby.mc.getRenderItem().zLevel = 0.0f;
                }
                GlStateManager.disableLighting();
                GlStateManager.disableBlend();
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            }
        }
    }
    
    protected void drawTexturedRect(final int x, final int y, final int textureY, final int height) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder BufferBuilder2 = tessellator.getBuffer();
        BufferBuilder2.begin(7, DefaultVertexFormats.POSITION_TEX);
        BufferBuilder2.pos((double)x, (double)(y + height), 500.0).tex(0.0, (double)((textureY + height) * 0.00390625f)).endVertex();
        BufferBuilder2.pos((double)(x + 176), (double)(y + height), 500.0).tex(0.6875, (double)((textureY + height) * 0.00390625f)).endVertex();
        BufferBuilder2.pos((double)(x + 176), (double)y, 500.0).tex(0.6875, (double)(textureY * 0.00390625f)).endVertex();
        BufferBuilder2.pos((double)x, (double)y, 500.0).tex(0.0, (double)(textureY * 0.00390625f)).endVertex();
        tessellator.draw();
    }
}
