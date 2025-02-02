/*
 * This is the latest source code of Collective.
 * Minecraft version: 1.19.x, mod version: 4.27.
 *
 * If you'd like access to the source code of previous Minecraft versions or previous mod versions, consider becoming a Github Sponsor or Patron.
 * You'll be added to a private repository which contains all versions' source of Collective ever released, along with some other perks.
 *
 * Github Sponsor link: https://github.com/sponsors/ricksouth
 * Patreon link: https://patreon.com/ricksouth
 *
 * Becoming a Sponsor or Patron allows me to dedicate more time to the development of mods.
 * Thanks for looking at the source code! Hope it's of some use to your project. Happy modding!
 */

package com.natamus.collective_fabric.fabric.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.natamus.collective_fabric.fabric.callbacks.CollectiveBlockEvents;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(value = Block.class, priority = 1001)
public class BlockMixin {
	@Inject(method = "setPlacedBy", at = @At(value = "HEAD"), cancellable = true) 
	public void Block_setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack, CallbackInfo ci) {
		if (!CollectiveBlockEvents.BLOCK_PLACE.invoker().onBlockPlace(level, blockPos, blockState, livingEntity, itemStack)) {
			ci.cancel();
		}
	}
	
	@Inject(method = "playerDestroy", at = @At(value = "HEAD"), cancellable = true) 
	public void Block_playerDestroy(Level level, Player player, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity, ItemStack itemStack, CallbackInfo ci) {
		if (!CollectiveBlockEvents.BLOCK_DESTROY.invoker().onBlockDestroy(level, player, blockPos, blockState, blockEntity, itemStack)) {
			ci.cancel();
		}
	}
}
