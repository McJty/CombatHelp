package mcjty.combathelp.properties;

import mcjty.combathelp.varia.Tools;
import mcjty.lib.tools.ChatTools;
import mcjty.lib.tools.ItemStackList;
import mcjty.lib.tools.ItemStackTools;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;

public class PlayerHotbarStore {
    private ItemStackList hotbar = ItemStackList.create(9);
    private ItemStack offhand = ItemStackTools.getEmptyStack();

    public void saveNBTData(NBTTagCompound compound) {
        if (ItemStackTools.isValid(offhand)) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            offhand.writeToNBT(tagCompound);
            compound.setTag("off", tagCompound);
        }

        for (int i = 0 ; i < hotbar.size() ; i++) {
            if (ItemStackTools.isValid(hotbar.get(i))) {
                NBTTagCompound tagCompound = new NBTTagCompound();
                hotbar.get(i).writeToNBT(tagCompound);
                compound.setTag("hot" + i, tagCompound);
            }
        }
    }

    public void loadNBTData(NBTTagCompound compound) {
        if (compound.hasKey("off")) {
            offhand = ItemStackTools.loadFromNBT(compound.getCompoundTag("off"));
        } else {
            offhand = ItemStackTools.getEmptyStack();
        }
        for (int i = 0 ; i < hotbar.size() ; i++) {
            if (compound.hasKey("hot" + i)) {
                hotbar.set(i, ItemStackTools.loadFromNBT(compound.getCompoundTag("hot" + i)));
            } else {
                hotbar.set(i, ItemStackTools.getEmptyStack());
            }
        }
    }

    public void remember(EntityPlayerMP player) {
        offhand = player.getHeldItemOffhand();
        for (int i = 0 ; i < hotbar.size() ; i++) {
            hotbar.set(i, player.inventory.getStackInSlot(i));
        }
        ChatTools.addChatMessage(player, new TextComponentString("Saved hotbar and offhand configuration"));
    }

    public void restore(EntityPlayerMP player) {
        if (ItemStackTools.isValid(offhand)) {
            int slotFor = Tools.getSlotFor(offhand, player, new boolean[0]);
            if (slotFor != -1) {
                ItemStack oldstack = player.getHeldItemOffhand();
                player.setHeldItem(EnumHand.OFF_HAND, player.inventory.getStackInSlot(slotFor));
                player.inventory.setInventorySlotContents(slotFor, oldstack);
            }
        }

        boolean[] locked = new boolean[hotbar.size()];
        for (int i = 0 ; i < locked.length ; i++) {
            locked[i] = false;
        }
        for (int i = 0 ; i < hotbar.size() ; i++) {
            if (ItemStackTools.isValid(hotbar.get(i))) {
                int slotFor = Tools.getSlotFor(hotbar.get(i), player, locked);
                if (slotFor != -1 && slotFor != i) {
                    ItemStack oldstack = player.getHeldItemOffhand();
                    player.setHeldItem(EnumHand.OFF_HAND, player.inventory.getStackInSlot(slotFor));
                    player.inventory.setInventorySlotContents(slotFor, oldstack);
                }
                locked[i] = true;
            }
        }

        player.openContainer.detectAndSendChanges();
    }
}
