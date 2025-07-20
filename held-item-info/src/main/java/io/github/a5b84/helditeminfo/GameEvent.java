package io.github.a5b84.helditeminfo;

import com.github.tartaricacid.touhoulittlemaid.command.RootCommand;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = HeldItemInfo.MOD_ID)
public class GameEvent {
    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        var dispatcher = event.getDispatcher();


        HeldItemInfoDebugCommand.register(dispatcher);
    }
}
