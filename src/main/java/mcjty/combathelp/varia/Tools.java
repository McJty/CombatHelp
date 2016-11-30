package mcjty.combathelp.varia;

import mcjty.lib.tools.ItemStackTools;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;

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
}
