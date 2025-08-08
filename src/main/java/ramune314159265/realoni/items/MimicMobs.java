package ramune314159265.realoni.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import ramune314159265.realoni.Realoni;

import java.util.Optional;

public class MimicMobs extends CustomItem implements Listener {
	@Override
	public String getName() {
		return "擬態モブ召喚";
	}

	@Override
	public ItemStack getItemStack() {
		ItemStack item = new ItemStack(Material.EGG);
		ItemMeta meta = item.getItemMeta();
		meta.itemName(Component.text(getName()));
		meta.setRarity(ItemRarity.EPIC);
		meta.setEnchantmentGlintOverride(true);
		item.setItemMeta(meta);
		return item;
	}

	@Override
	public NamespacedKey getKey() {
		return null;
	}

	@Override
	public void initialize() {
		Realoni.getInstance().getServer().getPluginManager().registerEvents(this, Realoni.getInstance());
	}

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent e) {
		if (e.getEntity().getType() != EntityType.EGG) {
			return;
		}
		ThrowableProjectile entity = (ThrowableProjectile) e.getEntity();

		String name = ((TextComponent) entity.getItem().getItemMeta().itemName()).content();
		if (!name.equals(getName())) {
			return;
		}
		entity.getLocation().getWorld().createExplosion(entity.getLocation(), 0f, false, true);

		for (Player p : Realoni.getInstance().getServer().getOnlinePlayers()) {
			if(!Realoni.processingGame.getPlayerRole(p).isSurvivor()) {
				continue;
			}
			ItemStack head = new ItemStack(Material.PLAYER_HEAD);
			SkullMeta meta = (SkullMeta) head.getItemMeta();
			meta.setOwningPlayer(p);
			meta.addAttributeModifier(
					Attribute.MOVEMENT_SPEED,
					new AttributeModifier(NamespacedKey.minecraft(""), 0.35d, AttributeModifier.Operation.ADD_SCALAR)
			);
			head.setItemMeta(meta);
			ItemStack chestPlate = Optional.ofNullable(p.getInventory().getChestplate()).orElse(new ItemStack(Material.AIR));
			ItemStack leggings = Optional.ofNullable(p.getInventory().getLeggings()).orElse(new ItemStack(Material.AIR));
			ItemStack boots = Optional.ofNullable(p.getInventory().getBoots()).orElse(new ItemStack(Material.AIR));

			Zombie zombie = (Zombie) entity.getWorld().spawnEntity(entity.getLocation(), EntityType.ZOMBIE);
			zombie.setShouldBurnInDay(false);
			zombie.getEquipment().setHelmet(head, true);
			zombie.getEquipment().setHelmetDropChance(0);
			zombie.getEquipment().setChestplate(chestPlate,true);
			zombie.getEquipment().setLeggings(leggings,true);
			zombie.getEquipment().setBoots(boots,true);
			zombie.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD), true);
			zombie.setCustomNameVisible(true);
			zombie.customName(Component.text(p.getName()));
			zombie.setAdult();
			zombie.setTarget(p);

			Skeleton skeleton = (Skeleton) entity.getWorld().spawnEntity(entity.getLocation(), EntityType.SKELETON);
			skeleton.setShouldBurnInDay(false);
			skeleton.getEquipment().setHelmet(head, true);
			skeleton.getEquipment().setHelmetDropChance(0);
			skeleton.getEquipment().setChestplate(chestPlate,true);
			skeleton.getEquipment().setLeggings(leggings,true);
			skeleton.getEquipment().setBoots(boots,true);
			skeleton.setCustomNameVisible(true);
			skeleton.customName(Component.text(p.getName()));
			skeleton.setTarget(p);
		}
	}
}
