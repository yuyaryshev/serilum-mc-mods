/*
 * This is the latest source code of GUI Clock.
 * Minecraft version: 1.19.x, mod version: 3.2.
 *
 * If you'd like access to the source code of previous Minecraft versions or previous mod versions, consider becoming a Github Sponsor or Patron.
 * You'll be added to a private repository which contains all versions' source of GUI Clock ever released, along with some other perks.
 *
 * Github Sponsor link: https://github.com/sponsors/ricksouth
 * Patreon link: https://patreon.com/ricksouth
 *
 * Becoming a Sponsor or Patron allows me to dedicate more time to the development of mods.
 * Thanks for looking at the source code! Hope it's of some use to your project. Happy modding!
 */

package com.natamus.guiclock.events;

import java.awt.Color;
import java.util.Collection;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import com.natamus.collective_fabric.functions.StringFunctions;
import com.natamus.guiclock.config.ConfigHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class GUIEvent {
	private static final Minecraft mc = Minecraft.getInstance();
	private static String daystring = "";
	
	public static void renderOverlay(PoseStack posestack, float tickDelta){
		boolean gametimeb = ConfigHandler.mustHaveClockInInventoryForGameTime.getValue();
		boolean realtimeb = ConfigHandler.mustHaveClockInInventoryForRealTime.getValue();
		boolean found = true;
		
		if (gametimeb || realtimeb) {
			found = false;
			Inventory inv = mc.player.getInventory();
			for (int n = 0; n <= 35; n++) {
				if (inv.getItem(n).getItem().equals(Items.CLOCK)) {
					found = true;
					break;
				}
			}
		}
		
		posestack.pushPose();
		
		Font fontRender = mc.font;
		Window scaled = mc.getWindow();
		int width = scaled.getGuiScaledWidth();
		
		int heightoffset = ConfigHandler.clockHeightOffset.getValue();
		if (heightoffset < 5) {
			heightoffset = 5;
		}
		
		if (ConfigHandler.lowerClockWhenPlayerHasEffects.getValue()) {
			Collection<MobEffectInstance> activeeffects = mc.player.getActiveEffects();
			if (activeeffects.size() > 0) {
				boolean isvisible = false;
				for (MobEffectInstance effect : activeeffects) {
					if (effect.isVisible()) {
						isvisible = true;
						break;
					}
				}
				
				if (isvisible) {
					heightoffset += 24;
				}
			}
		}
		
		int xcoord = 0;
		int daycoord = 0;
		if (ConfigHandler.showOnlyMinecraftClockIcon.getValue()) {
			if (gametimeb) {
				if (!found) {
					return;
				}
			}
			
			if (ConfigHandler.clockPositionIsLeft.getValue()) {
				xcoord = 20;
			}
			else if (ConfigHandler.clockPositionIsCenter.getValue()) {
				xcoord = (width/2) - 8;
			}
			else {
				xcoord = width - 20;
			}
			
			xcoord += ConfigHandler.clockWidthOffset.getValue();
			
			ItemRenderer itemrenderer = mc.getItemRenderer();
			itemrenderer.renderAndDecorateItem(new ItemStack(Items.CLOCK), xcoord, heightoffset);
		}
		else {
			String time = "";
			String realtime = StringFunctions.getPCLocalTime(ConfigHandler._24hourformat.getValue(), ConfigHandler.showRealTimeSeconds.getValue());
			if (ConfigHandler.showBothTimes.getValue()) {
				if (gametimeb && realtimeb) {
					if (!found) {
						return;
					}
					time = getGameTime() + " | " + realtime;
				}
				else if (!found && gametimeb) {
					time = realtime;
				}
				else if (!found && realtimeb) {
					time = getGameTime();
				}
				else {
					time = getGameTime() + " | " + realtime;
				}
			}
			else if (ConfigHandler.showRealTime.getValue()) {
				if (realtimeb) {
					if (!found) {
						return;
					}
				}
				time = realtime;
			}
			else {
				if (gametimeb) {
					if (!found) {
						return;
					}
				}
				time = getGameTime();
			}
			
			if (time == "") {
				return;
			}
			
			int stringWidth = fontRender.width(time);
			int daystringWidth = fontRender.width(daystring);
			
			Color colour = new Color(ConfigHandler.RGB_R.getValue(), ConfigHandler.RGB_G.getValue(), ConfigHandler.RGB_B.getValue(), 255);
			
			if (ConfigHandler.clockPositionIsLeft.getValue()) {
				xcoord = 5;
				daycoord = 5;
			}
			else if (ConfigHandler.clockPositionIsCenter.getValue()) {
				xcoord = (width/2) - (stringWidth/2);
				daycoord = (width/2) - (daystringWidth/2);
			}
			else {
				xcoord = width - stringWidth - 5;
				daycoord = width - daystringWidth - 5;
			}
			
			xcoord += ConfigHandler.clockWidthOffset.getValue();
			daycoord += ConfigHandler.clockWidthOffset.getValue();
			
			fontRender.draw(posestack, time, xcoord, heightoffset, colour.getRGB());
			if (daystring != "") {
				fontRender.draw(posestack, daystring, daycoord, heightoffset+10, colour.getRGB());
			}
		}
		
		posestack.popPose();
	}
	
	private static String getGameTime() {
		int time = 0;
		int gametime = (int)mc.level.getDayTime();
		int daysplayed = 0;
		
		while (gametime >= 24000) {
			gametime-=24000;
			daysplayed += 1;
		}
		
		if (ConfigHandler.showDaysPlayedWorld.getValue()) {
			daystring = "Day " + daysplayed;
		}

		if (gametime >= 18000) {
			time = gametime-18000;
		}
		else {
			time = 6000+gametime;
		}
		
		String suffix = "";
		if (!ConfigHandler._24hourformat.getValue()) {
			if (time >= 13000) {
				time = time - 12000;
				suffix = " PM";
			}
			else {
				if (time >= 12000) {
					suffix = " PM";
				}
				else {
					suffix = " AM";
					if (time <= 999) {
						time += 12000;
					}
				}
			}
		}
		
		String stringtime = time/10 + "";
		for (int n = stringtime.length(); n < 4; n++) {
			stringtime = "0" + stringtime;
		}
		
		
		String[] strsplit = stringtime.split("");
		
		int minutes = (int)Math.floor(Double.parseDouble(strsplit[2] + strsplit[3])/100*60);
		String sm = minutes + "";
		if (minutes < 10) {
			sm = "0" + minutes;
		}
		
		if (!ConfigHandler._24hourformat.getValue() && strsplit[0].equals("0")) {
			stringtime = strsplit[1] + ":" + sm.charAt(0) + sm.charAt(1);
		}
		else {
			stringtime = strsplit[0] + strsplit[1] + ":" + sm.charAt(0) + sm.charAt(1);
		}
		
		return stringtime + suffix;
	}
}