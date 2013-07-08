package me.rainoboy97.core.commands;

import me.rainoboy97.core.Core;
import me.rainoboy97.core.handlers.CoreCmd;
import me.rainoboy97.core.utils.Txt;

import org.bukkit.command.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Core_cmd extends CoreCmd {

	@SuppressWarnings("unused")
	private final Core plugin;

	public Core_cmd(Core plugin) {
		super(plugin, CSender.BOTH, 1);
		this.plugin = plugin;
		this.registerCommand("core", "c");
	}

	@Override
	public boolean onCmd(Player player, ConsoleCommandSender console, Command command, String alias, String[] args) {
		if (args.length == 0) {
			sendMsg(Txt.titleize(Core.desc().getName(), "<purple>", "<blue>"));
			sendMsg(Txt.center("<gray>Version <gold>" + Core.desc().getVersion()));
			sendMsg(Txt.center("<gray>Coded by <gold>" + Txt.implodeCommaAnd(Core.desc().getAuthors())));
			sendMsg(Txt.center(String.format("<darkaqua>Type <green>/%s ? <darkaqua>for help!", alias)));
			sendMsg(Txt.titleize(Core.desc().getName(), "<purple>", "<blue>"));
			
			return true;
		}
		if(args[0].equalsIgnoreCase("?")) {
			sendMsg(Txt.titleize(Core.desc().getName() + " help", "<purple>", "<blue>"));
			sendMsg(Txt.titleize(Core.desc().getName() + " help", "<purple>", "<blue>"));
			
			return true;
		} else {
			sendMsg("<red>Unknown argument!");
		}
		return true;
	}

}
