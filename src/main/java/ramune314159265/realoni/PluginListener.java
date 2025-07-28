package ramune314159265.realoni;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PluginListener implements Listener {
	@EventHandler
	public void onPlayerJoined(PlayerJoinEvent e) {
		Realoni.playerInitialize(e.getPlayer());
	}

	@EventHandler
	public void onBlockBroken(BlockBreakEvent e) {
		Supply.brokenHandle(e);
	}
}
