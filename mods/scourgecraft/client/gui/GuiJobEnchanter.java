package mods.scourgecraft.client.gui;

import mods.scourgecraft.FactionController;
import mods.scourgecraft.JobController;
import mods.scourgecraft.ScourgeCraftCore;
import mods.scourgecraft.network.packet.Packet2RegisterFaction;
import mods.scourgecraft.network.packet.Packet8JobLevelUp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.network.PacketDispatcher;

public class GuiJobEnchanter extends GuiScreen {
	
	public final int xSizeOfTexture = 233;
	public final int ySizeOfTexture = 137;
	private EntityPlayer playerPar1;
	
	public GuiJobEnchanter(EntityPlayer player) {
		playerPar1 = player;
	}

	@Override
	public void drawScreen(int x, int y, float f) {
		drawDefaultBackground();
		
		GL11.glColor4f(1F, 1F, 1F, 1F);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceFileGui.factionSelectorBackgroundGui);
		
		int posX = (this.width - xSizeOfTexture) / 2;
		int posY = (this.height - ySizeOfTexture) / 2;
		
		drawTexturedModalRect(posX, posY, 0, 0, xSizeOfTexture, ySizeOfTexture);
		
		this.drawCenteredString(this.fontRenderer, "Job Enchanter", this.width / 2, posY + 8, 0xFFFFFF);
		this.drawCenteredString(this.fontRenderer, "Job", posX + 20, posY + 20, 0xFFFF00);
		this.drawCenteredString(this.fontRenderer, "Level", posX + 85, posY + 20, 0xFFFF00);
		this.drawCenteredString(this.fontRenderer, "Cost", posX + 150, posY + 20, 0xFFFF00);
		this.drawCenteredString(this.fontRenderer, "Up", posX + 200, posY + 20, 0xFFFF00);
		
		this.drawString(this.fontRenderer, "Builder", posX + 7, posY + 40, 0xFFFFFF);
		this.drawString(this.fontRenderer, Integer.toString(playerPar1.builderLevel), posX + 85 , posY + 40, 0xFFFFFF);
		this.drawString(this.fontRenderer, JobController.getNextLevelCost("Builder", playerPar1.builderLevel), posX + 146 , posY + 40, 0xFFFFFF);
		this.buttonList.add(new GuiButton(1, posX + 190, posY + 33, 20, 20, "+"));
		
		this.drawString(this.fontRenderer, "Warrior", posX + 7, posY + 60, 0xFFFFFF);
		this.drawString(this.fontRenderer, "0", posX + 85 , posY + 60, 0xFFFFFF);
		this.drawString(this.fontRenderer, JobController.getNextLevelCost("Warrior", playerPar1.warriorLevel), posX + 146 , posY + 60, 0xFFFFFF);
		this.buttonList.add(new GuiButton(2, posX + 190, posY + 53, 20, 20, "+"));
		
		
		this.drawString(this.fontRenderer, "Enchanter", posX + 7, posY + 80, 0xFFFFFF);
		this.drawString(this.fontRenderer, "0", posX + 85 , posY + 80, 0xFFFFFF);
		this.drawString(this.fontRenderer, JobController.getNextLevelCost("Enchanter", playerPar1.enchanterLevel), posX + 146 , posY + 80, 0xFFFFFF);
		this.buttonList.add(new GuiButton(3, posX + 190, posY + 73, 20, 20, "+"));
		
		
		super.drawScreen(x, y, f);
	}
	
	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}
	
	public void actionPerformed(GuiButton button)
	{
		if (playerPar1 == null)
			return;
		
		switch (button.id)
		{
			case 1: //Upgrade Builder
			{
				if (playerPar1.vitalityPoints >= JobController.getNextLevelCostByInt("Builder", playerPar1.builderLevel))
				{
					PacketDispatcher.sendPacketToServer(new Packet8JobLevelUp("Builder").makePacket());
				}
				else
					playerPar1.addChatMessage("Sorry you do not have enough Vitality to upgrade Builder to level " + playerPar1.builderLevel++);
				break;
			}
			case 2: //Upgrade Warrior
			{
				if (playerPar1.vitalityPoints >= JobController.getNextLevelCostByInt("Warrior", playerPar1.warriorLevel))
				{
					PacketDispatcher.sendPacketToServer(new Packet8JobLevelUp("Warrior").makePacket());
				}
				else
					playerPar1.addChatMessage("Sorry you do not have enough Vitality to upgrade Warrior to level " + playerPar1.warriorLevel++);
				break;
			}
			case 3: //Upgrade Enchanter
			{
				if (playerPar1.vitalityPoints >= JobController.getNextLevelCostByInt("Enchanter", playerPar1.enchanterLevel))
				{
					PacketDispatcher.sendPacketToServer(new Packet8JobLevelUp("Enchanter").makePacket());
				}
				else
					playerPar1.addChatMessage("Sorry you do not have enough Vitality to upgrade Enchanter to level " + playerPar1.enchanterLevel++);
				break;
			}
		}
	}
}

