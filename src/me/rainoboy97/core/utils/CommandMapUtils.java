package me.rainoboy97.core.utils;

import java.lang.reflect.Field;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.craftbukkit.v1_6_R1.CraftServer;

public class CommandMapUtils {

	private static CommandMap cmap;

	public CommandMapUtils() {
		this.init();
	}
	
	public boolean init() {
		try {
			if (Bukkit.getServer() instanceof CraftServer) {
				final Field f = CraftServer.class.getDeclaredField("commandMap");
				f.setAccessible(true);
				cmap = (CommandMap) f.get(Bukkit.getServer());
				return true;
			}
		} catch (Exception e) {
			CoreLog.log(Level.SEVERE, "Could not load CommandMap: " + e);
			return false;
		}
		return false;
	}
	
	public static CommandMap getCMap() {
		return cmap != null ? cmap : null;
	}

}
