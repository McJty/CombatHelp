package mcjty.combathelp;

import mcjty.combathelp.properties.PlayerInventoryStore;
import mcjty.combathelp.properties.PlayerProperties;
import mcjty.combathelp.properties.PropertiesDispatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ForgeEventHandlers {

    @SubscribeEvent
    public void onEntityConstructing(AttachCapabilitiesEvent.Entity event){
        if (event.getEntity() instanceof EntityPlayer) {
            if (!event.getEntity().hasCapability(PlayerProperties.PLAYER_INVENTORY_CAPABILITY, null)) {
                event.addCapability(new ResourceLocation(CombatHelp.MODID, "Properties"), new PropertiesDispatcher());
            }
        }
    }

    @SubscribeEvent
    public void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            // We need to copyFrom the capabilities
            if (event.getOriginal().hasCapability(PlayerProperties.PLAYER_INVENTORY_CAPABILITY, null)) {
                PlayerInventoryStore oldStore = event.getOriginal().getCapability(PlayerProperties.PLAYER_INVENTORY_CAPABILITY, null);
                PlayerInventoryStore newStore = PlayerProperties.getPlayerInventoryStore(event.getEntityPlayer());
                newStore.copyFrom(oldStore);
            }
        }
    }

}