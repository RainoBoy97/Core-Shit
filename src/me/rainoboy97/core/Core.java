package me.rainoboy97.core;

import java.io.File;
import java.util.logging.Level;

import me.rainoboy97.core.commands.Core_cmd;
import me.rainoboy97.core.utils.CoreLog;
import me.rainoboy97.core.utils.Txt;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {

	public static Core core;

	public static String lang_path;

	public void onDisable() {

	}

	public void onEnable() {
		Core.core = this;
		Core.lang_path = this.getDataFolder() + File.separator + "lang" + File.separator;
		long timeStarted = System.currentTimeMillis();

		CoreLog.logWithColor("<green>Enabling");
		
		this.registerCommands();
		
		// Register events
		CoreLog.logWithColor(Txt.parse("<green>Successfully enabled (<gold>" + (System.currentTimeMillis() - timeStarted) + "ms<green>)"));
	}

	private boolean registerCommands() {
		try {
			new Core_cmd(this);
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