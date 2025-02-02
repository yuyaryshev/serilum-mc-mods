/*
 * This is the latest source code of Collective.
 * Minecraft version: 1.19.0, mod version: 4.28.
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

package com.natamus.collective.functions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfigFunctions {
	public static List<String> getRawConfigValues(String modid) {
		return getRawConfigValues(modid, false);
	}
	
	public static List<String> getRawConfigValues(String modid, boolean tve) {
		String dirpath = System.getProperty("user.dir") + File.separator + "config";
		if (tve) {
			dirpath = dirpath + File.separator + "TVE";
		}
		
		File dir = new File(dirpath);
		File file = new File(dirpath + File.separator + modid + "-common.toml");
		
		List<String> values = new ArrayList<String>();
		if (dir.isDirectory() && file.isFile()) {
			try {
				String content = new String(Files.readAllBytes(Paths.get(dirpath + File.separator + modid + "-common.toml")));
				for (String line : content.split("\n")) {
					String trimmedline = line.trim();
					if (trimmedline.startsWith("[") || trimmedline.startsWith("#")) {
						continue;
					}
					
					values.add(trimmedline);
				}
				
			} catch (IOException ignored) { }
		}
		
		return values;
	}

	public static HashMap<String, String> getDictValues(String modid) {
		return getDictValues(modid, false);
	}

	public static HashMap<String, String> getDictValues(String modid, boolean tve) {
		HashMap<String, String> hm = new HashMap<String, String>();

		List<String> rawvalues = getRawConfigValues(modid, tve);
		for (String rawvalue : rawvalues) {
			if (rawvalue.length() < 3) {
				continue;
			}

			String rv = rawvalue.replace("\"", "");

			String[] rvspl;
			if (rv.contains("=")) {
				rvspl = rv.split("=");
			}
			else if (rv.contains(":")) {
				rvspl = rv.split(":");
			}
			else {
				continue;
			}

			if (rvspl.length < 2) {
				continue;
			}

			String keystr = rvspl[0].trim();
			String valuestr = rvspl[1].trim();

			hm.put(keystr, valuestr);
		}

		return hm;
	}
}
