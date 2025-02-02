/*
 * This is the latest source code of Altered Damage.
 * Minecraft version: 1.19.0, mod version: 1.6.
 *
 * If you'd like access to the source code of previous Minecraft versions or previous mod versions, consider becoming a Github Sponsor or Patron.
 * You'll be added to a private repository which contains all versions' source of Altered Damage ever released, along with some other perks.
 *
 * Github Sponsor link: https://github.com/sponsors/ricksouth
 * Patreon link: https://patreon.com/ricksouth
 *
 * Becoming a Sponsor or Patron allows me to dedicate more time to the development of mods.
 * Thanks for looking at the source code! Hope it's of some use to your project. Happy modding!
 */

package com.natamus.altereddamage.events;

import com.natamus.altereddamage.config.ConfigHandler;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class EntityEvent {
	@SubscribeEvent
	public void onEntityDamageTaken(LivingHurtEvent e) {
		Entity target = e.getEntity();
		Level world = target.getCommandSenderWorld();
		if (world.isClientSide) {
			return;
		}
			
		Double modifier = 1.0;
		
		if (target instanceof Player) {
			if (!ConfigHandler.GENERAL.alterPlayerDamageTaken.get()) {
				return;
			}
			
			modifier = ConfigHandler.GENERAL.playerDamageModifier.get();
		}
		else {
			if (!ConfigHandler.GENERAL.alterEntityDamageTaken.get()) {
				return;
			}
			
			modifier = ConfigHandler.GENERAL.entityDamageModifier.get();
		}
		
		float damage = (float)(e.getAmount()*modifier);
		
		if (ConfigHandler.GENERAL.preventFatalModifiedDamage.get()) {
			LivingEntity le = (LivingEntity)target;
			float health = (float)Math.floor(le.getHealth());
			if (damage >= health) {
				return;
			}
		}
		
		e.setAmount(damage);
	}
}
