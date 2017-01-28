package network.Core;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import network.Core.BanSystem.UnbanCommand;
import network.Core.BanSystem.BanGui.CommandListener;
import network.Core.BanSystem.BanGui.GuiClicker;

public class Main extends JavaPlugin {
	
	public static Main main;
	
	public static String prefix = "§3§l[§b§lDyrho§3§l] §b";
	public static String geenPermMessage = prefix + "§cSorry, you don't have permission to do this!";
	public static String geenSpelerMessage = prefix + "§Sorry, you have to be a player to do this!";
	
	public static void sendToServer(String server, String messageServerName, Player p) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("Connect");
			out.writeUTF(server);
		} catch (Exception e) {
			e.printStackTrace();
		}

		p.sendMessage(prefix + "You will be connected to " + messageServerName + "§b...");
		
		p.sendPluginMessage(main, "BungeeCord", b.toByteArray());
	}
	
	public void onEnable() {
		main = this;
		
		broadcast(prefix + "Starting up Core plugin...");
		
		broadcast(prefix + "Commands registreren...");
		getCommand("ban").setExecutor(new CommandListener());
		getCommand("unban").setExecutor(new UnbanCommand());
		getCommand("nick").setExecutor(new network.Core.NickName.CommandListener());
		getCommand("lobby").setExecutor(new network.Core.LobbyCommand.CommandListener());
		getCommand("hub").setExecutor(new network.Core.LobbyCommand.CommandListener());
		
		broadcast(prefix + "Registering events...");
		Bukkit.getPluginManager().registerEvents(new GuiClicker(), this);
		Bukkit.getPluginManager().registerEvents(new network.Core.BanSystem.JoinListener(), this);
		Bukkit.getPluginManager().registerEvents(new network.Core.NickName.JoinListener(), this);
		
		broadcast(prefix + "Starting up BungeeCord messaging channel...");
	    Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
	    
		broadcast(prefix + "Plugin has been started up!");
	}
	
	public static void broadcast(String message) {
		Bukkit.broadcastMessage(message);
	}
}
