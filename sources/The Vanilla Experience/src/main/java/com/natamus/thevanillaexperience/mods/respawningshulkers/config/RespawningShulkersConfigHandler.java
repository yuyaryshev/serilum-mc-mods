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

package com.natamus.thevanillaexperience.mods.respawningshulkers.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class RespawningShulkersConfigHandler {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final General GENERAL = new General(BUILDER);
	public static final ForgeConfigSpec spec = BUILDER.build();

	public static class General {
		public final ForgeConfigSpec.ConfigValue<Integer> timeInTicksToRespawn;
		public final ForgeConfigSpec.ConfigValue<Boolean> shulkersFromSpawnersDoNotRespawn;

		public General(ForgeConfigSpec.Builder builder) {
			builder.push("General");
			timeInTicksToRespawn = builder
					.comment("The amount of time in ticks it takes for a shulker to respawn after it died. 20 ticks = 1 second. By default 60 seconds.")
					.defineInRange("timeInTicksToRespawn", 1200, 1, 72000);
			shulkersFromSpawnersDoNotRespawn = builder
					.comment("If enabled, shulkers which spawn from (modded) spawners will not be respawned after a death.")
					.define("shulkersFromSpawnersDoNotRespawn", true);
			
			builder.pop();
		}
	}
}