/*
 * This is the latest source code of Shulker Drops Two.
 * Minecraft version: 1.18.2, mod version: 1.8.
 *
 * If you'd like access to the source code of previous Minecraft versions or previous mod versions, consider becoming a Github Sponsor or Patron.
 * You'll be added to a private repository which contains all versions' source of Shulker Drops Two ever released, along with some other perks.
 *
 * Github Sponsor link: https://github.com/sponsors/ricksouth
 * Patreon link: https://patreon.com/ricksouth
 *
 * Becoming a Sponsor or Patron allows me to dedicate more time to the development of mods.
 * Thanks for looking at the source code! Hope it's of some use to your project. Happy modding!
 */

package com.natamus.shulkerdropstwo.events;

import java.util.Iterator;

import com.natamus.shulkerdropstwo.config.ConfigHandler;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class EntityEvent {
	@SubscribeEvent
	public void mobItemDrop(LivingDropsEvent e) {
		Entity entity = e.getEntity();
		Level world = entity.getCommandSenderWorld();
		if (world.isClientSide) {
			return;
		}
		
		if (entity instanceof Shulker == false) {
			return;
		}
		
		boolean foundshells = false;
		
		Iterator<ItemEntity> iterator = e.getDrops().iterator();
		while (iterator.hasNext()) {
			ItemEntity drop = iterator.next();
			ItemStack item = drop.getItem();
			if (item.getItem().equals(Items.SHULKER_SHELL)) {
				item.setCount(ConfigHandler.GENERAL.shulkerDropAmount.get());
				drop.setItem(item);
				foundshells = true;
				break;
			}
		}
		
		if (ConfigHandler.GENERAL.alwaysDropShells.get() && !foundshells) {
			BlockPos pos = entity.blockPosition();
			ItemEntity shells = new ItemEntity(world, pos.getX(), pos.getY()+1, pos.getZ(), new ItemStack(Items.SHULKER_SHELL, ConfigHandler.GENERAL.shulkerDropAmount.get()));
			e.getDrops().add(shells);
		}
	}
}
