package ramune314159265.realoni.skills;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import ramune314159265.realoni.Ground;
import ramune314159265.realoni.Realoni;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class FloorLava extends SkillUsable {
	@Override
	public String getName() {
		return "床溶岩";
	}
	static final int radius = 50;
	static final Set<Material> includeBlocks = Sets.newHashSet(Iterables.concat(
			Tag.DIRT.getValues(),
			Tag.SNOW.getValues(),
			Tag.SAND.getValues(),
			new HashSet<>(List.of(
			))));

	static Set<Location> getLocations(Location center) {
		Set<Location> result = new HashSet<>();
		for (int rx = -radius; rx <= radius; rx++) {
			for (int rz = -radius; rz <= radius; rz++) {
				if(Math.pow(radius, 2) < Math.pow(rx, 2) + Math.pow(rz, 2)) {
					continue;
				}
				int x = center.getBlockX() + rx;
				int z = center.getBlockZ() + rz;
				int y = Ground.getY(center.getWorld(), x, z);
				Location location = new Location(center.getWorld(), x, y, z);
				if (includeBlocks.contains(location.getBlock().getType())) {
					result.add(location);
				}
			}
		}
		return result;
	}

	@Override
	public void use(Player player) {
		Location center = player.getLocation().clone();
		Set<Location> locations = getLocations(center);
		Collection<? extends Player> players = Realoni.getInstance().getServer().getOnlinePlayers();
		BukkitScheduler scheduler = Bukkit.getScheduler();
		AtomicInteger tick = new AtomicInteger(-1);
		scheduler.runTaskTimer(Realoni.getInstance(), (BukkitTask task) -> {
			center.getNearbyPlayers(radius).forEach(p -> {
				p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, SoundCategory.MASTER, 1, 1);
				p.getWorld().spawnParticle(
						Particle.LAVA, p.getLocation(),
						2, 1, 0, 1, 1
				);
			});
			int t = tick.addAndGet(1);
			if(0 <= t && t <= 59) {
				players.forEach(p -> {
					locations.forEach(l -> p.sendBlockDamage(l, (float) t / 60));
				});
			}
			if (60 == t) {
				locations.forEach(l -> l.getBlock().setType(Material.MAGMA_BLOCK));
			}
			if(61 <= t && t <= 119) {
				players.forEach(p -> {
					locations.forEach(l -> p.sendBlockDamage(l, (float) (t - 60) / 60));
				});
			}
			if (120 == t) {
				locations.forEach(l -> l.getBlock().setType(Material.LAVA));
				task.cancel();
			}
		}, 0, 1);
	}
}
