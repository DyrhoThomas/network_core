package network.Core.LobbyCommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import network.Core.Main;

public class CommandListener implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Main.geenSpelerMessage);
			return true;
		} else {
			Player p = (Player) sender;
			
			Main.sendToServer("lobby", "the Lobby", p);
			
			return true;
		}
	}
}
