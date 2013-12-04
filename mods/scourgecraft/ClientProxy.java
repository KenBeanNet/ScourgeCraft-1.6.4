package mods.scourgecraft;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import mods.scourgecraft.tick.PlayerFactionHandler;
import mods.scourgecraft.tick.PlayerTickHandler;
import mods.scourgecraft.tick.ServerTickHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {

	public static PlayerFactionHandler playerFactionHandler = new PlayerFactionHandler();
	public static PlayerTickHandler playerTickHandler = new PlayerTickHandler();
	@Override
    public void registerHandlers()
    {
            TickRegistry.registerTickHandler(playerTickHandler, Side.CLIENT);
            TickRegistry.registerTickHandler(playerFactionHandler, Side.CLIENT);
            super.registerHandlers();
    }
	
	public File getMinecraftDir()
    {
        return Minecraft.getMinecraft().mcDataDir;
    }
}