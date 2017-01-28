package network.Core.NickName;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class UpdateNick {
	
	static Connection connection;
	
	public static String getNickName(Player p) {
		try {
			PreparedStatement sql = connection.prepareStatement("SELECT * FROM `nicknames` WHERE UUID=?;");
			sql.setString(1, p.getUniqueId().toString());
			ResultSet rset = sql.executeQuery();
			String nick;
			if(rset.next()) {
				nick = rset.getString(2);
			} else {
				nick = p.getName();
			}
			rset.close();
			sql.close();
			return nick;
		} catch (SQLException e) {
			e.printStackTrace();
			Bukkit.getLogger().warning(e.getMessage());
			return null;
		}
	}
	public static boolean containsInMySQL(Player p) {
		boolean contains = false;
		try {
			PreparedStatement sql = connection.prepareStatement("SELECT * FROM `nicknames` WHERE UUID=?;");
			sql.setString(1, p.getUniqueId().toString());
			ResultSet rset = sql.executeQuery();
			if(rset.next()) {
				contains = true;
			}
			rset.close();
			sql.close();
			return contains;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public static void createInMySQL(Player p, String nick) {
		try {
			PreparedStatement sql = connection.prepareStatement("INSERT INTO `nicknames` values(?,?);");
			sql.setString(1, p.getUniqueId().toString());
			sql.setString(2, nick);
			sql.executeUpdate();
			sql.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void updateInMySQL(Player p, String nick) {
		try {
			PreparedStatement sql = connection.prepareStatement("UPDATE `nicknames` SET nick=? WHERE UUID=?;");
			sql.setString(1, nick);
			sql.setString(2, p.getUniqueId().toString());
			sql.executeUpdate();
			sql.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void removeInMySQL(Player p) {
		try {
			PreparedStatement sql = connection.prepareStatement("DELETE FROM `nicknames` WHERE UUID=?;");
			sql.setString(1, p.getUniqueId().toString());
			sql.executeUpdate();
			sql.close();
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
}
