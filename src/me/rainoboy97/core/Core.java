package me.rainoboy97.core;

import java.util.logging.Level;

import me.rainoboy97.core.commands.Core_cmd;
import me.rainoboy97.core.utils.CoreLog;
import me.rainoboy97.core.utils.Txt;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin implements Listener {

	public static Core core;
	
	

	public void onDisable() {

	}

	public void onEnable() {
		Core.core = this;
		long timeStarted = System.currentTimeMillis();
		
		CoreLog.logWithColor("<green>Enabling");
		
		this.registerCommands();
		
		// Register events
		CoreLog.logWithColor(Txt.parse("<green>Successfully enabled (<gold>" + (System.currentTimeMillis() - timeStarted) + "ms<green>)"));
	}
	
	private boolean registerCommands() {
		try {
			this.getCommand("core").setExecutor(new Core_cmd(this));
			return true;
		} catch (Exception e) {
			CoreLog.log(Level.WARNING, "Could not register commands!");
			e.printStackTrace();
			return false;
		}
	}

	public static Core get() {
		return core;
	}
	
	public static PluginDescriptionFile desc() {
		return core.getDescription();
	}
}