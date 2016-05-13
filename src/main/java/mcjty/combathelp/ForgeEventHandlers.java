package mcjty.combathelp;

import mcjty.combathelp.properties.PlayerProperties;
import mcjty.combathelp.properties.PropertiesDispatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
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


}