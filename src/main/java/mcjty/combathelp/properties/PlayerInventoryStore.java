package mcjty.combathelp.properties;

import net.minecraft.nbt.NBTTagCompound;

public class PlayerInventoryStore {

    private PlayerHotbarStore store[] = new PlayerHotbarStore[3];

    public void copyFrom(PlayerInventoryStore source) {
        store[0] = source.store[0];
        store[1] = source.store[1];
        store[2] = source.store[2];
    }

    public PlayerInventoryStore() {
        for (int i = 0 ; i < store.length ; i++) {
            store[i] = new PlayerHotbarStore();
        }
    }

    public void saveNBTData(NBTTagCompound compound) {
        for (int i = 0 ; i < store.length ; i++) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            store[i].saveNBTData(tagCompound);
            compound.setTag("store" + i, tagCompound);
        }
    }

    public void loadNBTData(NBTTagCompound compound) {
        for (int i = 0 ; i < store.length ; i++) {
            if (compound.hasKey("store" + i)) {
                store[i].loadNBTData(compound.getCompoundTag("store" + i));
            }
        }
    }

    public PlayerHotbarStore getStore(int index) {
        return store[index];
    }
}
