package mcjty.combathelp;

import mcjty.combathelp.network.PacketHandler;
import mcjty.combathelp.network.PacketWieldShield;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeyInputHandler {

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (KeyBindings.shieldHotSwap.isPressed()) {
            PacketHandler.INSTANCE.sendToServer(new PacketWieldShield());
        }
    }
}
