package ramune314159265.realoni.skills;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import ramune314159265.realoni.Ground;
import ramune314159265.realoni.Realoni;

import java.util.*;
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

	static Map<Integer, Set<Location>> getLocations(Location center) {
		Map<Integer, Set<Location>> result = new HashMap<>();
		for (int rx = -radius; rx <= radius; rx++) {
			for (int rz = -radius; rz <= radius; rz++) {
				int distance = (int) Math.floor(Math.hypot(rx, rz));
				if(radius < distance) {
					continue;
				}
				int x = center.getBlockX() + rx;
				int z = center.getBlockZ() + rz;
				int y = Ground.getY(center.getWorld(), x, z);
				Location location = new Location(center.getWorld(), x, y, z);
				if (includeBlocks.contains(location.getBlock().getType())) {
					result.computeIfAbsent(distance, i -> new HashSet<>()).add(location);
				}
			}
		}
		return result;
	}

	@Override
	public void use(Player player) {
		Location center = player.getLocation().clone();
		Map<Integer, Set<Location>> locations = getLocations(center);
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
				int target = (int) Math.floor(((double) t / 59) * radius);
				locations.get(target).forEach(l -> l.getBlock().setType(Material.MAGMA_BLOCK));
			}
			if(60 <= t && t <= 159) {
				int target = (int) Math.floor(((double) (t - 60) / 99) * radius);
				locations.get(target).forEach(l -> l.getBlock().setType(Material.LAVA));
			}
			if(t == 160) {
				task.cancel();
			}
		}, 0, 1);
	}
}
