package ramune314159265.realoni.skills;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import ramune314159265.realoni.Realoni;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Mimicry extends Skill {
	static final Material[] flameMaterials = {
			Material.AIR,
			Material.RED_STAINED_GLASS,
			Material.YELLOW_STAINED_GLASS,
			Material.LIGHT_BLUE_STAINED_GLASS,
			Material.GREEN_STAINED_GLASS,
			Material.LIME_STAINED_GLASS,
	};

	@Override
	public String getName() {
		return "擬態";
	}

	@Override
	public void use(Player player) {
		List<Player> nearbyPlayers = player.getLocation().getNearbyPlayers(20)
				.stream()
				.filter(p -> {
					if(p.equals(player)){
						return false;
					}
					return Realoni.processingGame.getPlayerRole(p).isSurvivor();
				}).toList();
		if(nearbyPlayers.isEmpty()) {
			return;
		}
		Player targetPlayer = nearbyPlayers.get(new Random().nextInt(nearbyPlayers.size()));
		targetPlayer.sendMessage(Component.text("ガラスの檻に閉じ込められた！ 矢を当ててもらえば壊せそう...").color(NamedTextColor.LIGHT_PURPLE));

		EntityEquipment playerEquipment = player.getEquipment();
		ItemStack[] savedEquipments = {
				Optional.ofNullable(playerEquipment.getHelmet()).orElse(new ItemStack(Material.AIR)),
				Optional.ofNullable(playerEquipment.getChestplate()).orElse(new ItemStack(Material.AIR)),
				Optional.ofNullable(playerEquipment.getLeggings()).orElse(new ItemStack(Material.AIR)),
				Optional.ofNullable(playerEquipment.getBoots()).orElse(new ItemStack(Material.AIR))
		};
		playerEquipment.setHelmet(Optional.ofNullable(targetPlayer.getEquipment().getHelmet()).orElse(new ItemStack(Material.AIR)));
		playerEquipment.setChestplate(Optional.ofNullable(targetPlayer.getEquipment().getChestplate()).orElse(new ItemStack(Material.AIR)));
		playerEquipment.setLeggings(Optional.ofNullable(targetPlayer.getEquipment().getLeggings()).orElse(new ItemStack(Material.AIR)));
		playerEquipment.setBoots(Optional.ofNullable(targetPlayer.getEquipment().getBoots()).orElse(new ItemStack(Material.AIR)));
		player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, -1, 1, true));

		targetPlayer.getLocation().getWorld().spawnParticle(
				Particle.CLOUD, targetPlayer.getLocation(), 100, 0.1, 1, 0.1
		);

		AtomicInteger health = new AtomicInteger(5);
		ArmorStand fixedPlatform = (ArmorStand) player.getLocation().getWorld().spawnEntity(player.getLocation().add(0, 15 ,0), EntityType.ARMOR_STAND);
		fixedPlatform.setVisible(false);
		fixedPlatform.setMarker(true);
		BlockDisplay flameBlock = (BlockDisplay) player.getLocation().getWorld().spawnEntity(player.getLocation().add(0, 15 ,0), EntityType.BLOCK_DISPLAY);
		flameBlock.setBlock(flameMaterials[health.get()].createBlockData());
		flameBlock.setInterpolationDuration(2);
		Vector3f from = new Vector3f(1, 1, 1).normalize();
		Vector3f to = new Vector3f(0, 0, 1);
		Quaternionf rotation = new Quaternionf().rotationTo(from, to);
		flameBlock.setTransformation(new Transformation(
				new Vector3f(0,0, 0),
				new AxisAngle4f(rotation),
				new Vector3f(2, 2, 2),
				new AxisAngle4f()
		));
		final long startAt = Instant.now().toEpochMilli();
		BukkitScheduler openExec = Bukkit.getScheduler();
		openExec.runTaskTimer(Realoni.getInstance(), (BukkitTask task) -> {
			targetPlayer.setRotation(0, 90);
			fixedPlatform.addPassenger(targetPlayer);
			float spinRad = (float) (Instant.now().toEpochMilli() - startAt) / 1000;
			Quaternionf spin = new Quaternionf().rotateAxis(spinRad, 0, 0, 1);
			Quaternionf finalRotation = new Quaternionf(spin).mul(rotation);
			flameBlock.setTransformation(new Transformation(
					new Vector3f(0,0.75f, -1.1f),
					new AxisAngle4f(finalRotation),
					new Vector3f(2, 2, 2),
					new AxisAngle4f()
			));

			List<Arrow> arrows = targetPlayer.getLocation().getNearbyEntitiesByType(Arrow.class, 2).stream().toList();
			if(arrows.isEmpty()) {
				return;
			}
			arrows.getFirst().remove();
			if(health.addAndGet(-1) <= 0) {
				fixedPlatform.remove();
				flameBlock.remove();
				targetPlayer.getLocation().getWorld().playSound(
						targetPlayer.getLocation(),
						Sound.BLOCK_GLASS_BREAK,
						SoundCategory.MASTER,
						1f, 1f
				);
				targetPlayer.getLocation().getWorld().spawnParticle(
						Particle.BLOCK, targetPlayer.getLocation(), 100, 1, 1, 1, Material.GLASS.createBlockData()
				);
				playerEquipment.setHelmet(savedEquipments[0]);
				playerEquipment.setChestplate(savedEquipments[1]);
				playerEquipment.setLeggings(savedEquipments[2]);
				playerEquipment.setBoots(savedEquipments[3]);
				player.removePotionEffect(PotionEffectType.RESISTANCE);
				task.cancel();
			}
			targetPlayer.getLocation().getWorld().playSound(
					targetPlayer.getLocation(),
					Sound.BLOCK_GLASS_BREAK,
					SoundCategory.MASTER,
					0.5f, 1f
			);
			flameBlock.setBlock(flameMaterials[health.get()].createBlockData());
		},0,1);
	}
}
