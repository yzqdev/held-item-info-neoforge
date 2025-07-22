package io.github.a5b84.helditeminfo.mixin.block;

import io.github.a5b84.helditeminfo.Appenders;
import io.github.a5b84.helditeminfo.HeldItemInfoClient;
import io.github.a5b84.helditeminfo.TooltipAppender;
import io.github.a5b84.helditeminfo.TooltipBuilder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.BrushableBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({BrushableBlock.class})
public abstract class BrushableBlockMixin implements TooltipAppender {
   public boolean heldItemInfo_shouldAppendTooltip() {
      return HeldItemInfoClient.config.showContainerContent();
   }

   public void heldItemInfo_appendTooltip(TooltipBuilder builder) {
      CustomData blockEntityData = (CustomData)builder.stack.get(DataComponents.BLOCK_ENTITY_DATA);
      if (blockEntityData != null) {
         Appenders.appendItem(builder, blockEntityData.getUnsafe().getCompound("item"));
      }

   }
}
