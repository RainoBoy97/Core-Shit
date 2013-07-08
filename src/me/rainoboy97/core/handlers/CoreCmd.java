package me.rainoboy97.core.handlers;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;

import me.rainoboy97.core.Core;
import me.rainoboy97.core.utils.Txt;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

public abstract class CoreCmd implements CommandExecutor {

	protected final Core plugin;

	protected CommandSender cmdSender;
	protected CSender csender;
	protected int maxArgs;

	public CoreCmd(Core plugin, CSender sender, int maxArgs) {
		this.plugin = plugin;
		this.setSender(sender);
		this.maxArgs = maxArgs;
	}

	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		if (csender == CSender.PLAYER_ONLY) {
			if (isPlayer(sender)) {
				Player player = (Player) sender;
				this.cmdSender = player;
				if (!player.hasPermission("core." + command.getName())) {
					player.sendMessage(Txt.parse("<red>You do not have permission to use this command!"));
					return true;
				}
				if (!(args.length > maxArgs)) {
					return onCmd(player, null, command, alias, args);
				}
				player.sendMessage(Txt.parse("<red>Too many arguments (%s allowed)", maxArgs));
			}
			sender.sendMessage(Txt.parse("<red>This command can only be executed by a player!"));
		} else if (csender == CSender.CONSOLE_ONLY) {
			if (isConsole(sender)) {
				ConsoleCommandSender console = (ConsoleCommandSender) sender;
				this.cmdSender = console;
				if (!(args.length > maxArgs)) {
					return onCmd(null, console, command, alias, args);
				}
				console.sendMessage(Txt.parse("<red>Too many arguments (%s allowed)", maxArgs));
			}
			sender.sendMessage(Txt.parse("<red>This command can only be executed by console!"));
		} else if (csender == CSender.BOTH) {
			if (isPlayer(sender)) {
				Player player = (Player) sender;
				this.cmdSender = player;
				if (!player.hasPermission("core." + command.getName())) {
					player.sendMessage(Txt.parse("<red>You do not have permission to use this command!"));
					return true;
				}
				if (!(args.length > maxArgs)) {
					return onCmd(player, null, command, alias, args);
				}
				player.sendMessage(Txt.parse("<red>Too many arguments (%s allowed)", maxArgs));
			}
			if (isConsole(sender)) {
				ConsoleCommandSender console = (ConsoleCommandSender) sender;
				this.cmdSender = console;
				if (!(args.length > maxArgs)) {
					return onCmd(null, console, command, alias, args);
				}
				console.sendMessage(Txt.parse("<red>Too many arguments (%s allowed)", maxArgs));
			}
		}
		return false;
	}
	 
	public void registerCommand(String... aliases) {
		PluginCommand command = getCommand(aliases[0], plugin);
		command.setAliases(Arrays.asList(aliases));
		getCommandMap().register(plugin.getDescription().getName(), command);
		plugin.getCommand(aliases[0]).setExecutor(this);
	}
	 
	private PluginCommand getCommand(String name, Plugin plugin) {
		PluginCommand command = null;
	 
		try {
			Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
			c.setAccessible(true);
	 
			command = c.newInstance(name, plugin);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return command;
	}
	 
	private static CommandMap getCommandMap() {
		CommandMap commandMap = null;
	 
		try {
			if (Bukkit.getPluginManager() instanceof SimplePluginManager) {
				Field f = SimplePluginManager.class.getDeclaredField("commandMap");
				f.setAccessible(true);
	 
				commandMap = (CommandMap) f.get(Bukkit.getPluginManager());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	 
		return commandMap;
	}

	public void sendMsg(String msg) {
		this.cmdSender.sendMessage(Txt.parse(msg));
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
			this.csender = CSender.CONSOLE_ONLY;
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
