package ramune314159265.realoni.skills;

import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import ramune314159265.realoni.Realoni;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FakeAnimal extends Skill implements Listener {
	static final List<EntityType> targetEntities = List.of(
			EntityType.COW,
			EntityType.PIG,
			EntityType.SHEEP,
			EntityType.CHICKEN,
			EntityType.SALMON,
			EntityType.COD
	);
	static final NamespacedKey storageKey = NamespacedKey.fromString("chameleon_fake_animal", Realoni.getInstance());
	static final double spawnChance = 0.3d;
	static {
		Realoni.getInstance().getServer().getPluginManager().registerEvents(new FakeAnimal(), Realoni.getInstance());
		final long startAt = Instant.now().toEpochMilli();
		BukkitScheduler openExec = Bukkit.getScheduler();
		openExec.runTaskTimer(Realoni.getInstance(), (BukkitTask task) -> {
			double offsetX = Math.cos((Instant.now().toEpochMilli() - startAt) / 300d) * 0.5;
			double offsetY = Math.sin((Instant.now().toEpochMilli() - startAt) / 300d) * 0.5;
			Realoni.defaultWorld.getLivingEntities().stream()
					.filter(e -> targetEntities.contains(e.getType()))
					.forEach(e -> {
						if(!e.getPersistentDataContainer().getOrDefault(storageKey, PersistentDataType.BOOLEAN, false)){
							return;
						}
						e.getWorld().spawnParticle(
								Particle.DUST,
								e.getEyeLocation().add(offsetX, 0.5, offsetY),
								5, 0, 0, 0, 0 ,new Particle.DustOptions(Color.RED, 1f),true
						);
					});
		}, 1, 3);
	}

	@Override
	public String getName() {
		return "偽動物";
	}

	@Override
	public void use(Player player) {
		Realoni.defaultWorld.getLivingEntities().stream()
				.filter(e -> targetEntities.contains(e.getType()))
				.forEach(e -> {
					if(spawnChance < Math.random()) {
						return;
					}
					Entity spawnedEntity = e.getWorld().spawnEntity(e.getLocation(), e.getType());
					spawnedEntity.getPersistentDataContainer().set(storageKey, PersistentDataType.BOOLEAN, true);
				});
	}

	@EventHandler
	public void onEntityDied(EntityDeathEvent e) {
		if(!targetEntities.contains(e.getEntity().getType())){
			return;
		}
		if(Objects.isNull(e.getDamageSource().getCausingEntity())){
			return;
		}
		if(e.getDamageSource().getCausingEntity().getType() != EntityType.PLAYER) {
			return;
		}
		if(!e.getEntity().getPersistentDataContainer().getOrDefault(storageKey, PersistentDataType.BOOLEAN, false)){
			return;
		}
		e.getEntity().getWorld().createExplosion(e.getEntity().getLocation(), 0.5f, false, true);

		Player player = (Player) e.getDamageSource().getCausingEntity();

		ItemStack head = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta) head.getItemMeta();
		meta.setOwningPlayer(player);
		meta.addAttributeModifier(
				Attribute.MOVEMENT_SPEED,
				new AttributeModifier(NamespacedKey.minecraft(""), 0.25d, AttributeModifier.Operation.ADD_SCALAR)
		);
		head.setItemMeta(meta);
		ItemStack chestPlate = Optional.ofNullable(player.getInventory().getChestplate()).orElse(new ItemStack(Material.AIR));
		ItemStack leggings = Optional.ofNullable(player.getInventory().getLeggings()).orElse(new ItemStack(Material.AIR));
		ItemStack boots = Optional.ofNullable(player.getInventory().getBoots()).orElse(new ItemStack(Material.AIR));
		ItemStack supply = new ItemStack(Material.BEEHIVE);
		ItemMeta supplyMeta = supply.getItemMeta();
		supplyMeta.itemName(Component.text("物資"));
		supplyMeta.setRarity(ItemRarity.RARE);
		supply.setItemMeta(supplyMeta);

		Zombie zombie = (Zombie) e.getEntity().getLocation().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.ZOMBIE);
		zombie.setShouldBurnInDay(false);
		zombie.getEquipment().setHelmet(head, true);
		zombie.getEquipment().setHelmetDropChance(0);
		zombie.getEquipment().setChestplate(chestPlate,true);
		zombie.getEquipment().setLeggings(leggings,true);
		zombie.getEquipment().setBoots(boots,true);
		zombie.getEquipment().setItemInMainHand(new ItemStack(Material.WOODEN_SWORD), true);
		zombie.getEquipment().setItemInOffHand(supply);
		zombie.getEquipment().setItemInOffHandDropChance(0.3f);
		zombie.setCustomNameVisible(true);
		zombie.customName(Component.text(player.getName()));
		zombie.setAdult();
		zombie.setTarget(player);
	}
}
