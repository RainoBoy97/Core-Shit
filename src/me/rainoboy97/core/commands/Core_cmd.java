package me.rainoboy97.core.commands;

import me.rainoboy97.core.Core;
import me.rainoboy97.core.handlers.CoreCmd;

import org.bukkit.command.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Core_cmd extends CoreCmd {

	private final Core plugin;

	public Core_cmd(Core plugin) {
		super(plugin, CSender.BOTH);
		this.plugin = plugin;
	}

	public boolean onCmd(Player player, ConsoleCommandSender console, Command command, String alias, String[] args) {
		
		return true;
	}

}
