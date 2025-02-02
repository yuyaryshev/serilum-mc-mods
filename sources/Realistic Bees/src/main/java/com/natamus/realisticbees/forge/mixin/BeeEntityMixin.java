/*
 * This is the latest source code of Realistic Bees.
 * Minecraft version: 1.19.0, mod version: 2.5.
 *
 * If you'd like access to the source code of previous Minecraft versions or previous mod versions, consider becoming a Github Sponsor or Patron.
 * You'll be added to a private repository which contains all versions' source of Realistic Bees ever released, along with some other perks.
 *
 * Github Sponsor link: https://github.com/sponsors/ricksouth
 * Patreon link: https://patreon.com/ricksouth
 *
 * Becoming a Sponsor or Patron allows me to dedicate more time to the development of mods.
 * Thanks for looking at the source code! Hope it's of some use to your project. Happy modding!
 */

package com.natamus.realisticbees.forge.mixin;

import com.natamus.realisticbees.util.Util;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.animal.Bee;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Bee.class, priority = 1001)
public class BeeEntityMixin {
    private final boolean preventBeeSuffocationDamage = Util.getPreventBeeSuffocationDamage();

    @Inject(method = "hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z", at = @At(value = "HEAD"), cancellable = true)
    private void hurt(DamageSource damageSource, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (preventBeeSuffocationDamage && damageSource.equals(DamageSource.IN_WALL)) {
            cir.setReturnValue(false);
        }
    }
}
