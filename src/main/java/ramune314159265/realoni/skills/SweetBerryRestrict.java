package ramune314159265.realoni.skills;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import ramune314159265.realoni.Ground;
import ramune314159265.realoni.Realoni;

import java.util.HashSet;
import java.util.Set;

public class SweetBerryRestrict extends Skill implements Listener {
	@Override
	public String getName() {
		return "スイートベリーの拘束";
	}

	static final Set<Location> sweetBerryLocations = new HashSet<>();

	static boolean isTargetBlock(Block block) {
		if (block.getType() != Material.SWEET_BERRY_BUSH) {
			return false;
		}
		BlockData data = block.getBlockData();
		if (!(data instanceof Ageable ageable)) {
			return false;
		}
		return 2 <= ageable.getAge(); // ageが2以上だとスイートベリーを回収することが出来る
	}

	static void searchSweetBerry(Chunk chunk) {
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				Block blockLocation = chunk.getBlock(x, 0, z);
				Block checkBlock = Realoni.defaultWorld.getBlockAt(blockLocation.getX(), Ground.getY(Realoni.defaultWorld, blockLocation.getX(), blockLocation.getZ()) + 1, blockLocation.getZ());
				if (isTargetBlock(checkBlock)) {
					sweetBerryLocations.add(checkBlock.getLocation());
				}
			}
		}
	}

	static {
		Realoni.getInstance().getServer().getPluginManager().registerEvents(new SweetBerryRestrict(), Realoni.getInstance());

		for (Chunk chunk : Realoni.defaultWorld.getLoadedChunks()) {
			searchSweetBerry(chunk);
		}

		BukkitScheduler openExec = Bukkit.getScheduler();
		openExec.runTaskTimer(Realoni.getInstance(), (BukkitTask task) -> {
			for (Location location : sweetBerryLocations) {
				if(0.3 < Math.random()) {
					continue;
				}
				if (!isTargetBlock(location.getBlock())){
					sweetBerryLocations.remove(location);
					continue;
				}
				Realoni.defaultWorld.spawnParticle(
						Particle.DUST, location.toCenterLocation(),
						10, 0.3, 0.3, 0.3, 0.05, new Particle.DustOptions(Color.BLACK, 2f), true
				);
			}
		}, 1, 3);
	}

	@EventHandler
	public void chunkLoaded(ChunkLoadEvent e) {
		if(e.getWorld() != Realoni.defaultWorld) {
			return;
		}
		searchSweetBerry(e.getChunk());
	}

	@EventHandler
	public void entityDamagedByBlock(EntityDamageByBlockEvent e) {
		if(!(e.getEntity() instanceof Player player)){
			return;
		}
		if(!e.getDamager().getType().equals(Material.SWEET_BERRY_BUSH)) {
			return;
		}
		if(!Realoni.processingGame.getPlayerRole(player).isSurvivor()) {
			e.setCancelled(true);
			return;
		}

		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 0, false, false, false));
		player.setFireTicks(60);
	}

	@EventHandler
	public void playerItemConsumed(PlayerItemConsumeEvent e){
		if(!e.getItem().getType().equals(Material.SWEET_BERRIES)){
			return;
		}
		if (!Realoni.processingGame.getPlayerRole(e.getPlayer()).isSurvivor()) {
			return;
		}

		e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.POISON, 60, 0, false, false, false));
	}
}
