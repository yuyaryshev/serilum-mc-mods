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

package com.natamus.thevanillaexperience.mods.bouncierbeds.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class BouncierBedsConfigHandler {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final General GENERAL = new General(BUILDER);
	public static final ForgeConfigSpec spec = BUILDER.build();

	public static class General {
		public final ForgeConfigSpec.ConfigValue<Boolean> bedsPreventFallDamage;
		public final ForgeConfigSpec.ConfigValue<Double> bedBounciness;

		public General(ForgeConfigSpec.Builder builder) {
			builder.push("General");
			bedsPreventFallDamage = builder
					.comment("Whether beds should prevent fall damage when a player lands on them. It's recommended to keep this enabled if you have lots of bounciness.")
					.define("bedsPreventFallDamage", true);
			bedBounciness = builder
					.comment("The modifier of how much a bed bounces. A value of 2.0 makes the player jump around 30 blocks. A value of 100.0 makes the player jump around 4500 blocks.")
					.defineInRange("bedBounciness", 1.5, 0.0, 100.0);
			
			builder.pop();
		}
	}
}