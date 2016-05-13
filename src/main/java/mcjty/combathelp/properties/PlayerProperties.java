package mcjty.combathelp.properties;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class PlayerProperties {

    @CapabilityInject(PlayerInventoryStore.class)
    public static Capability<PlayerInventoryStore> PLAYER_INVENTORY_CAPABILITY;

    public static PlayerInventoryStore getPlayerInventoryStore(EntityPlayer player) {
        return player.getCapability(PLAYER_INVENTORY_CAPABILITY, null);
    }


}
