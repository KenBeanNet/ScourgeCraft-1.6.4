package mods.scourgecraft.client.gui;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.logging.Level;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.PacketDispatcher;
import mods.scourgecraft.FactionController;
import mods.scourgecraft.ScourgeCraftCore;
import mods.scourgecraft.network.packet.Packet2RegisterFaction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.network.packet.Packet250CustomPayload;

public class GuiFactionSelector extends GuiScreen {
	
	public final int xSizeOfTexture = 233;
	public final int ySizeOfTexture = 137;
	private EntityPlayer playerPar1;
	
	public GuiFactionSelector(EntityPlayer player) {
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
		

		//if (playerPar1.factionId != 0)
		{
			this.drawCenteredString(this.fontRenderer, "Round Statistics : " + ScourgeCraftCore.instance.factionController.getRoundId(), this.width / 2, posY + 10, 0xFFFFFF);
			this.drawString(this.fontRenderer, "Total Players", posX + 7, posY + 25, 0xFFFFFF);
			this.drawString(this.fontRenderer, "Total Kills", posX + 7, posY + 50, 0xFFFFFF);
			this.drawString(this.fontRenderer, "Total Deaths", posX + 7, posY + 75, 0xFFFFFF);
			for (int i = 0; i < FactionController.FACTION_COUNT; i++)
			{
				int color = FactionController.getColorByFactionId((byte)(i+1));
				this.drawString(this.fontRenderer, Integer.toString(ScourgeCraftCore.instance.factionController.getPlayerCount()[i]), posX + 85 + (i * 30), posY + 25, color);
				this.drawString(this.fontRenderer, Integer.toString(ScourgeCraftCore.instance.factionController.getKillCount()[i]), posX + 85 + (i * 30), posY + 50, color);
				this.drawString(this.fontRenderer, Integer.toString(ScourgeCraftCore.instance.factionController.getDeathCount()[i]), posX + 85 + (i * 30), posY + 75, color);
			}
		}
		
		super.drawScreen(x, y, f);
	}
	
	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}
	
	public void initGui()
	{
		int posX = (this.width - xSizeOfTexture) / 2;
		int posY = (this.height - ySizeOfTexture) / 2;
		
		if (playerPar1.factionId == 0)
		{
			this.buttonList.add(new GuiButton(1, posX + 10, posY + 100, 45, 20, "One"));
			this.buttonList.add(new GuiButton(2, posX + 65, posY + 100, 45, 20, "Two"));
			this.buttonList.add(new GuiButton(3, posX + 120, posY + 100, 45, 20, "Three"));
			this.buttonList.add(new GuiButton(4, posX + 175, posY + 100, 45, 20, "Four"));
		}
	}
	
	public void actionPerformed(GuiButton button)
	{
		if (playerPar1 == null)
			return;
		
		if (playerPar1.factionId == 0)
		{
			PacketDispatcher.sendPacketToServer(new Packet2RegisterFaction((byte)button.id).makePacket());
			this.onGuiClosed();
		}
	}
}
