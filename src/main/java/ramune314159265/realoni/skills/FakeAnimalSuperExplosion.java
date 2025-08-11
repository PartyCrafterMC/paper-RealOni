package ramune314159265.realoni.skills;

import io.papermc.paper.entity.TeleportFlag;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import ramune314159265.realoni.Realoni;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class FakeAnimalSuperExplosion extends Skill {
	static final int requiredAnimalCount = 10;

	@Override
	public String getName() {
		return "偽動物超爆破";
	}

	@Override
	public void use(Player player) {
		List<LivingEntity> fakeAnimals = Realoni.defaultWorld.getLivingEntities().stream()
				.filter(e -> FakeAnimal.targetEntities.contains(e.getType()) &&
						e.getPersistentDataContainer().getOrDefault(FakeAnimal.storageKey, PersistentDataType.BOOLEAN, false))
				.sorted(Comparator.comparing(e -> e.getLocation().distance(player.getLocation()), Comparator.naturalOrder()))
				.toList();
		if (fakeAnimals.size() < requiredAnimalCount) {
			player.sendMessage(Component.text("発動に必要な偽動物が足りない！").color(NamedTextColor.RED));
			return;
		}
		List<LivingEntity> targetFakeAnimals = fakeAnimals.subList(0, requiredAnimalCount);
		List<Location> targetFakeAnimalLocations = targetFakeAnimals.stream().map(Entity::getLocation).toList();

		player.getWorld().spawnParticle(
				Particle.CLOUD, player.getLocation(), 100, 0.1, 1, 0.1
		);
		player.setVelocity(new Vector(0, 1.75, 0));

		AtomicReference<Location> startLocation = new AtomicReference<>();
		AtomicInteger tick = new AtomicInteger(-1);
		BukkitScheduler openExec = Bukkit.getScheduler();
		openExec.runTaskTimer(Realoni.getInstance(), (BukkitTask task) -> {
			int t = tick.addAndGet(1);
			if(0 <= t && t <= 20) {
				for (int i = 0; i < targetFakeAnimals.size(); i++) {
					double positionX = Math.cos((2 * Math.PI) / ((double) i / requiredAnimalCount)) * 4 + player.getLocation().getX();
					double positionZ = Math.sin((2 * Math.PI) / ((double) i / requiredAnimalCount)) * 4 + player.getLocation().getY();
					targetFakeAnimals.get(i).teleport(
							new Location(
									player.getWorld(),
									targetFakeAnimalLocations.get(i).getX() + (positionX - targetFakeAnimalLocations.get(i).getX()) * (t / 20d),
									targetFakeAnimalLocations.get(i).getY() + (player.getLocation().getY() - targetFakeAnimalLocations.get(i).getY()) * (t / 20d),
									targetFakeAnimalLocations.get(i).getZ() + (positionZ - targetFakeAnimalLocations.get(i).getZ()) * (t / 20d)
							)
					);
				}
			}
			if(t == 20) {
				player.setAllowFlight(true);
				player.setFlying(true);
				startLocation.set(player.getLocation());
			}
			if(20 <= t && t <= 180) {
				double offsetY = Math.sin((t - 30) / 4d) * 2;
				player.teleport(startLocation.get().clone().add(0, offsetY, 0));
				for (int i = 0; i < targetFakeAnimals.size(); i++) {
					double positionX = Math.cos((2 * Math.PI) / ((double) i / requiredAnimalCount) + Math.pow(t - 20 / 10d, 2))
							* ((t - 20) * 4 / 160d) + player.getLocation().getX();
					double positionZ = Math.sin((2 * Math.PI) / ((double) i / requiredAnimalCount) + Math.pow(t - 20 / 10d, 2))
							* ((t - 20) * 4 / 160d) + player.getLocation().getY();
					targetFakeAnimals.get(i).teleport(
							new Location(player.getWorld(), positionX, player.getLocation().getY(), positionZ)
					);
				}
			}
			if(t == 180) {
				targetFakeAnimals.forEach(Entity::remove);
				player.setFlying(false);
				player.setAllowFlight(false);
				player.setVelocity(new Vector(0, 1, 0));
			}
			if(t == 200) {
				player.setVelocity(new Vector(0, -1, 0));
			}
		}, 0, 1);
	}
}
