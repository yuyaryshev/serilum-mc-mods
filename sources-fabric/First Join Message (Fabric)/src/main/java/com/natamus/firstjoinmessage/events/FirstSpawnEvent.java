/*
 * This is the latest source code of First Join Message.
 * Minecraft version: 1.19.x, mod version: 1.5.
 *
 * If you'd like access to the source code of previous Minecraft versions or previous mod versions, consider becoming a Github Sponsor or Patron.
 * You'll be added to a private repository which contains all versions' source of First Join Message ever released, along with some other perks.
 *
 * Github Sponsor link: https://github.com/sponsors/ricksouth
 * Patreon link: https://patreon.com/ricksouth
 *
 * Becoming a Sponsor or Patron allows me to dedicate more time to the development of mods.
 * Thanks for looking at the source code! Hope it's of some use to your project. Happy modding!
 */

package com.natamus.firstjoinmessage.events;

import com.natamus.collective_fabric.functions.PlayerFunctions;
import com.natamus.collective_fabric.functions.StringFunctions;
import com.natamus.firstjoinmessage.config.ConfigHandler;
import com.natamus.firstjoinmessage.util.Reference;

import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class FirstSpawnEvent {
	public static void onSpawn(Level world, Entity entity) {
		if (world.isClientSide) {
			return;
		}
		
		if (entity instanceof Player == false) {
			return;
		}
		
		Player player = (Player)entity;
		if (PlayerFunctions.isJoiningWorldForTheFirstTime(player, Reference.MOD_ID)) {
			String joinmessage = ConfigHandler.firstJoinMessage.getValue();
			ChatFormatting colour = ChatFormatting.getById(ConfigHandler.firstJoinMessageTextFormattingColourIndex.getValue());
			if (colour == null) {
				return;
			}
			
			StringFunctions.sendMessage(player, joinmessage, colour);
		}
	}
}
