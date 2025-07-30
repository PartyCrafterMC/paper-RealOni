package ramune314159265.realoni;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

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

	@EventHandler
	public void onPlayerItemDamaged(PlayerItemDamageEvent e) {
		if(e.getItem().getType() != Material.SHIELD) {
			return;
		}
		e.setDamage(e.getItem().getType().getMaxDurability());
	}
}
