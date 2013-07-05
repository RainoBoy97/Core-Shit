package me.rainoboy97.core.handlers;

import me.rainoboy97.core.Core;
import me.rainoboy97.core.utils.Txt;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public abstract class CoreCmd implements CommandExecutor {

	protected final Core plugin;

	protected CommandSender cmdSender;

	protected CSender csender;

	public CoreCmd(Core plugin, CSender sender) {
		this.plugin = plugin;
		this.setSender(sender);
	}

	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		if (csender == CSender.PLAYER_ONLY) {
			if (isPlayer(sender)) {
				Player player = (Player) sender;
				this.cmdSender = player;
				return onCmd(player, null, command, alias, args);
			}
			sender.sendMessage(Txt.parse("<red>This command can only be executed by a player!"));
		} else if (csender == CSender.CONSOLE_ONLY) {
			if(isConsole(sender)) {
				ConsoleCommandSender console = (ConsoleCommandSender) sender;
				this.cmdSender = console;
				return onCmd(null, console, command, alias, args);
			}
			sender.sendMessage(Txt.parse("<red>This command can only be executed by console!"));
		} else if (csender == CSender.BOTH){
			if (isPlayer(sender)) {
				Player player = (Player) sender;
				this.cmdSender = player;
				return onCmd(player, null, command, alias, args);
			}
			if(isConsole(sender)) {
				ConsoleCommandSender console = (ConsoleCommandSender) sender;
				this.cmdSender = console;
				return onCmd(null, console, command, alias, args);
			}
		}
		return true;
	}

	public abstract boolean onCmd(Player player, ConsoleCommandSender console, Command command, String alias, String[] args);

	public boolean isPlayer(CommandSender sender) {
		return sender instanceof Player ? true : false;
	}

	public boolean isConsole(CommandSender sender) {
		return sender instanceof ConsoleCommandSender ? true : false;
	}

	public void setSender(CSender sender) {
		switch (sender) {
		case PLAYER_ONLY:
			this.csender = CSender.PLAYER_ONLY;
			break;
		case CONSOLE_ONLY:
			this.csender = CSender.PLAYER_ONLY;
			break;
		case BOTH:
			this.csender = CSender.BOTH;
			break;
		default:
			this.csender = CSender.BOTH;
			break;
		}
	}

	protected enum CSender {
		PLAYER_ONLY, CONSOLE_ONLY, BOTH;
	}

}
