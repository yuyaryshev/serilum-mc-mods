/*
 * This is the latest source code of Configurable Furnace Burn Time.
 * Minecraft version: 1.19.x, mod version: 1.2.
 *
 * If you'd like access to the source code of previous Minecraft versions or previous mod versions, consider becoming a Github Sponsor or Patron.
 * You'll be added to a private repository which contains all versions' source of Configurable Furnace Burn Time ever released, along with some other perks.
 *
 * Github Sponsor link: https://github.com/sponsors/ricksouth
 * Patreon link: https://patreon.com/ricksouth
 *
 * Becoming a Sponsor or Patron allows me to dedicate more time to the development of mods.
 * Thanks for looking at the source code! Hope it's of some use to your project. Happy modding!
 */

package com.natamus.configurablefurnaceburntime.events;

import com.natamus.configurablefurnaceburntime.config.ConfigHandler;

import net.minecraft.world.item.ItemStack;

public class FurnaceBurnEvent {
	public static int furnaceBurnTimeEvent(ItemStack itemStack, int burntime) {
		return (int)Math.ceil(burntime * ConfigHandler.burnTimeModifier.getValue());
	}
}
