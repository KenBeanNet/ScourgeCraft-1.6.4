package mods.scourgecraft.blocks;

import java.util.Random;

import mods.scourgecraft.ScourgeCraftCore;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockJobEnchanter extends Block {

	public BlockJobEnchanter(int par1) {
        super(par1, Material.iron);
	}
	
	/**
	* When this method is called, your block should register all the icons it needs with the given IconRegister. This
	* * is the only chance you get to register icons.
	*/
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.blockIcon = par1IconRegister.registerIcon(ScourgeCraftCore.modid + ":" + "jobEnchanter");
	}
	
	/**
	* Called upon block activation (right click on the block.)
	*/
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
	    if (par1World.isRemote)
	    {
	        return true;
	    }
	    else
	    {
	    	TileEntity te = par1World.getBlockTileEntity(par2, par3, par4);
	                
	    	par5EntityPlayer.openGui(ScourgeCraftCore.instance, 1, par1World, (int)par5EntityPlayer.posX, (int)par5EntityPlayer.posY, (int)par5EntityPlayer.posZ);
	            
	        return true;
	    }
	}
	
	@Override
	public int quantityDropped(Random par1Random)
    {
        return 0;
    }
	
	@Override
	public boolean canDropFromExplosion(Explosion par1Explosion)
	{
		return false;
	}
	
	public int getMobilityFlag()
    {
		return 2; //Do not allow to be moved by Pistons or such.
    }
}