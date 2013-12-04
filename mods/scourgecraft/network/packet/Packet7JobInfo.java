package mods.scourgecraft.network.packet;

import mods.scourgecraft.ClientProxy;
import mods.scourgecraft.ScourgeCraftCore;
import mods.scourgecraft.network.ScourgePacket;
import net.minecraft.entity.player.EntityPlayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class Packet7JobInfo extends ScourgePacket {

	private int vitalityPoints;
    private byte builderLevel;
    private byte warriorLevel;
    private byte enchanterLevel;
   
    public Packet7JobInfo(int par1VitalityPoints, byte par1BuilderLevel, byte par1WarriorLevel, byte par1EnchanterLevel) {
    		this.vitalityPoints = par1VitalityPoints;
    		this.builderLevel = par1BuilderLevel;
    		this.warriorLevel = par1WarriorLevel;
    		this.enchanterLevel = par1EnchanterLevel;
    }

    public Packet7JobInfo() { } // Be sure to always have the default constructor in your class, or the reflection code will fail!

    @Override
    protected void write(ByteArrayDataOutput out) {
    		out.writeInt(vitalityPoints);
    		out.writeByte(builderLevel);
    		out.writeByte(warriorLevel);
    		out.writeByte(enchanterLevel);
    }

    @Override
    protected void read(ByteArrayDataInput in) {
    	vitalityPoints = in.readInt();
    	builderLevel = in.readByte();
    	warriorLevel = in.readByte();
    	enchanterLevel = in.readByte();
    }

    @Override
    protected void execute(EntityPlayer player, Side side) {
            if (side.isClient()) {
            	player.vitalityPoints = vitalityPoints;
            	player.builderLevel = builderLevel;
            	player.warriorLevel = builderLevel;
            	player.enchanterLevel = builderLevel;
            } 
    }
}
