/*
 * This is the latest source code of Villager Death Messages.
 * Minecraft version: 1.19.x, mod version: 2.3.
 *
 * If you'd like access to the source code of previous Minecraft versions or previous mod versions, consider becoming a Github Sponsor or Patron.
 * You'll be added to a private repository which contains all versions' source of Villager Death Messages ever released, along with some other perks.
 *
 * Github Sponsor link: https://github.com/sponsors/ricksouth
 * Patreon link: https://patreon.com/ricksouth
 *
 * Becoming a Sponsor or Patron allows me to dedicate more time to the development of mods.
 * Thanks for looking at the source code! Hope it's of some use to your project. Happy modding!
 */

package com.natamus.villagerdeathmessages.events;

import com.natamus.collective_fabric.functions.EntityFunctions;
import com.natamus.collective_fabric.functions.StringFunctions;
import com.natamus.villagerdeathmessages.config.ConfigHandler;

import net.minecraft.ChatFormatting;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class VillagerEvent {
	public static void villagerDeath(Level world, Entity entity, DamageSource source) {
		if (world.isClientSide) {
			return;
		}
		
		boolean goname = false;
		if (entity instanceof Villager == false) {
			if (ConfigHandler.mentionModdedVillagers.getValue()) {
				if (EntityFunctions.isModdedVillager(entity)) {
					goname = true;
				}
			}
			
			if (!goname) {
				return;
			}
		}
		
		boolean modded = false;
		String prefix = "";
		
		if (goname) {
			modded = true;
		}
		else {
			Villager villager = (Villager)entity;
			VillagerData d = villager.getVillagerData();
			VillagerProfession prof = d.getProfession();
			
			if (prof != null) {
				String profession = "";
				if (prof.toString() != "none") {
					profession = prof.toString();
				}
				
				if (profession != "") {
					prefix = "A " + profession + " villager";
					if (villager.hasCustomName()) {
						prefix = villager.getName().getString() + " the " + profession;
					}
				}
				else {
					prefix = "A villager";
					if (villager.hasCustomName()) {
						prefix = villager.getName().getString();
					}
				}
			}
			else {
				modded = true;
			}
		}
		
		if (modded) {
			prefix = "A special villager";
			if (entity.hasCustomName()) {
				prefix = entity.getName().getString();
			}
		}
		
		// Damage source
		String imsourcename = source.getMsgId();
		String sourcename = "";
		
		Entity truesource = source.getEntity();
		if (truesource != null) {
			sourcename = truesource.getName().getString();
		}
		if (sourcename != "" && imsourcename.equals("player")) {
			imsourcename = sourcename;
		}
		else if (imsourcename.contains(".")) {
			imsourcename = imsourcename.split("\\.")[0];
		}
		
		// Position	
		String locstring = "";
		if (ConfigHandler.showLocation.getValue()) {
			Vec3 loc = entity.position();
			String location = "x=" + (int)loc.x + ", y=" + (int)loc.y + ", z=" + (int)loc.z;
			
			locstring = " at " + location;
		}
		
		StringFunctions.broadcastMessage(world, prefix + " has died" + locstring + " by " + imsourcename + ".", ChatFormatting.DARK_GREEN);
	}
}
