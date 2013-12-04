package mods.scourgecraft;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;

import mods.scourgecraft.tick.ServerTickHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	public void registerHandlers()
    {
		TickRegistry.registerTickHandler(new ServerTickHandler(), Side.SERVER);
    }
	
	public File getMinecraftDir()
    {
        return new File(".");
    }
}
