/*
 * This is the latest source code of Starter Kit.
 * Minecraft version: 1.19.0, mod version: 3.2.
 *
 * If you'd like access to the source code of previous Minecraft versions or previous mod versions, consider becoming a Github Sponsor or Patron.
 * You'll be added to a private repository which contains all versions' source of Starter Kit ever released, along with some other perks.
 *
 * Github Sponsor link: https://github.com/sponsors/ricksouth
 * Patreon link: https://patreon.com/ricksouth
 *
 * Becoming a Sponsor or Patron allows me to dedicate more time to the development of mods.
 * Thanks for looking at the source code! Hope it's of some use to your project. Happy modding!
 */

package com.natamus.starterkit.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigHandler {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final General GENERAL = new General(BUILDER);
	public static final ForgeConfigSpec spec = BUILDER.build();

	public static class General {
		public final ForgeConfigSpec.ConfigValue<Boolean> enableFTBIslandCreateCompatibility;

		public General(ForgeConfigSpec.Builder builder) {
			builder.push("General");
			enableFTBIslandCreateCompatibility = builder
					.comment("Whether the starter kit should be re-set after the '/ftbteamislands create' command from FTB Team Islands. Does nothing when it's not installed.")
					.define("enableFTBIslandCreateCompatibility", true);
			
			builder.pop();
		}
	}
}