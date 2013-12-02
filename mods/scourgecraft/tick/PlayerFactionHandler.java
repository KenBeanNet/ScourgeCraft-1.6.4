package mods.scourgecraft.tick;

import java.util.Date;
import java.util.EnumSet;

import org.lwjgl.opengl.GL11;

import mods.scourgecraft.ScourgeCraftCore;
import mods.scourgecraft.client.gui.ResourceFileGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class PlayerFactionHandler  implements ITickHandler {

	private int ticks = 0;
	private byte roundType;
	private final Minecraft mc;
	private int secondsLeftInRound = 0;
	private ResourceLocation roundImage;

	public PlayerFactionHandler() {
		mc = Minecraft.getMinecraft();
	}

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {

	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
	// setup render
	if (mc != null)
	{
		if (mc.inGameHasFocus)
		{
			ticks++;
			
			ScaledResolution res = new ScaledResolution(this.mc.gameSettings,
			this.mc.displayWidth, this.mc.displayHeight);
			FontRenderer fontRender = mc.fontRenderer;
			int width = res.getScaledWidth();
			int height = res.getScaledHeight();
			int color = 0xFFFFFF;
			mc.entityRenderer.setupOverlayRendering();

			// draw
			String text = "ScourgeCraft";
			fontRender.drawStringWithShadow(text, (width / 2) - (fontRender.getStringWidth(text) / 2) , 2, color);
			
			//Round Information
			if (ticks % 20 == 0)
				secondsLeftInRound();
			
			String displayText = "";
			if (secondsLeftInRound > 3600)
				displayText = "> 1 Hour";
			else if (secondsLeftInRound <= 3600 && secondsLeftInRound >= 120)
				displayText = (secondsLeftInRound / 60) + " Minutes";
			else if (secondsLeftInRound <= 3600 && secondsLeftInRound >= 60)
				displayText = (secondsLeftInRound / 60) + " Minute";
			else
				displayText = secondsLeftInRound + " Seconds";
			
			if (secondsLeftInRound > 0)
			{
				GuiIngame gui = mc.ingameGUI;
			
				int[] factionGuiWidth = ScourgeCraftCore.instance.factionController.getGuiWidthLength(200);
				//Balance of Power Bar
				int guiTotalWidthStart = 0;
				int guiTotalWidthEnd = factionGuiWidth[0];
				gui.drawRect(width / 2 - 100 + guiTotalWidthStart, 15, width / 2 - 100 + guiTotalWidthEnd, 25, 0xaFFF0000);
				String percentText = (int)(((float)factionGuiWidth[0] / (float)200) * 100) + "%";
				fontRender.drawStringWithShadow(percentText, width / 2 - 100 + guiTotalWidthStart + 2, 16, color);
				guiTotalWidthStart = guiTotalWidthEnd;
				guiTotalWidthEnd += factionGuiWidth[1];
				gui.drawRect(width / 2 - 100 + guiTotalWidthStart, 15, width / 2 - 100 + guiTotalWidthEnd, 25, 0xaF0000FF);
				percentText = (int)(((float)factionGuiWidth[1] / (float)200) * 100) + "%";
				fontRender.drawStringWithShadow(percentText, width / 2 - 100 + guiTotalWidthStart + 2, 16, color);
				guiTotalWidthStart = guiTotalWidthEnd;
				guiTotalWidthEnd += factionGuiWidth[2];
				gui.drawRect(width / 2 - 100 + guiTotalWidthStart, 15, width / 2 - 100 + guiTotalWidthEnd, 25, 0xaF009933);
				percentText = (int)(((float)factionGuiWidth[2] / (float)200) * 100) + "%";
				fontRender.drawStringWithShadow(percentText, width / 2 - 100 + guiTotalWidthStart + 2, 16, color);
				guiTotalWidthStart = guiTotalWidthEnd;
				guiTotalWidthEnd += factionGuiWidth[3];
				gui.drawRect(width / 2 - 100 + guiTotalWidthStart, 15, width / 2 - 100 + guiTotalWidthEnd, 25, 0xaFFFFF00);
				percentText = (int)(((float)factionGuiWidth[3] / (float)200) * 100) + "%";
				fontRender.drawStringWithShadow(percentText, width / 2 - 100 + guiTotalWidthStart + 2, 16, color);
			
			
				fontRender.drawStringWithShadow(displayText, width / 2 - 100 + guiTotalWidthEnd + 20, 28, color);
				
				if (roundType == 0)	
					Minecraft.getMinecraft().renderEngine.bindTexture(ResourceFileGui.factionRoundWarmup);
				else if (roundType == 1)
					Minecraft.getMinecraft().renderEngine.bindTexture(ResourceFileGui.factionRoundPrep);
				else if (roundType == 2)
					Minecraft.getMinecraft().renderEngine.bindTexture(ResourceFileGui.factionRoundBalance);
				else if (roundType == 3)
					Minecraft.getMinecraft().renderEngine.bindTexture(ResourceFileGui.factionRoundWar);
			
				gui.drawTexturedModalRect(width / 2 - 100 + guiTotalWidthEnd + 25, 2, 0, 0, 24, 24);
			}
			
			if (ticks == 100)
				ticks = 0;
		}
	}
	}

	@Override
	public EnumSet<TickType> ticks() {
	return EnumSet.of(TickType.RENDER);
	}

	@Override
	public String getLabel() {
	return "tickhandler";
	}

	public void setRoundType(byte par1RoundType) 
	{
		this.roundType = par1RoundType;
		switch (roundType)
		{
			case 0:
			{
				mc.thePlayer.addChatMessage("The server is in warmup mode");
				break;
			}
			case 1:
			{
				mc.thePlayer.addChatMessage("The server is in Prep mode");
				break;
			}
			case 2:
			{
				mc.thePlayer.addChatMessage("The server is in Balance mode");
				break;
			}
			case 3: 
			{
				mc.thePlayer.addChatMessage("The server is in War mode");
				break;
			}
		}
		secondsLeftInRound();
	}
	
	private void secondsLeftInRound()
	{
		Date now = new Date();
		
		if (roundType == 0) //Warmup Round
		{
			Date aDate = new Date(now.getYear(), now.getMonth(), now.getDate(), now.getHours(), 5, 0);
			secondsLeftInRound = (int)(aDate.getTime() - now.getTime()) / 1000;
		}
		else if (roundType == 3)
		{
			Date aDate = new Date(now.getYear(), now.getMonth(), now.getDate(), now.getHours() + 1, 0, 0);
			secondsLeftInRound = (int)(aDate.getTime() - now.getTime()) / 1000;
		}
		else if (roundType == 2)
		{
			Date aDate = new Date(now.getYear(), now.getMonth(), now.getDate(), now.getHours() + 2, 0, 0);
			secondsLeftInRound = (int)(aDate.getTime() - now.getTime()) / 1000;
		}
		else if (roundType == 3)
		{
			Date aDate = new Date(now.getYear(), now.getMonth(), now.getDate(), now.getHours() + 1, 0, 0);
			secondsLeftInRound = (int)(aDate.getTime() - now.getTime()) / 1000;
		}
	}
}
