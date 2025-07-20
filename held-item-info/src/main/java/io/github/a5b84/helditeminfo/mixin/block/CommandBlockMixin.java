package io.github.a5b84.helditeminfo.mixin.block;

import io.github.a5b84.helditeminfo.HeldItemInfo;
import io.github.a5b84.helditeminfo.TooltipAppender;
import io.github.a5b84.helditeminfo.TooltipBuilder;
import io.github.a5b84.helditeminfo.Util;
import java.util.function.Supplier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.CommandBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({CommandBlock.class})
public abstract class CommandBlockMixin implements TooltipAppender {
   public boolean heldItemInfo_shouldAppendTooltip() {
      return HeldItemInfo.config.showCommandBlockInfo();
   }

   public void heldItemInfo_appendTooltip(TooltipBuilder builder) {
      CustomData blockEntityData = (CustomData)builder.stack.get(DataComponents.BLOCK_ENTITY_DATA);
      if (blockEntityData != null) {
         String command = blockEntityData.getUnsafe().getString("Command");
         if (command != null) {
            command = command.trim();
            if (!command.isEmpty()) {
               int maxLines = Math.min(HeldItemInfo.config.maxCommandLines(), builder.getRemainingLines());

               for(MutableComponent text : Util.wrapLines(command, maxLines)) {
                  builder.append((Supplier)(() -> text.withStyle(TooltipBuilder.DEFAULT_COLOR)));
               }

            }
         }
      }
   }
}
