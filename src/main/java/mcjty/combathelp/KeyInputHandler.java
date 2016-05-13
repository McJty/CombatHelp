package mcjty.combathelp;

import mcjty.combathelp.network.PacketHandler;
import mcjty.combathelp.network.PacketHotbarHandler;
import mcjty.combathelp.network.PacketWieldShield;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class KeyInputHandler {

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (KeyBindings.shieldHotSwap.isPressed()) {
            PacketHandler.INSTANCE.sendToServer(new PacketWieldShield());
        } else if (KeyBindings.restoreHotbar1.isPressed()) {
            boolean control = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
            PacketHandler.INSTANCE.sendToServer(new PacketHotbarHandler(0, control));
        } else if (KeyBindings.restoreHotbar2.isPressed()) {
            boolean control = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
            PacketHandler.INSTANCE.sendToServer(new PacketHotbarHandler(1, control));
        } else if (KeyBindings.restoreHotbar3.isPressed()) {
            boolean control = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
            PacketHandler.INSTANCE.sendToServer(new PacketHotbarHandler(2, control));
        }
    }
}
