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

package com.natamus.thevanillaexperience.mods.fixedanvilrepaircost.events;

import com.natamus.thevanillaexperience.mods.fixedanvilrepaircost.config.FixedAnvilRepairCostConfigHandler;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class FixedAnvilRepairCostRepairEvent {
	@SubscribeEvent
	public void onRepairEvent(AnvilUpdateEvent e) {
		ItemStack leftstack = e.getLeft();
		ItemStack rightstack = e.getRight();
		ItemStack outputstack = e.getOutput();
		
		if (!rightstack.getItem().equals(Items.ENCHANTED_BOOK) && !leftstack.getItem().equals(rightstack.getItem()) && outputstack.isEmpty()) {
			Item leftitem = leftstack.getItem();
			if (!leftitem.isValidRepairItem(leftstack, rightstack)) {
				return;
			}
			
			int levelcost = FixedAnvilRepairCostConfigHandler.GENERAL.repairCostLevelAmount.get();
			int materialcost = FixedAnvilRepairCostConfigHandler.GENERAL.repairCostMaterialAmount.get();
			
			if (levelcost >= 1 || materialcost >= 1) {
				if (levelcost >= 1) {
					e.setCost(levelcost);
				}
				if (materialcost >= 1) {
					e.setMaterialCost(materialcost);
					
					if (materialcost > rightstack.getCount()) {
						e.setCanceled(true);
						return;
					}
				}
				
				int currentdamage = leftstack.getDamageValue();
				int maxdamage = leftstack.getMaxDamage();
				int repairamount = (int)(maxdamage * FixedAnvilRepairCostConfigHandler.GENERAL.percentRepairedPerAction.get());
				
				currentdamage -= repairamount;
				if (currentdamage < 0) {
					currentdamage = 0;
				}
				
				ItemStack newoutputstack = leftstack.copy();
				newoutputstack.setDamageValue(currentdamage);
				
				e.setOutput(newoutputstack);
			}
		}
	}
}
