/*
 * This is the latest source code of Fall Through Slime.
 * Minecraft version: 1.19.0, mod version: 1.5.
 *
 * If you'd like access to the source code of previous Minecraft versions or previous mod versions, consider becoming a Github Sponsor or Patron.
 * You'll be added to a private repository which contains all versions' source of Fall Through Slime ever released, along with some other perks.
 *
 * Github Sponsor link: https://github.com/sponsors/ricksouth
 * Patreon link: https://patreon.com/ricksouth
 *
 * Becoming a Sponsor or Patron allows me to dedicate more time to the development of mods.
 * Thanks for looking at the source code! Hope it's of some use to your project. Happy modding!
 */

package com.natamus.fallthroughslime.events;

import java.util.HashMap;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class SlimeEvent {
	private static HashMap<String, Vec3> lastvecs = new HashMap<String, Vec3>();
	private static HashMap<String, Integer> lastticks = new HashMap<String, Integer>();
	
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent e) {
		Player player = e.player;
		Level world = player.getCommandSenderWorld();
		if (world.isClientSide || !e.phase.equals(Phase.START)) {
			return;
		}
		
		String playername = player.getName().getString();
		if (!lastticks.containsKey(playername)) {
			lastticks.put(playername, 20);
			return;
		}
		
		int ticki = lastticks.get(playername);
		if (ticki > 0) {
			lastticks.put(playername, ticki-1);
			return;
		}
		lastticks.put(playername, 20);
		
		BlockPos ppos = player.blockPosition();
		Vec3 pvec = player.position();
		
		Vec3 lastvec = lastvecs.get(playername);
		lastvecs.put(playername, pvec);
		if (lastvec != null) {
			if (lastvec.x != pvec.x && lastvec.z != pvec.z) {
				return;
			}
		}
		else {
			return;
		}
		
		Block down = world.getBlockState(ppos.below()).getBlock();
		if (down.equals(Blocks.SLIME_BLOCK)) {
			player.teleportTo(pvec.x, pvec.y-0.2, pvec.z);
		}
	}
}
