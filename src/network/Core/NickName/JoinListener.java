package network.Core.NickName;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		String name = p.getName();
		
		UpdateNick.openConnection();
		
		if(UpdateNick.containsInMySQL(e.getPlayer())) {
			name = UpdateNick.getNickName(p);
		}
		
		p.setPlayerListName(name);
		p.setCustomName(name);
		
		UpdateNick.closeConnection();
	}
}
