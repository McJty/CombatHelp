package mcjty.combathelp.network;

import io.netty.buffer.ByteBuf;
import mcjty.combathelp.varia.Tools;
import net.minecraft.entity.player.EntityPlayerMP;
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
            Tools.wieldShield(playerEntity);
        }
    }


}
