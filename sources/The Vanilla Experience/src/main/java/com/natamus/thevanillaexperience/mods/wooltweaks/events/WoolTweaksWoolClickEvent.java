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

package com.natamus.thevanillaexperience.mods.wooltweaks.events;

import com.natamus.thevanillaexperience.mods.wooltweaks.util.WoolTweaksUtil;

import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WoolCarpetBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class WoolTweaksWoolClickEvent {
	@SubscribeEvent
	public void onWoolClick(PlayerInteractEvent.RightClickBlock e) {
		Level world = e.getWorld();
		if (world.isClientSide) {
			return;
		}
		
		ItemStack handstack = e.getItemStack();
		Item handitem = handstack.getItem();
		if (handitem instanceof DyeItem == false) {
			return;
		}
		
		BlockPos target = e.getPos();
		BlockState state = world.getBlockState(target);
		Block block = state.getBlock();
		
		Block newblock = null;
		if (block.getTags().contains(new ResourceLocation("wool"))) {
			newblock = WoolTweaksUtil.woolblocks.get(handitem);
		}
		else if (block instanceof BedBlock) {
			newblock = WoolTweaksUtil.bedblocks.get(handitem);
		}
		else if (block instanceof WoolCarpetBlock) {
			newblock = WoolTweaksUtil.carpetblocks.get(handitem);
		}
		else {
			return;
		}
		
		if (newblock == null) {
			return;
		}
		
		if (block.equals(newblock)) {
			return;
		}
		
		e.setCanceled(true);
		
		BlockState newstate = newblock.defaultBlockState();
		if (block instanceof BedBlock) {
			Direction direction = state.getValue(BedBlock.FACING);
			newstate = newstate.setValue(BedBlock.FACING, direction);
			newstate = newstate.setValue(BedBlock.OCCUPIED, state.getValue(BedBlock.OCCUPIED));
			
			BedPart bedpart = state.getValue(BedBlock.PART);
			newstate = newstate.setValue(BedBlock.PART, bedpart);
			
			BlockPos othertarget = target.immutable();
			BedPart otherpart;
			if (bedpart.equals(BedPart.HEAD)) {
				otherpart = BedPart.FOOT;
				othertarget = target.relative(direction.getOpposite());
				
				world.setBlockAndUpdate(target, Blocks.AIR.defaultBlockState());
				world.setBlockAndUpdate(othertarget, Blocks.AIR.defaultBlockState());
			}
			else {
				otherpart = BedPart.HEAD;
				othertarget = target.relative(direction);
				
				world.setBlockAndUpdate(othertarget, Blocks.AIR.defaultBlockState());
				world.setBlockAndUpdate(target, Blocks.AIR.defaultBlockState());
			}
			
			world.setBlockAndUpdate(othertarget, newstate.setValue(BedBlock.PART, otherpart));
		}
		
		world.setBlockAndUpdate(target, newstate);
		
		Player player = e.getPlayer();
		if (!player.isCreative()) {
			handstack.shrink(1);
		}
	}
}