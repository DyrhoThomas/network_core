package network.Core.BanSystem;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import network.Core.Main;

public class ExecuteBans {
	
	static Main main = Main.main;
	
	public static Connection connection;
	
	public static void tempBan(Player bannedByWho, String reason, OfflinePlayer whoIsBanned, long banDurationInSeconds) {
		long banDurationInMillis = banDurationInSeconds * 1000;
		long currentTime = System.currentTimeMillis();
		long unbanTime = currentTime + banDurationInMillis;
		
		Date unbanDate = new Date(unbanTime);
		DateFormat formatter = new SimpleDateFormat("dd/MM/YYYY");
		DateFormat formatter2 = new SimpleDateFormat("HH:mm:ss");
		String dateFormatted = formatter.format(unbanDate);
		String timeFormatted = formatter2.format(unbanDate);
		
		if(whoIsBanned.isOnline()) {
			Player whoIsBannedOnline = Bukkit.getPlayer(whoIsBanned.getName());
			whoIsBannedOnline.kickPlayer(
					"§3You have been banned!\n" + 
					"§2\n" +
					"§3Banned by: §b" + bannedByWho.getName() + "\n" +
					"§3Reason: §b" + reason + "\n" + 
					"§3Ban expires: §b" + dateFormatted + " om " + timeFormatted + "\n" + 
					"\n" + 
					"\n§Buy an unban:" +
					"\n§bhttp://shop.dyrhonetwork.nl"
					);
		}
		
		openConnection();
		try {
			PreparedStatement sql = connection.prepareStatement("INSERT INTO `tempbans` values (?,?,?,?);");
			sql.setString(1, whoIsBanned.getUniqueId().toString());
			sql.setLong(2, unbanTime);
			sql.setString(3, reason);
			sql.setString(4, bannedByWho.getUniqueId().toString());
			sql.executeUpdate();
			sql.close();
			closeConnection();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		
		
	}
	
	public static void permBan(Player bannedByWho, String reason, OfflinePlayer whoIsBanned) {
		if(whoIsBanned.isOnline()) {
			Player banned = (Player) whoIsBanned;
			banned.kickPlayer(
					"§3You have been banned!\n" + 
					"§2\n" +
					"§3Banned by: §b" + bannedByWho.getName() + "\n" +
					"§3Reason: §b" + reason + "\n" + 
					"§3Ban expires: §bNever\n" + 
					"\n" + 
					"\n§Buy an unban:" +
					"\n§bhttp://shop.dyrhonetwork.nl"
					);
		}
		openConnection();
		try {
			PreparedStatement sql = connection.prepareStatement("INSERT INTO `permbans` values (?,?,?);");
			sql.setString(1, whoIsBanned.getUniqueId().toString());
			sql.setString(2, reason);
			sql.setString(3, bannedByWho.getUniqueId().toString());
			sql.executeUpdate();
			sql.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
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
}
