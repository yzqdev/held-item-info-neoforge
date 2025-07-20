package io.github.a5b84.helditeminfo.mixin;

import io.github.a5b84.helditeminfo.Appenders;
import io.github.a5b84.helditeminfo.ContainerContentAppender;
import io.github.a5b84.helditeminfo.HeldItemInfo;
import io.github.a5b84.helditeminfo.TooltipAppender;
import io.github.a5b84.helditeminfo.TooltipBuilder;
import io.github.a5b84.helditeminfo.TooltipLine;
import java.util.Collections;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Gui.class})
public abstract class InGameHudMixin {
   @Shadow
   @Final
   private Minecraft minecraft;
   @Shadow
   private int toolHighlightTimer;
   @Shadow
   private ItemStack lastToolHighlight;
   @Unique
   private List<TooltipLine> tooltip = Collections.emptyList();
   @Unique
   private int y;
   @Unique
   private ItemStack stackBeforeTick;
   @Unique
   private int maxWidth = -1;

   @Inject(
      method = {"renderSelectedItemName(Lnet/minecraft/client/gui/GuiGraphics;I)V"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/gui/Gui;getFont()Lnet/minecraft/client/gui/Font;"
)}
   )
   public void onBeforeRenderHeldItemTooltip(GuiGraphics context, int yShift, CallbackInfo ci) {
      this.y = context.guiHeight() - 50 - 9 - (int)(((float)HeldItemInfo.config.lineHeight() - HeldItemInfo.config.offsetPerExtraLine()) * (float)(this.tooltip.size() - 1)) - HeldItemInfo.config.verticalOffset();
      if (!this.minecraft.gameMode.canHurtPlayer()) {
         this.y += 14;
      }

      if (HeldItemInfo.config.showName() && this.tooltip.size() > 1) {
         this.y -= HeldItemInfo.config.itemNameSpacing();
      }

   }

   @Redirect(
      method = {"renderSelectedItemName(Lnet/minecraft/client/gui/GuiGraphics;I)V"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/gui/GuiGraphics;drawStringWithBackdrop(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;IIII)I"
)
   )
   private int drawTextProxy(GuiGraphics context, Font textRenderer, Component text, int _x, int _y, int width, int color) {
      int backgroundColor = this.minecraft.options.getBackgroundColor(0);
      if ((backgroundColor & -16777216) != 0) {
         if (this.maxWidth < 0) {
            for(TooltipLine line : this.tooltip) {
               if (line.width > this.maxWidth) {
                  this.maxWidth = line.width;
               }
            }
         }

         int scaledWidth = context.guiWidth();
         int height = HeldItemInfo.config.lineHeight() * this.tooltip.size();
         if (HeldItemInfo.config.showName() && this.tooltip.size() > 1) {
            height += HeldItemInfo.config.itemNameSpacing();
         }

         context.fill((scaledWidth - this.maxWidth) / 2 - 2, this.y - 2, (scaledWidth + this.maxWidth) / 2 + 2, this.y + height + 2, backgroundColor);
      }

      int lineHeight = HeldItemInfo.config.lineHeight();
      int i = 0;

      for(TooltipLine line : this.tooltip) {
         int x = (context.guiWidth() - line.width) / 2;
         context.drawString(textRenderer, line.text, x, this.y, color);
         this.y += lineHeight;
         if (i == 0 && HeldItemInfo.config.showName()) {
            this.y += HeldItemInfo.config.itemNameSpacing();
         }

         ++i;
      }

      return 0;
   }

   @Inject(
      method = {"tick()V"},
      at = {@At("HEAD")}
   )
   public void onBeforeTick(CallbackInfo ci) {
      this.stackBeforeTick = this.lastToolHighlight;
   }

   @Inject(
      method = {"tick()V"},
      at = {@At("RETURN")}
   )
   public void onAfterTick(CallbackInfo ci) {
      if (this.minecraft.player != null && this.lastToolHighlight != this.stackBeforeTick) {
         if (this.lastToolHighlight.isEmpty()) {
            this.tooltip = Collections.emptyList();
         } else {
            List<Component> newInfo = this.buildTooltip(this.lastToolHighlight);
            if (!TooltipLine.areEquivalent(this.tooltip, newInfo)) {
               this.tooltip = TooltipLine.from(newInfo);
               this.maxWidth = -1;
               this.toolHighlightTimer = (int)(20.0F * (HeldItemInfo.config.baseFadeDuration() + HeldItemInfo.config.fadeDurationPerExtraLine() * (float)(this.tooltip.size() - 1)));
            }
         }

      }
   }

   @Unique
   private List<Component> buildTooltip(ItemStack stack) {
      if (stack.isEmpty()) {
         return Collections.emptyList();
      } else {
         Item.TooltipContext tooltipContext = TooltipContext.of(this.minecraft.level);
         TooltipBuilder builder = new TooltipBuilder(stack, tooltipContext, HeldItemInfo.config.maxLines());
         if (HeldItemInfo.config.showName()) {
            MutableComponent stackName = Component.empty().append(stack.getHoverName()).withStyle(stack.getRarity().color());
            if (stack.has(DataComponents.CUSTOM_NAME)) {
               stackName.withStyle(ChatFormatting.ITALIC);
            }

            builder.append(stackName);
         }

         if (!HeldItemInfo.config.respectHideFlags() || !stack.has(DataComponents.HIDE_TOOLTIP)) {
            Item item = stack.getItem();
            if (item instanceof TooltipAppender) {
               TooltipAppender appender = (TooltipAppender)item;
               if (appender.heldItemInfo_shouldAppendTooltip()) {
                  appender.heldItemInfo_appendTooltip(builder);
               }
            }

            if (item instanceof BlockItem) {
               BlockItem blockItem = (BlockItem)item;
               Block var7 = blockItem.getBlock();
               if (var7 instanceof TooltipAppender) {
                  TooltipAppender appender = (TooltipAppender)var7;
                  if (appender.heldItemInfo_shouldAppendTooltip()) {
                     appender.heldItemInfo_appendTooltip(builder);
                  }
               }
            }

            if (HeldItemInfo.config.showMusicDiscDescription()) {
               Appenders.appendMusicDiscDescription(builder);
            }

            if (HeldItemInfo.config.showEnchantments()) {
               Appenders.appendEnchantments(builder);
            }

            if (HeldItemInfo.config.showContainerContent()) {
               ContainerContentAppender.appendContainerContent(builder);
            }

            if (HeldItemInfo.config.showLore()) {
               Appenders.appendLore(builder);
            }

            if (HeldItemInfo.config.showUnbreakable()) {
               Appenders.appendUnbreakable(builder);
            }
         }

         return builder.build();
      }
   }
}
