package network.Core.BanSystem.BanGui;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import network.Core.Main;
import network.Core.BanSystem.CheckBanned;
import network.Core.BanSystem.ExecuteBans;

public class GuiClicker implements Listener {
	
	static Connection connection = ExecuteBans.connection;
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if(e.getClickedInventory().getTitle().startsWith("§3Ban §b")) {
			
			List<String> silentLore = new ArrayList<String>();
			List<String> nonsilentLore = new ArrayList<String>();
			silentLore.add("§c");
			silentLore.add("§4Current:§c Silent");
			silentLore.add("§cBan someone without broadcasting it!");
			
			nonsilentLore.add("§c");
			nonsilentLore.add("§4Current:§c Not-Silent");
			nonsilentLore.add("§cBan someone without broadcasting it!");
			
			

			String whoIsBanned = e.getClickedInventory().getTitle().substring(8);
			
			//in de juiste inv geklikt!
			if(e.getCurrentItem().getType().equals(Material.SKULL_ITEM)) {
				String reason = "Unknown";
				
				if(e.getSlot() == 1) {
					reason = "Multiple hacks, client is possible!";
				} else if(e.getSlot() == 2) {
					reason = "Creating lagg!";
				} else if(e.getSlot() == 3) {
					reason = "Multiple times insulting the staff!";
				} else if(e.getSlot() == 4) {
						reason = "Threatening with DDOS!";
				}
				
				if(CheckBanned.isPermBanned(Bukkit.getOfflinePlayer(whoIsBanned))) {
					e.setCancelled(true);
					e.getWhoClicked().closeInventory();
					
						e.getWhoClicked().sendMessage(Main.prefix + "§cThis player has already been banned!");
				} else {
					if(!e.getClickedInventory().getItem(36).containsEnchantment(Enchantment.KNOCKBACK)) {

						Bukkit.broadcastMessage(
								"§3-----§bDyrhoBans§3-----\n" + 
								"§b" + whoIsBanned + "§3 has been banned by §b" + e.getWhoClicked().getName() + "\n" +
								"§3Duration of ban: §bAlways\n" +
								"§3Reason: §b" + reason + "\n" +
								"§3-----§bDyrhoBans§3-----\n"
								);
					}
					e.setCancelled(true);
					e.getWhoClicked().closeInventory();
					
					e.getWhoClicked().sendMessage(Main.prefix + "You have banned §3" + whoIsBanned + "§b from the server!");
					
					ExecuteBans.permBan((Player) e.getWhoClicked(), reason, Bukkit.getOfflinePlayer(whoIsBanned));
				}
			} if (e.getCurrentItem().getType().equals(Material.BLAZE_ROD)) {
				if(e.getCurrentItem().getEnchantments().containsKey(Enchantment.KNOCKBACK)) {
					ItemStack item = e.getCurrentItem();
					item.removeEnchantment(Enchantment.KNOCKBACK);
					ItemMeta itemMeta = item.getItemMeta();
					itemMeta.setLore(nonsilentLore);
					item.setItemMeta(itemMeta);
					e.getInventory().setItem(36, item);
					e.setCancelled(true);
				} else {
					ItemStack item = e.getCurrentItem();
					item.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
					ItemMeta itemMeta = item.getItemMeta();
					itemMeta.setLore(silentLore);
					item.setItemMeta(itemMeta);
					e.getInventory().setItem(36, item);
					e.setCancelled(true);
				}
			} if (e.getCurrentItem().getType().equals(Material.BARRIER)) {
				e.setCancelled(true);
				e.getWhoClicked().closeInventory();
			} if (e.getCurrentItem().getType().equals(Material.INK_SACK)) {
				String reason = "Unknown";
				long banTime = 0;
				String banTimeString = "Unknown";
				if(e.getCurrentItem().getDurability() == (short) 10) {
					
					//7 dagen ban
					
					banTime = 604800;
						banTimeString = "7 days";
					if(e.getSlot() == 19) {
						reason = "Abuse the chat multiple times!";
					}
				} else if (e.getCurrentItem().getDurability() == (short) 11) {
					//4 weken ban
					
					banTime = 4*7*24*60*60;

					banTimeString = "4 weeks";
					
					if(e.getSlot() == 20) {
						reason = "light hacks!";
					}
				} else if (e.getCurrentItem().getDurability() == (short) 14) {
					//12 weken ban
					
					banTime = 12*7*24*60*60;
						banTimeString = "12 weeks";
					
					if(e.getSlot() == 21) {
						reason = "Mild hacks";
					}
				} else if (e.getCurrentItem().getDurability() == (short) 1) {
					//26 weken ban
					
					banTime = 26*7*24*60*60;
					banTimeString = "26 weeks";
					
					if(e.getSlot() == 22) {
						reason = "heavy hacks";
					}
				}
				
				if(CheckBanned.isTempBanned(Bukkit.getOfflinePlayer(whoIsBanned))) {
					e.setCancelled(true);
					e.getWhoClicked().closeInventory();
					e.getWhoClicked().sendMessage(Main.prefix + "§cThis player has already been banned!");
				} else if(CheckBanned.isPermBanned(Bukkit.getOfflinePlayer(whoIsBanned))){
					e.setCancelled(true);
					e.getWhoClicked().closeInventory();
					
					e.getWhoClicked().sendMessage(Main.prefix + "§cThis player has already been banned!");
				} else {
					
					ExecuteBans.tempBan((Player) e.getWhoClicked(), reason, Bukkit.getOfflinePlayer(whoIsBanned), banTime);
					if (!e.getClickedInventory().getItem(36).containsEnchantment(Enchantment.KNOCKBACK)) {
						Bukkit.broadcastMessage(
								"§3-----§bDyrhoBans§3-----\n" + 
								"§b" + whoIsBanned + "§3 is banned by §b" + e.getWhoClicked().getName() + "\n" +
								"§3Duration of ban: §b" + banTimeString + "\n" +
								"§3Reason: §b" + reason + "\n" +
								"§3-----§bDyrhoBans§3-----\n"
								);
					}
					e.setCancelled(true);
					e.getWhoClicked().closeInventory();
					
					e.getWhoClicked().sendMessage(Main.prefix + "You have banned §3" + whoIsBanned + "§b from the server!");
				}
			}
		}
	}
}