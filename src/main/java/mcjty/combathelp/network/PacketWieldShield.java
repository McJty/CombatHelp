package mcjty.combathelp.network;

import io.netty.buffer.ByteBuf;
import mcjty.combathelp.Config;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
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
            ItemStack stack = playerEntity.inventory.offHandInventory[0];
            if (stack != null && stack.getItem() instanceof ItemShield) {
                // Already ok
                return;
            }
            // Find a shield
            for (Item shieldItem : Config.shieldOptions) {
                if (shieldItem != null) {
                    int slotFor = getSlotFor(new ItemStack(shieldItem, 1), playerEntity);
                    if (slotFor != -1) {
                        ItemStack oldstack = playerEntity.inventory.offHandInventory[0];
                        playerEntity.inventory.offHandInventory[0] = playerEntity.inventory.getStackInSlot(slotFor);
                        playerEntity.inventory.setInventorySlotContents(slotFor, oldstack);
                        playerEntity.openContainer.detectAndSendChanges();
                        return;
                    }
                }
            }
        }
    }

    private static int getSlotFor(ItemStack stack, EntityPlayerMP player) {
        for (int i = 0; i < player.inventory.mainInventory.length; ++i) {
            if (player.inventory.mainInventory[i] != null && stackEqualExact(stack, player.inventory.mainInventory[i])) {
                return i;
            }
        }

        return -1;
    }

    private static boolean stackEqualExact(ItemStack stack1, ItemStack stack2) {
        return stack1.getItem() == stack2.getItem() && (!stack1.getHasSubtypes() || stack1.getMetadata() == stack2.getMetadata()) && ItemStack.areItemStackTagsEqual(stack1, stack2);
    }


}
