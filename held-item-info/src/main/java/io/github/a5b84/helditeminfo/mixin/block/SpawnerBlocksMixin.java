package io.github.a5b84.helditeminfo.mixin.block;

import io.github.a5b84.helditeminfo.HeldItemInfo;
import io.github.a5b84.helditeminfo.TooltipAppender;
import io.github.a5b84.helditeminfo.TooltipBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Spawner;
import net.minecraft.world.level.block.SpawnerBlock;
import net.minecraft.world.level.block.TrialSpawnerBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({SpawnerBlock.class, TrialSpawnerBlock.class})
public class SpawnerBlocksMixin implements TooltipAppender {
   public boolean heldItemInfo_shouldAppendTooltip() {
      return HeldItemInfo.config.showSpawnerEntity();
   }

   public void heldItemInfo_appendTooltip(TooltipBuilder builder) {

      String spawnDataKey =((Object) this instanceof TrialSpawnerBlock)? "spawn_data" : "SpawnData";
      Component text = Spawner.getSpawnEntityDisplayName(builder.stack, spawnDataKey);
      if (text != null) {
         builder.append(text);
      }

   }
}
