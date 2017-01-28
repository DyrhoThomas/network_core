package network.Core.NickName;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import network.Core.Main;

public class CommandListener implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof ConsoleCommandSender) { 
			sender.sendMessage(Main.geenSpelerMessage);
			
			//TODO console edition!
			return true;
		} else if(!(sender instanceof Player)) {
			sender.sendMessage(Main.geenSpelerMessage);
			return true;
		} else {
			//we weten dat het een speler is
			Player p = (Player) sender;
			
			if(p.hasPermission("dyrho.legendary")) {
				//permission check
				if(args.length == 1) {
					//args length check
					UpdateNick.openConnection();
					
					if(args[0].equalsIgnoreCase("off")) {
						if(UpdateNick.containsInMySQL(p)) {
							UpdateNick.removeInMySQL(p);
							p.sendMessage(Main.prefix + "Your nickname has been removed!");
							p.setCustomName(p.getName());
							p.setPlayerListName(p.getName());
							UpdateNick.closeConnection();
							return true;
						} else {
							p.sendMessage("§cYou don't have a nickname to remove!");
							UpdateNick.closeConnection();
							return true;
						}
					}
					
					String nick = args[0];
					if(p.hasPermission("dyrho.moderator")) {
						nick = nick.replace("&", "§");
					}
					if(UpdateNick.containsInMySQL(p)) {
						UpdateNick.updateInMySQL(p, nick);
					} else {
						UpdateNick.createInMySQL(p, nick);
					}
					p.sendMessage(Main.prefix + "Your nickname has been changed to §3" + nick + "§b!");
					p.setCustomName(nick);
					p.setPlayerListName(nick);
					UpdateNick.closeConnection();
					return true;
				} else {
					p.sendMessage(Main.prefix + "§cWrong arguments, please use /nick <nickname|off>");
					return true;
				}
			} else {
				p.sendMessage(Main.geenPermMessage);
				return true;
			}
		}
	}
}
