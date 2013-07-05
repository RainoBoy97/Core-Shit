package me.rainoboy97.core.utils;

import static org.bukkit.ChatColor.*;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerUtil {

	public static Player matchPlayer(CommandSender sender, String name, boolean silent) {
		List<Player> player = Bukkit.matchPlayer(name);
		if (player.isEmpty()) {
			if (!silent)
				sender.sendMessage(RED + "No player matches found for " + AQUA + BOLD.toString() + name);
			return null;
		} else if (player.size() > 1) {
			if (!silent)
				sender.sendMessage(RED + "Found " + GOLD + BOLD.toString() + player.size() + RED + " player matches for " + AQUA + BOLD.toString() + name);
			return null;
		} else if (player.size() == 1) {
			return player.get(0);
		}
		return null;

	}

}
