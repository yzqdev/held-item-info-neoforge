package io.github.a5b84.helditeminfo;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag.Default;

public interface GenericTooltipAppender extends TooltipAppender {
   default void heldItemInfo_appendTooltip(TooltipBuilder builder) {
      Item item = builder.stack.getItem();
      List<Component> tooltip = new ArrayList();
      item.appendHoverText(builder.stack, builder.tooltipContext, tooltip, Default.NORMAL);
      List<Component> var4 = this.heldItemInfo_postProcess(builder, tooltip);
      builder.appendAll(var4);
   }

   default List<Component> heldItemInfo_postProcess(TooltipBuilder builder, List<Component> tooltip) {
      return tooltip;
   }
}
