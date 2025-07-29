package ramune314159265.realoni;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
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

	@EventHandler
	public void onPlayerDied(PlayerDeathEvent e) {
		e.getPlayer().setGameMode(GameMode.SPECTATOR);
	}
}
