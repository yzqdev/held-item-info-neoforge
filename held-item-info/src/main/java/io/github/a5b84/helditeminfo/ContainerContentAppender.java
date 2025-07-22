package io.github.a5b84.helditeminfo;

import com.google.common.collect.Iterables;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.ShulkerBoxBlock;

public final class ContainerContentAppender {
   private ContainerContentAppender() {
   }

   public static void appendContainerContent(TooltipBuilder builder) {
      if (builder.stack.has(DataComponents.CONTAINER_LOOT)) {
         builder.append(ShulkerBoxBlock.UNKNOWN_CONTENTS);
      }

      for(ContainerEntry innerStack : getContainerEntries(builder.stack)) {
         builder.append((Supplier)(() -> Component.translatable("container.shulkerBox.itemCount", new Object[]{innerStack.getName(), innerStack.getCount()}).withStyle(TooltipBuilder.DEFAULT_COLOR)));
      }

   }

   private static Iterable<? extends ContainerEntry> getContainerEntries(ItemStack containerStack) {
      Iterable<ItemStack> stacks = Collections.emptyList();
      ItemContainerContents containerComponent = (ItemContainerContents)containerStack.get(DataComponents.CONTAINER);
      if (containerComponent != null) {
         stacks = containerComponent.nonEmptyItems();
      }

      BundleContents bundleContents = (BundleContents)containerStack.get(DataComponents.BUNDLE_CONTENTS);
      if (bundleContents != null) {
         stacks = Iterables.concat(stacks, bundleContents.items());
      }

      if (!HeldItemInfoClient.config.mergeSimilarContainerItems()) {
         return Iterables.transform(stacks, ItemStackContainerEntry::new);
      } else {
         Map<Component, MergedContainerEntry> entries = new LinkedHashMap();

         for(ItemStack stack : stacks) {
            entries.compute(stack.getHoverName(), (name, entry) -> {
               if (entry == null) {
                  entry = new MergedContainerEntry(name);
               }

               entry.count += stack.getCount();
               return entry;
            });
         }

         return entries.values();
      }
   }

   private abstract static class ContainerEntry {
      public abstract Component getName();

      public abstract int getCount();
   }

   private static class ItemStackContainerEntry extends ContainerEntry {
      private final ItemStack stack;

      public ItemStackContainerEntry(ItemStack stack) {
         this.stack = stack;
      }

      public Component getName() {
         return this.stack.getHoverName();
      }

      public int getCount() {
         return this.stack.getCount();
      }
   }

   private static class MergedContainerEntry extends ContainerEntry {
      private final Component name;
      private int count = 0;

      private MergedContainerEntry(Component name) {
         this.name = name;
      }

      public Component getName() {
         return this.name;
      }

      public int getCount() {
         return this.count;
      }
   }
}
