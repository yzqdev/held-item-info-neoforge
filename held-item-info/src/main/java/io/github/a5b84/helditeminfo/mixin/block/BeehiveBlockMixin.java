package io.github.a5b84.helditeminfo.mixin.block;

import io.github.a5b84.helditeminfo.HeldItemInfo;
import io.github.a5b84.helditeminfo.TooltipAppender;
import io.github.a5b84.helditeminfo.TooltipBuilder;
import java.util.List;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({BeehiveBlock.class})
public abstract class BeehiveBlockMixin implements TooltipAppender {
   public boolean heldItemInfo_shouldAppendTooltip() {
      return HeldItemInfo.config.showBeehiveContent();
   }

   public void heldItemInfo_appendTooltip(TooltipBuilder builder) {
      List<BeehiveBlockEntity.Occupant> beeData =  builder.stack.get(DataComponents.BEES);
      if (beeData != null && !beeData.isEmpty()) {
         builder.append(Component.translatable("container.shulkerBox.itemCount", new Object[]{Component.translatable("entity.minecraft.bee"), beeData.size()}).withStyle(TooltipBuilder.DEFAULT_COLOR));
      }

   }
}
