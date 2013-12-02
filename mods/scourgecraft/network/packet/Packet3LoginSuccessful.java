package mods.scourgecraft.network.packet;

import mods.scourgecraft.ScourgeCraftCore;
import mods.scourgecraft.network.ScourgePacket;
import net.minecraft.entity.player.EntityPlayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public class Packet3LoginSuccessful extends ScourgePacket {

	private String entityName;
    private byte factionId;
   
    //This packet's primary role is after you have logged in, you need to be notify of information of all players around you.  
    public Packet3LoginSuccessful(String par1EntityName) {
    		this.entityName = par1EntityName;
    }

    public Packet3LoginSuccessful() { } // Be sure to always have the default constructor in your class, or the reflection code will fail!

    @Override
    protected void write(ByteArrayDataOutput out) {
    		out.writeUTF(entityName);
    }

    @Override
    protected void read(ByteArrayDataInput in) {
    	entityName = in.readUTF();
    }

    @Override
    protected void execute(EntityPlayer player, Side side) {
            if (side.isServer()) {
                    EntityPlayer remotePlayer = player.worldObj.getPlayerEntityByName(entityName);
                    if (remotePlayer != null)
                    {
            			for (int i = 0; i < player.worldObj.playerEntities.size(); i++)
            			{
            				EntityPlayer newEp = (EntityPlayer)player.worldObj.playerEntities.get(i);
            				PacketDispatcher.sendPacketToPlayer(new Packet1UserInfo(newEp.username, newEp.factionId).makePacket(), (Player)player);
            				PacketDispatcher.sendPacketToPlayer(new Packet1UserInfo(player.username, player.factionId).makePacket(), (Player)newEp);
            			}
                    }
    				PacketDispatcher.sendPacketToPlayer(new Packet5RoundInfo(ScourgeCraftCore.instance.factionController.getRoundType()).makePacket(), (Player)player);
            } 
    }
}

