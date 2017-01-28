package network.Core.BanSystem.BanGui;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import network.Core.Main;

public class CommandListener implements CommandExecutor {
	
	Main main = Main.main;
	
	@SuppressWarnings({ "deprecation", "static-access" })
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(!p.hasPermission("dyrho.moderator")) {
				p.sendMessage(main.geenPermMessage);
				return true;
			}
			if(!(args.length == 1)) {
				p.sendMessage(main.prefix + "§cWrong arguments! Please use /ban <player>");
				return true;
			}
			if(Bukkit.getOfflinePlayer(args[0]) == null) {
				sender.sendMessage(main.prefix + "Sorry, this player can't be found!");
				return true;
			} else {
				//speler is offline
				OfflinePlayer ofp = Bukkit.getOfflinePlayer(args[0]);
				GuiOpener.openBanGui(p, ofp);
				return true;
			}
		} else {
			sender.sendMessage(main.prefix + main.geenSpelerMessage);
			return true;
		}
	}

}
