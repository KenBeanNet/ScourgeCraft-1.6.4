package mods.scourgecraft;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import mods.scourgecraft.network.packet.Packet1UserInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class PlayerTracker implements IPlayerTracker {


    @Override
    public void onPlayerChangedDimension(EntityPlayer player) {
    }

    @Override
    public void onPlayerRespawn(EntityPlayer player) {
    	ScourgeCraftCore.instance.playerEventListener.onPlayerRespawn(player);
    }

	@Override
	public void onPlayerLogin(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerLogout(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}
}