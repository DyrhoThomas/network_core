package network.Core.BanSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import network.Core.Main;

public class UnbanCommand implements CommandExecutor {
	
	static Connection connection;
	
	@SuppressWarnings("deprecation")
	public static void unbanPlayer(String playerToUnbanName, CommandSender sender) {
		openConnection();
		
		boolean unbanned = false;
		
		//UNBAN IN PERMBAN
		try {
			PreparedStatement sql = connection.prepareStatement("SELECT * FROM `permbans` WHERE UUID=?;");
			sql.setString(1, Bukkit.getOfflinePlayer(playerToUnbanName).getUniqueId().toString());
			ResultSet resultSet = sql.executeQuery();
			if(resultSet.next()) {
				PreparedStatement sql2 = connection.prepareStatement("DELETE FROM `permbans` WHERE UUID=?;");
				sql2.setString(1, Bukkit.getOfflinePlayer(playerToUnbanName).getUniqueId().toString());
				sql2.executeUpdate();
				sql2.close();
				unbanned = true;
			}
			resultSet.close();
			sql.close();
			
			//UNBAN IN TEMPBAN
			PreparedStatement sql3 = connection.prepareStatement("SELECT * FROM `tempbans` WHERE UUID=?;");
			sql3.setString(1, Bukkit.getOfflinePlayer(playerToUnbanName).getUniqueId().toString());
			ResultSet resultSet3 = sql3.executeQuery();
			if(resultSet3.next()) {
				PreparedStatement sql4 = connection.prepareStatement("DELETE FROM `tempbans` WHERE UUID=?;");
				sql4.setString(1, Bukkit.getOfflinePlayer(playerToUnbanName).getUniqueId().toString());
				sql4.executeUpdate();
				sql4.close();
				unbanned = true;
			}
			resultSet3.close();
			sql3.close();
			
			closeConnection();
			
			if(unbanned) {Bukkit.broadcastMessage(
					"§3-----§bDyrhoBans§3-----\n" + 
					"§b" + Bukkit.getOfflinePlayer(playerToUnbanName).getName() + "§3 has been unbanned by §b" + sender.getName() + "\n" +
					"§3-----§bDyrhoBans§3-----\n"
					);
				sender.sendMessage(Main.prefix + "You have unbanned §3" + Bukkit.getOfflinePlayer(playerToUnbanName).getName() + "§b!");
				return;
			} else {
				sender.sendMessage(Main.prefix + "§cCouldn't unban §4" + playerToUnbanName + "§c!");
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static synchronized void openConnection() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://37.34.63.229:3306/thomzrl_bans",
					"thomzrl_bans", "lFtnEC1Ur");
		} catch (SQLException e) {
			e.printStackTrace();
			Bukkit.getConsoleSender().sendMessage("§4[§cERROR§4]§c Error creating MySQL connection: " + e.getMessage());
		}
	}
	
	public static synchronized void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			Bukkit.getConsoleSender().sendMessage("§4[§cERROR§4]§c Error closing MySQL connection: " + e.getMessage());
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("dyrho.eigenaar")) {
			sender.sendMessage(Main.geenPermMessage);
			return true;
		} if (args.length != 1) {
			sender.sendMessage(Main.prefix + "§cWrong arguments! Please use /ban <player>");
			return true;
		}
		String playerToUnbanName = args[0];
		
		unbanPlayer(playerToUnbanName, sender);
		return true;
	}
}
