package network.Core.BanSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListener implements Listener {
	
	private static Connection connection;
	
	private static List<String> kickedPlayers = new ArrayList<String>();
	
	@EventHandler
	public void onConnectEvent(PlayerJoinEvent e) {
		openConnection();
		isBanned(e.getPlayer());
		if(kickedPlayers.contains(e.getPlayer().getName())) {
			e.setJoinMessage("");
		}
		closeConnection();
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		if(kickedPlayers.contains(e.getPlayer().getName())) {
			e.setQuitMessage("");
			kickedPlayers.remove(e.getPlayer().getName());
		}
	}
	
	public static void isBanned(Player p) {
		try {
			PreparedStatement sql = connection.prepareStatement("SELECT * FROM `permbans` WHERE UUID=?;");
			sql.setString(1, p.getUniqueId().toString());
			ResultSet resultSet = sql.executeQuery();
			if(resultSet.next()) {
				String uuidWhoBanned = resultSet.getString("bannedBy");
				OfflinePlayer bannedByWho = Bukkit.getOfflinePlayer(UUID.fromString(uuidWhoBanned));
				String reason = resultSet.getString("reason");
				resultSet.close();
				sql.close();
				p.kickPlayer(
						"§3You're still banned!\n" + 
								"§2\n" +
								"§3Banned by: §b" + bannedByWho.getName() + "\n" +
								"§3Reason: §b" + reason + "\n" + 
								"§3Ban expires: §bNever\n" + 
								"\n" +
								"\n§3Buy an unban:" +
								"\n§bhttp://shop.dyrhonetwork.nl"
								);
				kickedPlayers.add(p.getName());
			} else {
				//check if tempbanned
				PreparedStatement sql2 = connection.prepareStatement("SELECT * FROM `tempbans` WHERE UUID=?;");
				sql2.setString(1, p.getUniqueId().toString());
				ResultSet resultSet2 = sql2.executeQuery();
				if(resultSet2.next()) {
					//table contains player
					long unbanTime = resultSet2.getLong("unbanTime");
					if(unbanTime <= System.currentTimeMillis()) {
						//remove from table and let join
						resultSet2.close();
						sql2.close();
						
						PreparedStatement sql3 = connection.prepareStatement("DELETE FROM `tempbans` WHERE UUID=?;");
						sql3.setString(1, p.getUniqueId().toString());
						sql3.executeUpdate();
						sql3.close();
					} else {
						//kick
						String uuidWhoBanned = resultSet2.getString("bannedBy");
						String reason = resultSet2.getString("reason");
						OfflinePlayer bannedByWho = Bukkit.getOfflinePlayer(UUID.fromString(uuidWhoBanned));
						resultSet2.close();
						sql2.close();
						
						Date date = new Date(unbanTime);
						DateFormat formatter = new SimpleDateFormat("dd/MM/YYYY");
						DateFormat formatter2 = new SimpleDateFormat("HH:mm:ss");
						String dateFormated = formatter.format(date);
						String timeFormated = formatter2.format(date);
						
						p.kickPlayer(
								"§3You're still banned!\n" + 
										"§2\n" +
										"§3Banned by: §b" + bannedByWho.getName() + "\n" +
										"§3Reason: §b" + reason + "\n" + 
										"§3Ban expires: §b" + dateFormated + " om " + timeFormated + "\n" + 
										"\n" +
										"\n§3Buy an unban:" +
										"\n§bhttp://shop.dyrhonetwork.nl"
										);
						kickedPlayers.add(p.getName());
					}
				}
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
}
