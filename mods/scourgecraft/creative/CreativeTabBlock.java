package mods.scourgecraft.creative;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.scourgecraft.ScourgeCraftCore;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabBlock extends CreativeTabs {

	public CreativeTabBlock(String label) {
		super(label);
	}
	
	@Override
	public ItemStack getIconItemStack()
    {
        return new ItemStack(Block.blockEmerald, 1, 1);
    }
	
	@SideOnly(Side.CLIENT)
	@Override
    public String getTranslatedTabLabel() {
		return "SG : Blocks";
	}
	
	@SideOnly(Side.CLIENT)
	@Override
    public void displayAllReleventItems(List par1List) {
		super.displayAllReleventItems(par1List);}

}