package mcjty.combathelp.varia;

import mcjty.combathelp.Config;
import mcjty.lib.tools.ItemStackTools;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class Tools {

    public static int getBlockingItem(EntityPlayerMP player, int startfrom) {
        for (int i = startfrom; i < 36; ++i) {
            ItemStack stack = player.inventory.getStackInSlot(i);
            if (ItemStackTools.isValid(stack) && stack.getItemUseAction() == EnumAction.BLOCK) {
                return i;
            }
        }
        return -1;
    }

    public static int getSlotFor(ItemStack stack, EntityPlayerMP player, boolean[] locked) {
        for (int i = 0; i < 36; ++i) {
            if (i >= locked.length || !locked[i]) {
                ItemStack invstack = player.inventory.getStackInSlot(i);
                if (ItemStackTools.isValid(invstack) && stackEqualExact(stack, invstack)) {
                    return i;
                }
            }
        }
        for (int i = 0; i < 36; ++i) {
            if (i >= locked.length || !locked[i]) {
                ItemStack invstack = player.inventory.getStackInSlot(i);
                if (ItemStackTools.isValid(invstack) && stackEqualExactNoNBT(stack, invstack)) {
                    return i;
                }
            }
        }
        return -1;
    }

    private static boolean stackEqualExact(ItemStack stack1, ItemStack stack2) {
        return stack1.getItem() == stack2.getItem() && (!stack1.getHasSubtypes() || stack1.getMetadata() == stack2.getMetadata()) && ItemStack.areItemStackTagsEqual(stack1, stack2);
    }

    private static boolean stackEqualExactNoNBT(ItemStack stack1, ItemStack stack2) {
        return stack1.getItem() == stack2.getItem() && (!stack1.getHasSubtypes() || stack1.getMetadata() == stack2.getMetadata());
    }

    public static void wieldShield(EntityPlayerMP playerEntity) {
        ItemStack stack = playerEntity.getHeldItemOffhand();
        if (isUsefullOffhand(stack)) {
            // Already ok
            return;
        }

        // Find a shield
        for (Item shieldItem : Config.shieldOptions) {
            if (shieldItem != null) {
                int slotFor = getSlotFor(new ItemStack(shieldItem, 1), playerEntity, new boolean[0]);
                if (slotFor != -1) {
                    ItemStack oldstack = playerEntity.getHeldItemOffhand();
                    playerEntity.setHeldItem(EnumHand.OFF_HAND, playerEntity.inventory.getStackInSlot(slotFor));
                    playerEntity.inventory.setInventorySlotContents(slotFor, oldstack);
                    playerEntity.openContainer.detectAndSendChanges();
                    return;
                }
            }
        }
        // If we didn't find a shield like that we try to find an item that can block
        int slotFor = getBlockingItem(playerEntity, 0);
        if (slotFor != -1) {
            ItemStack oldstack = playerEntity.getHeldItemOffhand();
            playerEntity.setHeldItem(EnumHand.OFF_HAND, playerEntity.inventory.getStackInSlot(slotFor));
            playerEntity.inventory.setInventorySlotContents(slotFor, oldstack);
            playerEntity.openContainer.detectAndSendChanges();
            return;
        }
    }

    /// Check if the given item is one of the items in the config
    private static boolean isUsefullOffhand(ItemStack stack) {
        if (ItemStackTools.isEmpty(stack)) {
            return false;
        }

        if (stack.getItemUseAction() == EnumAction.BLOCK) {
            return true;
        }
        return false;
//
//        for (Item shieldItem : Config.shieldOptions) {
//            if (shieldItem != null) {
//                if (shieldItem == stack.getItem()) {
//                    return true;
//                }
//            }
//        }
//        return false;
    }
}
