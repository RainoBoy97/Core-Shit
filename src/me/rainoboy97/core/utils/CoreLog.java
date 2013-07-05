package me.rainoboy97.core.utils;

import java.util.logging.Level;

import me.rainoboy97.core.Core;

public class CoreLog {
	
	private static Core plugin = Core.get();
	private static final String PREFIX = "[" + plugin.getDescription().getName() + "] ";
	
	/** Logging with level **/
	public static void log(Level level, String msg) {
		plugin.getLogger().log(level, Txt.parse(msg));
	}
	
	/** Logging with level INFO **/
	public static void log(String msg) {
		log(Level.INFO, msg);
	}
	
	/** Logging with level INFO and color **/
	public static void logWithColor(String msg) {
		plugin.getServer().getConsoleSender().sendMessage(PREFIX + Txt.parse(msg));
	}

}
