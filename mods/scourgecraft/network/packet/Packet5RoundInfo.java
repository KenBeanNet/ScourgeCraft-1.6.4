package mods.scourgecraft.network.packet;

import mods.scourgecraft.ClientProxy;
import mods.scourgecraft.ScourgeCraftCore;
import mods.scourgecraft.network.ScourgePacket;
import net.minecraft.entity.player.EntityPlayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class Packet5RoundInfo extends ScourgePacket {

    private byte roundType;
   
    public Packet5RoundInfo(byte par1RoundType) {
    		this.roundType = par1RoundType;
    }

    public Packet5RoundInfo() { } // Be sure to always have the default constructor in your class, or the reflection code will fail!

    @Override
    protected void write(ByteArrayDataOutput out) {
    		out.writeByte(roundType);
    }

    @Override
    protected void read(ByteArrayDataInput in) {
    	roundType = in.readByte();
    }

    @Override
    protected void execute(EntityPlayer player, Side side) {
            if (side.isClient()) {
            	if (ScourgeCraftCore.instance.proxy instanceof ClientProxy)
            	{
            		ClientProxy proxy = (ClientProxy)ScourgeCraftCore.instance.proxy;
            		proxy.playerFactionHandler.setRoundType(roundType);
            	}
            } 
    }
}
