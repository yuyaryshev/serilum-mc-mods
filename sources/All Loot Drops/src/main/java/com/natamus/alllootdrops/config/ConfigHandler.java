/*
 * This is the latest source code of All Loot Drops.
 * Minecraft version: 1.19.0, mod version: 2.4.
 *
 * If you'd like access to the source code of previous Minecraft versions or previous mod versions, consider becoming a Github Sponsor or Patron.
 * You'll be added to a private repository which contains all versions' source of All Loot Drops ever released, along with some other perks.
 *
 * Github Sponsor link: https://github.com/sponsors/ricksouth
 * Patreon link: https://patreon.com/ricksouth
 *
 * Becoming a Sponsor or Patron allows me to dedicate more time to the development of mods.
 * Thanks for looking at the source code! Hope it's of some use to your project. Happy modding!
 */

package com.natamus.alllootdrops.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigHandler {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final General GENERAL = new General(BUILDER);
	public static final ForgeConfigSpec spec = BUILDER.build();

	public static class General {
		public final ForgeConfigSpec.ConfigValue<Integer> lootQuantity;
		public final ForgeConfigSpec.ConfigValue<Boolean> lootingEnchantAffectsQuantity;
		public final ForgeConfigSpec.ConfigValue<Double> lootingEnchantExtraQuantityChance;

		public General(ForgeConfigSpec.Builder builder) {
			builder.push("General");
			lootQuantity = builder
					.comment("Determines the amount of loot dropped by each mob.")
					.defineInRange("lootQuantity", 1, 1, 128);
			lootingEnchantAffectsQuantity = builder
					.comment("If enabled, the looting enchant has a chance to increase the quantity of loot dropped. Per level a roll is done with the chance from 'lootingEnchantExtraQuantityChancePerLevel'. If the roll succeeds, 1 is added to the loot amount.")
					.define("lootingEnchantAffectsQuantity", true);
			lootingEnchantExtraQuantityChance = builder
					.comment("The chance a roll succeeds in adding 1 to the total loot amount.")
					.defineInRange("lootingEnchantExtraQuantityChance", 0.5, 0, 1.0);
			
			builder.pop();
		}
	}
}