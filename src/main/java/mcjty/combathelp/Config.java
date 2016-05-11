package mcjty.combathelp;


import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;

public class Config {
    public static String CATEGORY_COMBATHELP = "combathelp";

    public static Item shieldOptions[] = new Item[10];

    public static void init(Configuration cfg) {
        for (int i = 0 ; i < shieldOptions.length ; i++) {
            String itemName = cfg.get(CATEGORY_COMBATHELP, "shieldItem" + i,
                                      (i == 0) ? "tconstruct:battlesign" :
                                              (i == 1 ? Items.SHIELD.getRegistryName().toString()
                                                      : ""),
                                      "Shield item to consider as an option").getString();
            if (itemName != null && !itemName.isEmpty()) {
                Item item = Item.REGISTRY.getObject(new ResourceLocation(itemName));
                if (item != null) {
                    shieldOptions[i] = item;
                }
            }
        }
    }
}
