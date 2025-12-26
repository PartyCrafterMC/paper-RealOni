package ramune314159265.realoni;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Enemy;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import ramune314159265.realoni.roles.Oni;
import ramune314159265.realoni.roles.Spectator;

import java.util.Objects;

public class PluginListener implements Listener {
	@EventHandler
	public void onPlayerJoined(PlayerJoinEvent e) {
		Realoni.playerInitialize(e.getPlayer());
		if (Objects.isNull(Realoni.processingGame)) {
			return;
		}
		if (Realoni.processingGame.playerRoles.containsKey(e.getPlayer())) {
			return;
		}
		e.getPlayer().sendMessage(Component.text("ゲームが進行中です(スペクテイターモードになりました)").color(NamedTextColor.GREEN));
		Realoni.processingGame.playerInitialize(e.getPlayer(), new Spectator(e.getPlayer()));
	}

	@EventHandler
	public void onBlockBroken(BlockBreakEvent e) {
		Supply.brokenHandle(e);
	}

	@EventHandler
	public void onPlayerDied(PlayerDeathEvent e) {
		if (Objects.isNull(Realoni.processingGame)) {
			return;
		}
		if (!Objects.isNull(Realoni.processingGame.getPlayerRole(e.getPlayer()).getDeathMessage())) {
			Realoni.broadcast(Component.text(
					Realoni.processingGame.getPlayerRole(e.getPlayer()).getDeathMessage()
			));
		}

		Realoni.processingGame.playerInitialize(e.getPlayer(), new Spectator(e.getPlayer()));
	}

	@EventHandler
	public void onPlayerItemDamaged(PlayerItemDamageEvent e) {
		if (e.getItem().getType() != Material.SHIELD) {
			return;
		}
		e.setDamage(e.getItem().getType().getMaxDurability());
	}

	@EventHandler
	public void onCreatureSpawned(CreatureSpawnEvent e) {
		if (!(e.getEntity() instanceof Enemy enemy)) {
			return;
		}
		Oni.oniTeam.addEntity(enemy);
	}
}
