package ramune314159265.realoni.skills;

import io.papermc.paper.entity.TeleportFlag;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
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

		targetFakeAnimals.forEach(e -> e.setInvisible(true));
		player.getWorld().spawnParticle(
				Particle.CLOUD, player.getLocation(), 100, 0.1, 1, 0.1
		);
		player.setVelocity(new Vector(0, 1.5, 0));
		player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, -1, 4, false, false, false));

		AtomicReference<Location> startLocation = new AtomicReference<>();
		AtomicInteger tick = new AtomicInteger(-1);
		BukkitScheduler openExec = Bukkit.getScheduler();
		openExec.runTaskTimer(Realoni.getInstance(), (BukkitTask task) -> {
			int t = tick.addAndGet(1);
			player.getWorld().spawnParticle(
					Particle.END_ROD, player.getLocation(), 1, 0, 0, 0, 0, null, true
			);
			if(t == 20) {
				player.setAllowFlight(true);
				player.setFlying(true);
				startLocation.set(player.getLocation());
			}
			if(20 <= t && t <= 140) {
				double offsetY = Math.sin((t - 30) / 5d) * 2;
				player.teleport(startLocation.get().clone().add(0, offsetY, 0));
				for (int i = 0; i < targetFakeAnimals.size(); i++) {
					LivingEntity e = targetFakeAnimals.get(i);
					if(e.isInvisible() && 0.9 < Math.cos((2 * Math.PI) * ((double) i / requiredAnimalCount) + Math.pow((t - 20) / 20d, 1.8))) {
						e.setInvisible(false);
						player.getWorld().spawnParticle(
								Particle.END_ROD, e.getLocation(), 5, 0, 0, 0, 0.15, null, true
						);
					}
					double positionX = Math.cos((2 * Math.PI) * ((double) i / requiredAnimalCount) + Math.pow((t - 20) / 20d, 1.8))
							* (4 - easeInExpo((t - 20) / 120d) * 4) + player.getLocation().getX();
					double positionZ = Math.sin((2 * Math.PI) * ((double) i / requiredAnimalCount) + Math.pow((t - 20) / 20d, 1.8))
							* (4 - easeInExpo((t - 20) / 120d) * 4) + player.getLocation().getZ();
					e.teleport(new Location(player.getWorld(), positionX, player.getLocation().getY(), positionZ));
				}
			}
			if(t == 140) {
				targetFakeAnimals.forEach(Entity::remove);
				player.setFlying(false);
				player.setAllowFlight(false);
				player.setVelocity(new Vector(0, 1, 0));
			}
			if(t == 150) {
				player.setVelocity(new Vector(0, -1.5, 0));
				player.getWorld().setGameRule(GameRule.DO_TILE_DROPS, false);
			}
			if(t == 155) {
				breakSphere(startLocation.get().clone().add(0, -5, 0), 20);
				player.getWorld().createExplosion(startLocation.get().clone().add(0, -25, 0), 10f, false, true);
				player.getWorld().spawnParticle(
						Particle.CAMPFIRE_SIGNAL_SMOKE, startLocation.get().clone().add(0, -20, 0), 300, 3, 3, 3, 1, null, true
				);
				player.setVelocity(new Vector(0, 2.5, 0));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 20 * 15, 1, false, false, false));

				Realoni.processingGame.playerRoles.entrySet().stream()
						.filter(entry -> entry.getValue().isSurvivor())
						.forEach(entry -> {
							Vector direction = entry.getKey().getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
							entry.getKey().setVelocity(direction.multiply(1d / (0.01d * entry.getKey().getLocation().distance(player.getLocation()))).setY(1));
							entry.getKey().damage(1d / (0.005d * entry.getKey().getLocation().distance(player.getLocation())), player);
						});
			}
			if(t == 170) {
				player.removePotionEffect(PotionEffectType.RESISTANCE);
				player.getWorld().setGameRule(GameRule.DO_TILE_DROPS, true);
				task.cancel();
			}
		}, 0, 1);
	}

	public double easeInExpo(double x) {
		return x == 0 ? 0 : Math.pow(2, 10 * x - 10);
	}

	public void breakSphere(Location center, double radius) {
		World world = center.getWorld();
		int r = (int) Math.ceil(radius);

		for (int x = -r; x <= r; x++) {
			for (int y = -r; y <= r; y++) {
				for (int z = -r; z <= r; z++) {
					Location loc = center.clone().add(x, y, z);

					if (center.distance(loc) <= radius) {
						world.getBlockAt(loc).setType(Material.AIR);
					}
				}
			}
		}
	}
}
