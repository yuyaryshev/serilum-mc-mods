/*
 * This is the latest source code of Sleep Sooner.
 * Minecraft version: 1.19.x, mod version: 2.8.
 *
 * If you'd like access to the source code of previous Minecraft versions or previous mod versions, consider becoming a Github Sponsor or Patron.
 * You'll be added to a private repository which contains all versions' source of Sleep Sooner ever released, along with some other perks.
 *
 * Github Sponsor link: https://github.com/sponsors/ricksouth
 * Patreon link: https://patreon.com/ricksouth
 *
 * Becoming a Sponsor or Patron allows me to dedicate more time to the development of mods.
 * Thanks for looking at the source code! Hope it's of some use to your project. Happy modding!
 */

package com.natamus.sleepsooner.events;

import com.natamus.collective_fabric.data.GlobalVariables;
import com.natamus.collective_fabric.functions.BlockPosFunctions;
import com.natamus.collective_fabric.functions.StringFunctions;
import com.natamus.collective_fabric.functions.WorldFunctions;
import com.natamus.sleepsooner.config.ConfigHandler;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.HitResult;

public class PlayerEvent {
	public static InteractionResult playerClick(Player player, Level world, InteractionHand hand, HitResult hitResult) {  
		if (world.isClientSide || !hand.equals(InteractionHand.MAIN_HAND)) {
            return InteractionResult.PASS;
        }
		
		if (!ConfigHandler.enableSleepSooner.getValue()) {
			return InteractionResult.PASS;
		}
		
		BlockPos hitpos = BlockPosFunctions.getBlockPosFromHitResult(hitResult);
		Block block = world.getBlockState(hitpos).getBlock();
		if (block instanceof BedBlock == false) {
			return InteractionResult.PASS;
		}
		
		Integer sleeptime = ConfigHandler.whenSleepIsPossibleInTicks.getValue();
		
		Integer currenttime = (int)world.getDayTime();
		Integer days = (int)Math.floor((double)currenttime/24000);
		Integer daytime = currenttime - (days*24000);
		
		if (sleeptime > 12540) {
			if (daytime > 12540 && daytime < sleeptime) {
				StringFunctions.sendMessage(player, "It's too early to sleep.", ChatFormatting.DARK_GREEN);
				
				return InteractionResult.FAIL;
			}
		}
		
		if (daytime > 12540) {
			return InteractionResult.PASS;
		}
		
		if (daytime < sleeptime) {
			return InteractionResult.PASS;
		}
		
		WorldFunctions.setWorldTime((ServerLevel)world, 12540);

		if (ConfigHandler.enablePreSleepMessage.getValue()) {
			String unique = GlobalVariables.lingermessages.get(GlobalVariables.random.nextInt(GlobalVariables.lingermessages.size()));
			
			StringFunctions.sendMessage(player, "You " + unique + " until dusk. You may now sleep.", ChatFormatting.DARK_GREEN);
		}
		
		return InteractionResult.PASS;
	}
}
