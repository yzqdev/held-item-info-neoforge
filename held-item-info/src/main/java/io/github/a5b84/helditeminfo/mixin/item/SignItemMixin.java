package io.github.a5b84.helditeminfo.mixin.item;

import com.mojang.serialization.DataResult;
import io.github.a5b84.helditeminfo.HeldItemInfo;
import io.github.a5b84.helditeminfo.TooltipAppender;
import io.github.a5b84.helditeminfo.TooltipBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.entity.SignText;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin({SignItem.class})
public abstract class SignItemMixin implements TooltipAppender {
    public boolean heldItemInfo_shouldAppendTooltip() {
        return HeldItemInfo.config.showSignText();
    }

    public void heldItemInfo_appendTooltip(TooltipBuilder builder) {
        CustomData blockEntityData = (CustomData) builder.stack.get(DataComponents.BLOCK_ENTITY_DATA);
        if (blockEntityData != null) {
            RegistryOps<Tag> dynamicOps = builder.tooltipContext.registries().createSerializationContext(NbtOps.INSTANCE);
            List<MutableComponent> frontLines = this.buildSide(blockEntityData, "front_text", dynamicOps);
            List<MutableComponent> backLines = this.buildSide(blockEntityData, "back_text", dynamicOps);
            if (!frontLines.isEmpty()) {
                builder.appendAll(frontLines);
                if (!backLines.isEmpty()) {
                    builder.append(Component.literal("-------").withStyle(TooltipBuilder.DEFAULT_COLOR));
                }
            }

            if (!backLines.isEmpty()) {
                builder.appendAll(backLines);
            }
        }

    }

    @Unique
    private List<MutableComponent> buildSide(CustomData blockEntityData, String sideKey, RegistryOps<Tag> dynamicOps) {
        ListTag messagesNbt = blockEntityData.getUnsafe().getCompound(sideKey).getList("messages", 8);
        if (messagesNbt.isEmpty()) {
            return Collections.emptyList();
        } else {
            DataResult var10000 = SignText.DIRECT_CODEC.parse(dynamicOps, blockEntityData.getUnsafe().getCompound(sideKey));


            Optional<SignText> signText = var10000.resultOrPartial((Consumer<String>) HeldItemInfo.LOGGER::error);
            if (signText.isPresent()) {
                Component[] messages = ((SignText) signText.get()).getMessages(Minecraft.getInstance().isTextFilteringEnabled());
                List<MutableComponent> lines = new ArrayList(messages.length);

                for (Component message : messages) {
                    if (message != null) {
                        String messageStr = message.getString();
                        if (!messageStr.isBlank()) {
                            lines.add(Component.literal(messageStr).withStyle(TooltipBuilder.DEFAULT_COLOR));
                        }
                    }
                }

                return lines;
            } else {
                return Collections.emptyList();
            }
        }
    }
}
