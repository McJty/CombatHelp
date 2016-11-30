package mcjty.combathelp.network;

import io.netty.buffer.ByteBuf;
import mcjty.combathelp.Config;
import mcjty.combathelp.varia.Tools;
import mcjty.lib.tools.ItemStackTools;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketWieldShield implements IMessage {

    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    public PacketWieldShield() {
    }

    public static class Handler implements IMessageHandler<PacketWieldShield, IMessage> {
        @Override
        public IMessage onMessage(PacketWieldShield message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketWieldShield message, MessageContext ctx) {
            EntityPlayerMP playerEntity = ctx.getServerHandler().playerEntity;
            ItemStack stack = playerEntity.getHeldItemOffhand();
            if (isUsefullOffhand(stack)) {
                // Already ok
                return;
            }

            // Find a shield
            for (Item shieldItem : Config.shieldOptions) {
                if (shieldItem != null) {
                    int slotFor = Tools.getSlotFor(new ItemStack(shieldItem, 1), playerEntity, new boolean[0]);
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
            int slotFor = Tools.getBlockingItem(playerEntity, 0);
            if (slotFor != -1) {
                ItemStack oldstack = playerEntity.getHeldItemOffhand();
                playerEntity.setHeldItem(EnumHand.OFF_HAND, playerEntity.inventory.getStackInSlot(slotFor));
                playerEntity.inventory.setInventorySlotContents(slotFor, oldstack);
                playerEntity.openContainer.detectAndSendChanges();
                return;
            }

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
