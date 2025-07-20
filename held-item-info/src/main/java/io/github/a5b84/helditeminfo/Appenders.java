package io.github.a5b84.helditeminfo;


import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.JukeboxPlayable;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

public final class Appenders {
   private static final Style LORE_STYLE;
   private static final Component UNBREAKABLE_TEXT;

   private Appenders() {
   }

   public static void appendMusicDiscDescription(TooltipBuilder builder) {
      JukeboxPlayable songComponent = (JukeboxPlayable)builder.stack.get(DataComponents.JUKEBOX_PLAYABLE);
      if (songComponent != null && (!HeldItemInfo.config.respectHideFlags() || songComponent.showInTooltip())) {
         songComponent.song().unwrap(builder.tooltipContext.registries()).ifPresent((entry) -> builder.append((Supplier)(() -> {
               MutableComponent description = ((JukeboxSong)entry.value()).description().copy();
               return ComponentUtils.mergeStyles(description, Style.EMPTY.withColor(TooltipBuilder.DEFAULT_COLOR));
            })));
      }

   }

   public static void appendEnchantments(TooltipBuilder builder) {
      appendEnchantments(builder, DataComponents.STORED_ENCHANTMENTS);
      appendEnchantments(builder, DataComponents.ENCHANTMENTS);
   }

   private static void appendEnchantments(TooltipBuilder builder, DataComponentType<ItemEnchantments> componentType) {
      ItemEnchantments enchantments = (ItemEnchantments)builder.stack.get(componentType);
      if (enchantments != null && !enchantments.isEmpty() && (!HeldItemInfo.config.respectHideFlags() || ( enchantments.showInTooltip ))) {
         HolderSet<Enchantment> tooltipOrder = enchantments. getTagOrEmpty(builder.tooltipContext.registries(), Registries.ENCHANTMENT, EnchantmentTags.TOOLTIP_ORDER);

         for(Holder<Enchantment> enchantment : tooltipOrder) {
            int level = enchantments.getLevel(enchantment);
            if (level > 0 && shouldShowEnchantment(enchantment)) {
               builder.append((Supplier)(() -> Enchantment.getFullname(enchantment, level)));
            }
         }

         for(Object2IntMap.Entry<Holder<Enchantment>> mapEntry : enchantments.entrySet()) {
            Holder<Enchantment> enchantment = mapEntry.getKey();
            if (!tooltipOrder.contains(enchantment) && shouldShowEnchantment(enchantment)) {
               builder.append((Supplier)(() -> {
                  int level = mapEntry.getIntValue();
                  return Enchantment.getFullname(enchantment, level);
               }));
            }
         }

      }
   }

   private static boolean shouldShowEnchantment(Holder<Enchantment> entry) {
      List<ResourceLocation> filters = HeldItemInfo.filteredEnchantments;
      if (filters.isEmpty()) {
         return true;
      } else {
         ResourceLocation id = (ResourceLocation)entry.unwrapKey().map(ResourceKey::location).orElse( null);
         return filters.contains(id) == HeldItemInfo.config.showOnlyFilteredEnchantments();
      }
   }

   public static void appendItem(TooltipBuilder builder, CompoundTag itemNbt) {
      if (itemNbt != null && !itemNbt.isEmpty()) {
         Optional<ItemStack> optionalStack = ItemStack.parse(builder.tooltipContext.registries(), itemNbt);
         ItemStack stack;
         if (!optionalStack.isEmpty() && !(stack = (ItemStack)optionalStack.get()).isEmpty()) {
            builder.append((Supplier)(() -> Component.translatable("container.shulkerBox.itemCount", new Object[]{stack.getHoverName(), stack.getCount()}).withStyle(TooltipBuilder.DEFAULT_COLOR)));
         }
      }
   }

   public static void appendLore(TooltipBuilder builder) {
      ItemLore loreComponent = (ItemLore)builder.stack.get(DataComponents.LORE);
      if (loreComponent != null) {
         int currentLoreLines = 0;

         for(Component line : loreComponent.lines()) {
            int maxLines = Math.min(HeldItemInfo.config.maxLoreLines() - currentLoreLines, builder.getRemainingLines());

            for(MutableComponent linePart : Util.wrapLines((FormattedText)line, maxLines)) {
               builder.append((Supplier)(() -> ComponentUtils.mergeStyles(linePart, LORE_STYLE)));
            }
         }
      }

   }

   public static void appendUnbreakable(TooltipBuilder builder) {
      Unbreakable unbreakableComponent =  builder.stack.get(DataComponents.UNBREAKABLE);
      if (unbreakableComponent != null && (!HeldItemInfo.config.respectHideFlags() || unbreakableComponent.showInTooltip())) {
         builder.append(UNBREAKABLE_TEXT);
      }

   }

   static {
      LORE_STYLE = Style.EMPTY.withColor(TooltipBuilder.DEFAULT_COLOR).withItalic(true);
      UNBREAKABLE_TEXT = Component.translatable("item.unbreakable").withStyle(TooltipBuilder.DEFAULT_COLOR);
   }
}
