package network.Core.BanSystem.BanGui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GuiOpener {
	
	public static void openBanGui(Player p, OfflinePlayer whoIsGonnaBanned) {
		Inventory inv = Bukkit.createInventory(null, 45, "§3Ban §b" + whoIsGonnaBanned.getName());
		
		addPermBanItems(inv);
		
		addTempBanItems(inv);
		
		addSideItems(inv);
		
		p.openInventory(inv);
	}
	
	public static void addTempBanItems(Inventory inv) {
		List<String> weekLore = new ArrayList<String>();
		weekLore.add("§c");
		weekLore.add("§3Duration: §b7 days");
		
		List<String> MaandLore = new ArrayList<String>();
		MaandLore.add("§c");
		MaandLore.add("§3Duration: §b4 weeks (+- 1 month)");

		List<String> drieMaandenLore = new ArrayList<String>();
		drieMaandenLore.add("§c");
		drieMaandenLore.add("§3Duration: §b12 weeks (+- 3 months)");

		List<String> halfJaarLore = new ArrayList<String>();
		halfJaarLore.add("§c");
		halfJaarLore.add("§3Duration: §b26 weeks (+- half a year)");
		
		ItemStack weekItem = new ItemStack(Material.INK_SACK);
		weekItem.setDurability((short) 10);
		ItemMeta weekMeta = weekItem.getItemMeta();
		weekMeta.setDisplayName("§3§lAbuse the chat multiple times!");
		weekMeta.setLore(weekLore);
		weekItem.setItemMeta(weekMeta);
		
		ItemStack lhItem = new ItemStack(Material.INK_SACK);
		lhItem.setDurability((short) 11);
		ItemMeta lhMeta = lhItem.getItemMeta();
		lhMeta.setDisplayName("§3§lLight hacks! (speed, nofall etc.)");
		lhMeta.setLore(MaandLore);
		lhItem.setItemMeta(lhMeta);

		ItemStack mhItem = new ItemStack(Material.INK_SACK);
		mhItem.setDurability((short) 14);
		ItemMeta mhMeta = mhItem.getItemMeta();
		mhMeta.setDisplayName("§3§lMild hacks (anti-knockback, spider etc.)");
		mhMeta.setLore(drieMaandenLore);
		mhItem.setItemMeta(mhMeta);

		ItemStack zhItem = new ItemStack(Material.INK_SACK);
		zhItem.setDurability((short) 1);
		ItemMeta zhMeta = zhItem.getItemMeta();
		zhMeta.setDisplayName("§3§lHeavy hacks! (Fly, kill-aura etc.)");
		zhMeta.setLore(halfJaarLore);
		zhItem.setItemMeta(zhMeta);
		
		inv.setItem(19, weekItem);
		inv.setItem(20, lhItem);
		inv.setItem(21, mhItem);
		inv.setItem(22, zhItem);
	}
	
	public static void addPermBanItems(Inventory inv) {
		List<String> permMeta = new ArrayList<String>();
		permMeta.add("§c");
		permMeta.add("§3Duration: §bForever");
		
		ItemStack hacks = new ItemStack(Material.SKULL_ITEM);
		ItemMeta hacksMeta = hacks.getItemMeta();
		hacksMeta.setDisplayName("§3§lMultiple hacks, client is possible!");
		hacksMeta.setLore(permMeta);
		hacks.setItemMeta(hacksMeta);
		
		ItemStack lag = new ItemStack(Material.SKULL_ITEM);
		ItemMeta lagMeta = lag.getItemMeta();
		lagMeta.setDisplayName("§3§lCreating lagg!");
		lagMeta.setLore(permMeta);
		lag.setItemMeta(lagMeta);
		
		ItemStack sbm = new ItemStack(Material.SKULL_ITEM);
		ItemMeta sbmMeta = sbm.getItemMeta();
		sbmMeta.setDisplayName("§3§lMultiple times insulting the staff!");
		sbmMeta.setLore(permMeta);
		sbm.setItemMeta(sbmMeta);
		

		ItemStack ddos = new ItemStack(Material.SKULL_ITEM);
		ItemMeta ddosMeta = ddos.getItemMeta();
		ddosMeta.setDisplayName("§3§lThreatening with DDOS!");
		ddosMeta.setLore(permMeta);
		ddos.setItemMeta(ddosMeta);
		
		inv.setItem(1, hacks);
		inv.setItem(2, lag);
		inv.setItem(3, sbm);
		inv.setItem(4, ddos);
	}
	
	public static void addSideItems(Inventory inv) {
		
		ItemStack closeInventory = new ItemStack(Material.BARRIER);
		ItemMeta closeMeta = closeInventory.getItemMeta();
		closeMeta.setDisplayName("§c§lClose");
		closeInventory.setItemMeta(closeMeta);
		
		ItemStack silent = new ItemStack(Material.BLAZE_ROD);
		ItemMeta silentMeta = silent.getItemMeta();
		silentMeta.setDisplayName("§c§lBan silent");
		List<String> silentLore = new ArrayList<String>();
		silentLore.add("§c");
		silentLore.add("§4Current:§c Not-Silent");
		silentLore.add("§cBan someone without broadcasting it!");
		silentMeta.setLore(silentLore);
		silent.setItemMeta(silentMeta);
		
		ItemStack glassPane = new ItemStack(Material.STAINED_GLASS_PANE);
		glassPane.setDurability((short) 15);
		
		inv.setItem(0, glassPane);
		inv.setItem(9, glassPane);
		inv.setItem(18, glassPane);
		inv.setItem(27, glassPane);
		inv.setItem(36, silent);
		inv.setItem(8, glassPane);
		inv.setItem(17, glassPane);
		inv.setItem(26, glassPane);
		inv.setItem(35, glassPane);
		inv.setItem(44, closeInventory);
	}
}
