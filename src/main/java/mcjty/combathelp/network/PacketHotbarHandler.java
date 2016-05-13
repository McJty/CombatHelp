package mcjty.combathelp.network;

import io.netty.buffer.ByteBuf;
import mcjty.combathelp.properties.PlayerHotbarStore;
import mcjty.combathelp.properties.PlayerInventoryStore;
import mcjty.combathelp.properties.PlayerProperties;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketHotbarHandler implements IMessage {

    private int slot;
    private boolean remember;

    @Override
    public void fromBytes(ByteBuf buf) {
        slot = buf.readByte();
        remember = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(slot);
        buf.writeBoolean(remember);
    }

    public PacketHotbarHandler() {
    }

    public PacketHotbarHandler(int slot, boolean remember) {
        this.slot = slot;
        this.remember = remember;
    }

    public static class Handler implements IMessageHandler<PacketHotbarHandler, IMessage> {
        @Override
        public IMessage onMessage(PacketHotbarHandler message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketHotbarHandler message, MessageContext ctx) {
            EntityPlayerMP playerEntity = ctx.getServerHandler().playerEntity;
            PlayerInventoryStore inventoryStore = PlayerProperties.getPlayerInventoryStore(playerEntity);
            PlayerHotbarStore store = inventoryStore.getStore(message.slot);
            if (message.remember) {
                store.remember(playerEntity);
            } else {
                store.restore(playerEntity);
            }
        }
    }
}
