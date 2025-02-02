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

package com.natamus.thevanillaexperience.mods.spidersproducewebs.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class SpidersProduceWebsConfigHandler {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final General GENERAL = new General(BUILDER);
	public static final ForgeConfigSpec spec = BUILDER.build();

	public static class General {		
		public final ForgeConfigSpec.ConfigValue<Integer> maxDistanceToSpiderBlocks;
		public final ForgeConfigSpec.ConfigValue<Integer> spiderWebProduceDelayTicks;

		public General(ForgeConfigSpec.Builder builder) {
			builder.push("General");
			maxDistanceToSpiderBlocks = builder
					.comment("The maximum distance in blocks the player can be away from a spider in order for the it to produce a web periodically.")
					.defineInRange("maxDistanceToSpiderBlocks", 32, 1, 256);
			spiderWebProduceDelayTicks = builder
					.comment("The delay in between spiders producing a web in ticks (1 second = 20 ticks).")
					.defineInRange("spiderWebProduceDelayTicks", 12000, 1, 72000);
			
			builder.pop();
		}
	}
}