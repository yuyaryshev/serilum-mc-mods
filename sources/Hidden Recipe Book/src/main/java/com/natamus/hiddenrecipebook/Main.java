/*
 * This is the latest source code of Hidden Recipe Book.
 * Minecraft version: 1.19.0, mod version: 2.4.
 *
 * If you'd like access to the source code of previous Minecraft versions or previous mod versions, consider becoming a Github Sponsor or Patron.
 * You'll be added to a private repository which contains all versions' source of Hidden Recipe Book ever released, along with some other perks.
 *
 * Github Sponsor link: https://github.com/sponsors/ricksouth
 * Patreon link: https://patreon.com/ricksouth
 *
 * Becoming a Sponsor or Patron allows me to dedicate more time to the development of mods.
 * Thanks for looking at the source code! Hope it's of some use to your project. Happy modding!
 */

package com.natamus.hiddenrecipebook;

import com.natamus.collective.check.RegisterMod;
import com.natamus.hiddenrecipebook.config.ConfigHandler;
import com.natamus.hiddenrecipebook.events.BookGUIEvent;
import com.natamus.hiddenrecipebook.util.Reference;
import com.natamus.hiddenrecipebook.util.Variables;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Reference.MOD_ID)
public class Main {
	public static Main instance;
	
    public Main() {
		if (!FMLEnvironment.dist.equals(Dist.CLIENT)) {
			return;
		}
    	
        instance = this;
        
        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    	
        modEventBus.addListener(this::loadComplete);
        modLoadingContext.registerConfig(ModConfig.Type.COMMON, ConfigHandler.spec);
        
        modEventBus.addListener(this::initClient);
        modEventBus.addListener(this::loadComplete);

        RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
    }
    
    private void initClient(final FMLClientSetupEvent event) {
    	Variables.hotkey = new KeyMapping("Show recipe book in crafting grid toggle.", 258, "key.categories.misc");
    	ClientRegistry.registerKeyBinding(Variables.hotkey);    	
    }
	
    private void loadComplete(final FMLLoadCompleteEvent event) {
    	MinecraftForge.EVENT_BUS.register(new BookGUIEvent());
	}
}
