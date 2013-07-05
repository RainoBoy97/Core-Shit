package me.rainoboy97.core.utils;

import static org.bukkit.ChatColor.AQUA;
import static org.bukkit.ChatColor.BOLD;
import static org.bukkit.ChatColor.RED;

import java.io.File;
import java.util.logging.Level;

import me.rainoboy97.core.Core;
import net.minecraft.server.v1_6_R1.MinecraftServer;

import org.bukkit.entity.Player;

public class RestartUtil {

	public static void restart(String message) {
		try {
			String startupScript = "START.bat";
			final File file = new File(startupScript);
			if (file.isFile()) {
				for (Player p : Core.get().getServer().getOnlinePlayers()) {
					p.kickPlayer(message);
				}
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException ex) {}
				
				MinecraftServer.getServer().ag().a();

				try {
					Thread.sleep(100);
				} catch (InterruptedException ex) {}

				try {
					MinecraftServer.getServer().stop();
				} catch (Throwable t) {}

				Thread shutdownHook = new Thread() {
					public void run() {
						try {
							String os = System.getProperty("os.name").toLowerCase();
							if (os.contains("win")) {
								Runtime.getRuntime().exec("cmd /c start " + file.getPath());
							} else {
								Runtime.getRuntime().exec(new String[] { "sh", file.getPath() });
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				};

				shutdownHook.setDaemon(true);
				Runtime.getRuntime().addShutdownHook(shutdownHook);
			} else {
				CoreLog.log(Level.WARNING, "Could not find the startup script " + file.getName() + "! Stopping server...");
			}
			System.exit(0);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void restartDefaultMessage() {
		restart(BOLD.toString() + RED + "Server restarting! " + BOLD.toString() + AQUA + "Please rejoin!");
	}
	
}
