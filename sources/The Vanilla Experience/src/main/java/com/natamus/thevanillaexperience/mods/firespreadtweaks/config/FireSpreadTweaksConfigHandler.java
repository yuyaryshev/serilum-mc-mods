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

package com.natamus.thevanillaexperience.mods.firespreadtweaks.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class FireSpreadTweaksConfigHandler {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final General GENERAL = new General(BUILDER);
	public static final ForgeConfigSpec spec = BUILDER.build();

	public static class General {
		public final ForgeConfigSpec.ConfigValue<Integer> timeFireBurnsInTicks;
		
		public final ForgeConfigSpec.ConfigValue<Boolean> enableRandomizedFireDuration;
		public final ForgeConfigSpec.ConfigValue<Integer> MinRandomExtraBurnTicks;
		public final ForgeConfigSpec.ConfigValue<Integer> MaxRandomExtraBurnTicks;

		public General(ForgeConfigSpec.Builder builder) {
			builder.push("General");
			timeFireBurnsInTicks = builder
					.comment("The time a fire will keep burning for in ticks. 20 ticks = 1 second.")
					.defineInRange("timeFireBurnsInTicks", 300, 0, 72000);
			
			enableRandomizedFireDuration = builder
					.comment("When enabled, uses the MinRandomExtraBurnTicks and MaxRandomExtraBurnTicks config values to randomize the time fire burns for.")
					.define("enableRandomizedFireDuration", true);	
			MinRandomExtraBurnTicks = builder
					.comment("If randomized fire duration is enabled, a random tick number will be chosen in between the minimum and maximum value. This will be added to timeFireBurnsInTicks. If the outcome is negative, it will be subtracted.")
					.defineInRange("MinRandomExtraBurnTicks", -100, -36000, 0);
			MaxRandomExtraBurnTicks = builder
					.comment("See MinRandomExtraBurnTicks's description.")
					.defineInRange("MaxRandomExtraBurnTicks", 100, 0, 36000);
			
			builder.pop();
		}
	}
}