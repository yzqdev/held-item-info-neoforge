package io.github.a5b84.helditeminfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class TooltipBuilder {
   public static final ChatFormatting DEFAULT_COLOR;
   public final ItemStack stack;
   public final Item.TooltipContext tooltipContext;
   private final int maxSize;
   private final List<Component> lines;
   private int realSize = 0;

   public TooltipBuilder(ItemStack stack, Item.TooltipContext tooltipContext, int maxSize) {
      this.stack = stack;
      this.tooltipContext = tooltipContext;
      this.maxSize = maxSize;
      this.lines = new ArrayList(maxSize);
   }

   public boolean canAdd() {
      return this.lines.size() < this.maxSize;
   }

   public int getRemainingLines() {
      return this.maxSize - this.lines.size();
   }

   public void append(Component text) {
      ++this.realSize;
      if (this.canAdd()) {
         this.lines.add(text);
      }

   }

   public void append(Supplier<Component> textSupplier) {
      ++this.realSize;
      if (this.canAdd()) {
         this.lines.add((Component)textSupplier.get());
      }

   }

   public void appendAll(List<? extends Component> newLines) {
      this.realSize += newLines.size();
      if (this.canAdd()) {
         for(Component line : newLines) {
            this.lines.add(line);
            if (!this.canAdd()) {
               break;
            }
         }
      }

   }

   public List<Component> build() {
      if (this.realSize > this.maxSize && HeldItemInfo.config.showHiddenLinesCount()) {
         Component moreText = Component.translatable("container.shulkerBox.more", new Object[]{this.realSize - this.maxSize + 1}).withStyle(DEFAULT_COLOR, ChatFormatting.ITALIC);
         this.lines.set(this.lines.size() - 1, moreText);
      }

      return this.lines;
   }

   static {
      DEFAULT_COLOR = ChatFormatting.GRAY;
   }
}
