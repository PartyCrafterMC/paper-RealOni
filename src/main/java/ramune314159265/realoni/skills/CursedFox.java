package ramune314159265.realoni.skills;

import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Biome;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import ramune314159265.realoni.Ground;
import ramune314159265.realoni.Realoni;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class CursedFox extends Skill implements Listener {
	@Override
	public String getName() {
		return "呪われた狐";
	}

	static final List<Biome> targetBiomes = List.of(
			Biome.GROVE,
			Biome.SNOWY_TAIGA,
			Biome.OLD_GROWTH_PINE_TAIGA,
			Biome.OLD_GROWTH_SPRUCE_TAIGA,
			Biome.TAIGA
	);
	static final NamespacedKey storageKey = NamespacedKey.fromString("fox_cursed_fox", Realoni.getInstance());
	static int foxDeathCount = 0;
	static Color getParticleColor() {
		return switch (foxDeathCount) {
			case 0, 1 -> Color.YELLOW;
			case 2, 3, 4 -> Color.ORANGE;
			case 5, 6 -> Color.RED;
			case 7 -> Color.fromRGB(152, 0, 0);
			case 8 -> Color.fromRGB(52, 0, 0);
			case 9 -> Color.fromRGB(37, 0, 0);
			default -> Color.BLACK;
		};
	}

	static {
		Realoni.getInstance().getServer().getPluginManager().registerEvents(new CursedFox(), Realoni.getInstance());

		ItemStack supply = new ItemStack(Material.BEEHIVE);
		ItemMeta supplyMeta = supply.getItemMeta();
		supplyMeta.itemName(Component.text("物資"));
		supplyMeta.setRarity(ItemRarity.RARE);
		supply.setItemMeta(supplyMeta);
		Random random = new Random();
		for (int i = 0; i < 100; i++) {
			int randomX = random.nextInt(Realoni.worldSize) - Realoni.worldSize / 2;
			int randomZ = random.nextInt(Realoni.worldSize) - Realoni.worldSize / 2;
			int y = Ground.getY(Realoni.defaultWorld, randomX, randomZ) + 2;
			Biome biome = Realoni.defaultWorld.getBiome(randomX, y, randomZ);
			if(!targetBiomes.contains(biome)){
				continue;
			}
			Fox spawnedEntity = (Fox) Realoni.defaultWorld.spawnEntity(new Location(Realoni.defaultWorld, randomX, y, randomZ), EntityType.FOX);
			spawnedEntity.getEquipment().setItemInMainHand(supply);
			spawnedEntity.getEquipment().setItemInMainHandDropChance(0.2f);
			spawnedEntity.getPersistentDataContainer().set(storageKey, PersistentDataType.BOOLEAN, true);
		}

		BukkitScheduler openExec = Bukkit.getScheduler();
		openExec.runTaskTimer(Realoni.getInstance(), (BukkitTask task) -> {
			Realoni.defaultWorld.getLivingEntities().stream()
					.filter(e -> e.getType() == EntityType.FOX)
					.forEach(e -> {
						if(!e.getPersistentDataContainer().getOrDefault(storageKey, PersistentDataType.BOOLEAN, false)){
							return;
						}
						Realoni.defaultWorld.spawnParticle(
								Particle.DUST, e.getLocation().add(0, 0.2, 0.5),
								5, 0.4, 0.2, 0.4, 0.05, new Particle.DustOptions(getParticleColor(), 1.5f), true
						);
					});
		}, 1, 5);
	}

	@EventHandler
	public void onEntityDied(EntityDeathEvent e) {
		if (e.getEntity().getType() != EntityType.FOX) {
			return;
		}
		if (Objects.isNull(e.getDamageSource().getCausingEntity())) {
			return;
		}
		if (!(e.getDamageSource().getCausingEntity() instanceof Player player)) {
			return;
		}
		if (!e.getEntity().getPersistentDataContainer().getOrDefault(storageKey, PersistentDataType.BOOLEAN, false)) {
			return;
		}

		player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 60, 1, false, false, false));
		foxDeathCount++;
		if(foxDeathCount == 10) {
			for (Player p : Realoni.getInstance().getServer().getOnlinePlayers()) {
				if(!Realoni.processingGame.getPlayerRole(p).isSurvivor()) {
					return;
				}
				AttributeInstance healthAttribute = p.getAttribute(Attribute.MAX_HEALTH);
				healthAttribute.addModifier(new AttributeModifier(NamespacedKey.fromString("fox_cursed_fox", Realoni.getInstance()), -4d, AttributeModifier.Operation.ADD_NUMBER));
			}
		}
	}
}
