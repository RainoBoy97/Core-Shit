package me.rainoboy97.core;

import me.rainoboy97.core.utils.CoreLog;
import me.rainoboy97.core.utils.Txt;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin implements Listener {

	public static Core core;
	
	

	public void onDisable() {

	}

	public void onEnable() {
		Core.core = this;
		long timeStarted = System.currentTimeMillis();
		
		CoreLog.logWithColor("<green>Enabling");
		
		
		
		// Register events
		CoreLog.logWithColor(Txt.parse("<green>Successfully enabled (<gold>" + (System.currentTimeMillis() - timeStarted) + "ms<green>)"));
	}

	public static Core get() {
		return core;
	}
}