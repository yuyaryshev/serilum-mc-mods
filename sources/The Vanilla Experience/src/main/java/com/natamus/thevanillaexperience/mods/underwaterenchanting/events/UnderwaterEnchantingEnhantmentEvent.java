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

package com.natamus.thevanillaexperience.mods.underwaterenchanting.events;

import java.util.Iterator;

import com.natamus.collective.functions.NumberFunctions;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.enchanting.EnchantmentLevelSetEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class UnderwaterEnchantingEnhantmentEvent {
	@SubscribeEvent
	public void onEnchanting(EnchantmentLevelSetEvent e) {
		Level world = e.getWorld();
		if (world.isClientSide) {
			return;
		}
		
		int row = e.getEnchantRow();
		int currentpower = e.getPower();
		
		if (currentpower == 0) {
			int bookshelfcount = 0;
			
			BlockPos epos = e.getPos();
			Iterator<BlockPos> it = BlockPos.betweenClosedStream(epos.getX()-2, epos.getY()-2, epos.getZ()-2, epos.getX()+2, epos.getY()+2, epos.getZ()+2).iterator();
			while (it.hasNext()) {
				BlockPos np = it.next();
				if (world.getBlockState(np).getBlock().equals(Blocks.BOOKSHELF)) {
					bookshelfcount++;
				}
			}
			
			if (bookshelfcount > 0) {
				int level = NumberFunctions.getEnchantingTableLevel(row, bookshelfcount);
				if (level < 0) {
					return;
				}
				
				e.setLevel(level);
			}
		}
	}
}
