/*
 * Copyright (c) 2015 Jerrell Fang
 *
 * This project is Open Source and distributed under The MIT License (MIT)
 * (http://opensource.org/licenses/MIT)
 *
 * You should have received a copy of the The MIT License along with
 * this project.   If not, see <http://opensource.org/licenses/MIT>.
 */

package com.meowj.langutils.locale;

import com.meowj.langutils.LangUtils;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.logging.Level;



/**
 * Created by Meow J on 6/20/2015.
 * <p>
 * Language helper
 *
 * @author Meow J
 */
public class LocaleHelper {
	private static YamlConfiguration enchi18n;
	private static YamlConfiguration potioni18n;
	private final static LangUtils plugin = LangUtils.plugin;
	private static YamlConfiguration itemi18n;
    /**
     * Return the language of the player
     *
     * @param player The player to be analyzed
     * @return the language of the player(in Java locale format)
     */
    public static String getPlayerLanguage(Player player) {
        return player.getLocale();
    }
    public static void loadItemi18n() {
		File itemi18nFile = new File(plugin.getDataFolder(), "itemi18n.yml");
		if (!itemi18nFile.exists()) {
			plugin.getLogger().info("Creating itemi18n.yml");
			plugin.saveResource("itemi18n.yml", true);
		}
		// Store it
		itemi18n = YamlConfiguration.loadConfiguration(itemi18nFile);
		itemi18n.options().copyDefaults(true);
		YamlConfiguration itemi18nYAML = YamlConfiguration.loadConfiguration(new InputStreamReader(plugin.getResource("itemi18n.yml")));
		itemi18n.setDefaults(itemi18nYAML);
		Material[] itemsi18n = Material.values();
		for (Material material : itemsi18n) {
			String itemname = itemi18n.getString("itemi18n."+material.name());
			if(itemname==null || itemname.equals("")) {
				plugin.getLogger().info("Found new items/blocks ["+material.name()+"] ,add it in config...");
				itemi18n.set("itemi18n."+material.name(), material.name());
			}
		}
		try {
			itemi18n.save(itemi18nFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			plugin.getLogger().log(Level.WARNING, "Could not load/save transaction itemname from itemi18n.yml. Skipping.");
		}
		plugin.getLogger().info("Complete to load Itemname i18n.");
	}
	public static String getItemi18n(String ItemBukkitName) {
		ItemBukkitName = ItemBukkitName.trim().replaceAll(" ", "_").toUpperCase(Locale.ROOT);
		String Itemname_i18n = null;
		try {
		Itemname_i18n = itemi18n.getString("itemi18n."+ItemBukkitName).trim();
		}catch (Exception e) {
			e.printStackTrace();
			Itemname_i18n = null;
		}
		if(ItemBukkitName==null) {
			return "";
		}
		if(Itemname_i18n==null) {
			String material = null;
			try {
			material =  Material.matchMaterial(ItemBukkitName).name();
			}catch (Exception e) {
				material = "ERROR";
			}
			return material;
		}else {
			return Itemname_i18n;
		}
	}
	
	
	public static void loadEnchi18n() {
		plugin.getLogger().info("Starting loading Enchantment i18n...");
		File enchi18nFile = new File(plugin.getDataFolder(), "enchi18n.yml");
		if (!enchi18nFile.exists()) {
			plugin.getLogger().info("Creating enchi18n.yml");
			plugin.saveResource("enchi18n.yml", true);
		}
		// Store it
	    enchi18n = YamlConfiguration.loadConfiguration(enchi18nFile);
		enchi18n.options().copyDefaults(true);
		YamlConfiguration enchi18nYAML = YamlConfiguration.loadConfiguration(new InputStreamReader(plugin.getResource("enchi18n.yml")));
		enchi18n.setDefaults(enchi18nYAML);
		Enchantment[] enchsi18n = Enchantment.values();
		for (Enchantment ench : enchsi18n) {
			String enchname = enchi18n.getString("enchi18n."+ench.getKey().getKey().toString().trim());
			if(enchname==null || enchname.equals("")) {
				plugin.getLogger().info("Found new ench ["+ench.getKey().getKey().toString()+"] ,add it in config...");
				enchi18n.set("enchi18n."+ench.getKey().getKey().toString().trim(),ench.getKey().getKey().toString().trim());
			}
		}
		try {
			enchi18n.save(enchi18nFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			plugin.getLogger().log(Level.WARNING, "Could not load/save transaction enchname from enchi18n.yml. Skipping.");
		}
		plugin.getLogger().info("Complete to load enchname i18n.");
	}
	public static String getEnchi18n(Enchantment key) {
		if(key==null) {
			return "ERROR";
		}
		String EnchString = key.getKey().getKey().toString().trim();
		String Ench_i18n = null;
		try {
			Ench_i18n = enchi18n.getString("enchi18n."+EnchString);
		}catch (Exception e) {
			e.printStackTrace();
			Ench_i18n = null;
		}
		if(Ench_i18n==null) {
			return EnchString;
		}else {
			return Ench_i18n;
		}
	}
	
	
	public static void loadPotioni18n() {
		plugin.getLogger().info("Starting loading Potion i18n...");
		File potioni18nFile = new File(plugin.getDataFolder(), "potioni18n.yml");
		if (!potioni18nFile.exists()) {
			plugin.getLogger().info("Creating potioni18n.yml");
			plugin.saveResource("potioni18n.yml", true);
		}
		// Store it
	    potioni18n = YamlConfiguration.loadConfiguration(potioni18nFile);
	    potioni18n.options().copyDefaults(true);
		YamlConfiguration potioni18nYAML = YamlConfiguration.loadConfiguration(new InputStreamReader(plugin.getResource("potioni18n.yml")));
		potioni18n.setDefaults(potioni18nYAML);
		PotionEffectType[] potionsi18n = PotionEffectType.values();
		for (PotionEffectType potion : potionsi18n) {
			if(potion!=null) {
				String potionname = potioni18n.getString("potioni18n."+potion.getName().trim());
				if(potionname == null || potionname.equals("")) {
					plugin.getLogger().info("Found new potion ["+potion.getName()+"] ,add it in config...");
					potioni18n.set("potioni18n."+potion.getName().trim(),potion.getName().trim());
				}
			}	
		}
		try {
			potioni18n.save(potioni18nFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			plugin.getLogger().log(Level.WARNING, "Could not load/save transaction potionname from potioni18n.yml. Skipping.");
		}
		plugin.getLogger().info("Complete to load potionname i18n.");
	}
	public static String getPotioni18n(PotionEffectType potion) {
		if(potion==null) {
			return "ERROR";
		}
		String PotionString = potion.getName().trim();
		String Potion_i18n = null;
		try {
			Potion_i18n = potioni18n.getString("potioni18n."+PotionString);
		}catch (Exception e) {
			e.printStackTrace();
			Potion_i18n = null;
		}
		if(Potion_i18n==null) {
			return PotionString;
		}else {
			return Potion_i18n;
		}
	}
}
