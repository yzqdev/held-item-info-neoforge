package io.github.a5b84.helditeminfo;


import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = HeldItemInfoClient.MOD_ID)
public class GameEvent {
    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        var dispatcher = event.getDispatcher();


        HeldItemInfoDebugCommand.register(dispatcher);
    }
}
