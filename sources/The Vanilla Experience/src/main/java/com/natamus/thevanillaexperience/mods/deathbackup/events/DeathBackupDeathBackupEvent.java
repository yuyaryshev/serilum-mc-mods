/*
 * This is the latest source code of The Vanilla Experience.
 * Minecraft version: 1.17.1, mod version: 1.4.
 *
 * If you'd like access to the source code of previous Minecraft versions or previous mod versions, consider becoming a Github Sponsor or Patron.
 * You'll be added to a private repository which contains all versions' source of The Vanilla Experience ever released, along with some other perks.
 *
 * Github Sponsor link: https://github.com/sponsors/ricksouth
 * Patreon link: https://patreon.com/ricksouth
 *
 * Becoming a Sponsor or Patron allows me to dedicate more time to the development of mods.
 * Thanks for looking at the source code! Hope it's of some use to your project. Happy modding!
 */

package com.natamus.thevanillaexperience.mods.deathbackup.events;

import com.natamus.collective.functions.DateFunctions;
import com.natamus.collective.functions.PlayerFunctions;
import com.natamus.collective.functions.StringFunctions;
import com.natamus.thevanillaexperience.mods.deathbackup.config.DeathBackupConfigHandler;
import com.natamus.thevanillaexperience.mods.deathbackup.util.DeathBackupUtil;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class DeathBackupDeathBackupEvent {

	@SubscribeEvent
	public void onPlayerDeath(LivingDeathEvent e) {
		Entity entity = e.getEntity();
		Level world = entity.getCommandSenderWorld();
		if (world.isClientSide) {
			return;
		}
		
		if (entity instanceof Player == false) {
			return;
		}
		
		if (world instanceof ServerLevel == false) {
			return;
		}
		
		ServerLevel serverworld = (ServerLevel)world;
		Player player = (Player)entity;
		String playername = player.getName().getString().toLowerCase();
		
		String gearstring = PlayerFunctions.getPlayerGearString(player);
		if (gearstring == "") {
			return;
		}
		
		String nowstring = DateFunctions.getNowInYmdhis();
		DeathBackupUtil.writeGearStringToFile(serverworld, playername, nowstring, gearstring);
		
		if (DeathBackupConfigHandler.GENERAL.sendBackupReminderMessageToThoseWithAccessOnDeath.get()) {
			if (player.hasPermissions(2)) {
				StringFunctions.sendMessage(player, DeathBackupConfigHandler.GENERAL.backupReminderMessage.get(), ChatFormatting.DARK_GRAY);
			}
		}
	}
}
