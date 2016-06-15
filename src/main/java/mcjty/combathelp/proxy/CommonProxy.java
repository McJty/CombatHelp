package mcjty.combathelp.proxy;

import mcjty.combathelp.CombatHelp;
import mcjty.combathelp.Config;
import mcjty.combathelp.ForgeEventHandlers;
import mcjty.combathelp.network.PacketHandler;
import mcjty.combathelp.properties.PlayerInventoryStore;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Level;

public abstract class CommonProxy {

    private Configuration mainConfig;

    public void preInit(FMLPreInitializationEvent e) {
        registerCapabilities();
        mainConfig = CombatHelp.config;
        readMainConfig();
        PacketHandler.registerMessages("combathelp");
    }

    private static void registerCapabilities(){
        CapabilityManager.INSTANCE.register(PlayerInventoryStore.class, new Capability.IStorage<PlayerInventoryStore>() {

            @Override
            public NBTBase writeNBT(Capability<PlayerInventoryStore> capability, PlayerInventoryStore instance, EnumFacing side) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void readNBT(Capability<PlayerInventoryStore> capability, PlayerInventoryStore instance, EnumFacing side, NBTBase nbt) {
                throw new UnsupportedOperationException();
            }

        }, () -> {
            throw new UnsupportedOperationException();
        });
    }

    private void readMainConfig() {
        Configuration cfg = mainConfig;
        try {
            cfg.load();
            cfg.addCustomCategoryComment(Config.CATEGORY_COMBATHELP, "Combat Help configuration");
            Config.init(cfg);
        } catch (Exception e1) {
            CombatHelp.logger.log(Level.ERROR, "Problem loading config file!", e1);
        } finally {
            if (mainConfig.hasChanged()) {
                mainConfig.save();
            }
        }
    }

    public void init(FMLInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(new ForgeEventHandlers());
    }

    public void postInit(FMLPostInitializationEvent e) {
        if (mainConfig.hasChanged()) {
            mainConfig.save();
        }
        mainConfig = null;
    }

}
