package network.Core.BanSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class CheckBanned {
	
	static Connection connection;
	
	public static boolean isTempBanned(OfflinePlayer p) {
		try {
			boolean isBanned = false;
			
			openConnection();
			PreparedStatement sql = connection.prepareStatement("SELECT * FROM `tempbans` WHERE UUID=?;");
			sql.setString(1, p.getUniqueId().toString());
			ResultSet rset = sql.executeQuery();
			if(rset.next()) {
				isBanned = true;
			}
			rset.close();
			sql.close();
			
			closeConnection();
			return isBanned;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean isPermBanned(OfflinePlayer p) {
		try {
			boolean isBanned = false;
			
			openConnection();
			PreparedStatement sql = connection.prepareStatement("SELECT * FROM `permbans` WHERE UUID=?;");
			sql.setString(1, p.getUniqueId().toString());
			ResultSet rset = sql.executeQuery();
			if(rset.next()) {
				isBanned = true;
			}
			rset.close();
			sql.close();
			
			closeConnection();
			return isBanned;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
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
