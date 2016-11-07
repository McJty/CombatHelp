package mcjty.combathelp.properties;

import mcjty.combathelp.varia.Tools;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;

public class PlayerHotbarStore {
    private ItemStack[] hotbar = new ItemStack[9];
    private ItemStack offhand;

    public void saveNBTData(NBTTagCompound compound) {
        if (offhand != null) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            offhand.writeToNBT(tagCompound);
            compound.setTag("off", tagCompound);
        }

        for (int i = 0 ; i < hotbar.length ; i++) {
            if (hotbar[i] != null) {
                NBTTagCompound tagCompound = new NBTTagCompound();
                hotbar[i].writeToNBT(tagCompound);
                compound.setTag("hot" + i, tagCompound);
            }
        }
    }

    public void loadNBTData(NBTTagCompound compound) {
        if (compound.hasKey("off")) {
            offhand = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("off"));
        } else {
            offhand = null;
        }
        for (int i = 0 ; i < hotbar.length ; i++) {
            if (compound.hasKey("hot" + i)) {
                hotbar[i] = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("hot" + i));
            } else {
                hotbar[i] = null;
            }
        }
    }

    public void remember(EntityPlayerMP player) {
        offhand = player.inventory.offHandInventory[0];
        System.arraycopy(player.inventory.mainInventory, 0, hotbar, 0, hotbar.length);
        player.addChatComponentMessage(new TextComponentString("Saved hotbar and offhand configuration"));
    }

    public void restore(EntityPlayerMP player) {
        if (offhand != null) {
            int slotFor = Tools.getSlotFor(offhand, player, new boolean[0]);
            if (slotFor != -1) {
                ItemStack oldstack = player.inventory.offHandInventory[0];
                player.inventory.offHandInventory[0] = player.inventory.getStackInSlot(slotFor);
                player.inventory.setInventorySlotContents(slotFor, oldstack);
            }
        }

        boolean[] locked = new boolean[hotbar.length];
        for (int i = 0 ; i < locked.length ; i++) {
            locked[i] = false;
        }
        for (int i = 0 ; i < hotbar.length ; i++) {
            if (hotbar[i] != null) {
                int slotFor = Tools.getSlotFor(hotbar[i], player, locked);
                if (slotFor != -1 && slotFor != i) {
                    ItemStack oldstack = player.inventory.mainInventory[i];
                    player.inventory.mainInventory[i] = player.inventory.getStackInSlot(slotFor);
                    player.inventory.setInventorySlotContents(slotFor, oldstack);
                }
                locked[i] = true;
            }
        }

        player.openContainer.detectAndSendChanges();
    }
}
