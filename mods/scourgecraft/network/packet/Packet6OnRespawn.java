package mods.scourgecraft.network.packet;

import mods.scourgecraft.ClientProxy;
import mods.scourgecraft.FactionController;
import mods.scourgecraft.ScourgeCraftCore;
import mods.scourgecraft.network.ScourgePacket;
import net.minecraft.entity.player.EntityPlayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class Packet6OnRespawn extends ScourgePacket 
{
   
    //This packet's primary role is after you have logged in, you need to be notify of information of all players around you.  
    public Packet6OnRespawn() {
    }

    @Override
    protected void write(ByteArrayDataOutput out) {
    	out.writeByte(0);
    }

    @Override
    protected void read(ByteArrayDataInput in) {
    	in.readByte();
    }

    @Override
    protected void execute(EntityPlayer player, Side side) {
            if (side.isClient()) {
            	ClientProxy proxy = (ClientProxy)ScourgeCraftCore.instance.proxy;
            	proxy.playerTickHandler.sentLoginUpdate = false;
            } 
    }
}
