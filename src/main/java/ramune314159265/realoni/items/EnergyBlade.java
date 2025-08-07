package ramune314159265.realoni.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import ramune314159265.realoni.Realoni;

import java.time.Instant;

public class EnergyBlade extends CustomItem implements Listener {
	@Override
	public String getName() {
		return "エナジーブレード";
	}

	@Override
	public ItemStack getItemStack() {
		ItemStack item = new ItemStack(Material.NETHERITE_SWORD);
		ItemMeta meta = item.getItemMeta();
		meta.itemName(
				Component.text(getName())
						.appendSpace()
						.append(Component.translatable("enchantment.level.1").color(NamedTextColor.BLUE))
		);
		meta.setRarity(ItemRarity.UNCOMMON);
		meta.setEnchantmentGlintOverride(true);
		meta.addEnchant(Enchantment.SHARPNESS, 1, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.addAttributeModifier(
				Attribute.MOVEMENT_SPEED,
				new AttributeModifier(NamespacedKey.minecraft(""), 0.2d, AttributeModifier.Operation.ADD_SCALAR)
		);
		item.setItemMeta(meta);
		return item;
	}

	@Override
	public NamespacedKey getKey() {
		return new NamespacedKey(Realoni.getInstance(), "energy_blade");
	}

	@Override
	public void initialize() {
		ShapedRecipe recipe = new ShapedRecipe(
				getKey(),
				this.getItemStack()
		);
		recipe.shape(
				"  C",
				"IC ",
				"NI "
		);
		recipe.setIngredient('C', Material.COAL_BLOCK);
		recipe.setIngredient('I', Material.IRON_BLOCK);
		recipe.setIngredient('N', Material.NETHER_STAR);
		Bukkit.addRecipe(recipe);

		Realoni.getInstance().getServer().getPluginManager().registerEvents(this, Realoni.getInstance());
	}

	@EventHandler
	public void onEntityDamaged(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player attacker)) return;

		ItemStack item = attacker.getInventory().getItemInMainHand();
		if (item.isEmpty()) {
			return;
		}
		ItemMeta meta = item.getItemMeta();

		String name = ((TextComponent) meta.itemName()).content();
		if (!name.equals(getName())) {
			return;
		}

		long lastUsed = meta.getPersistentDataContainer().getOrDefault(getKey(), PersistentDataType.LONG, Instant.now().toEpochMilli());

		if (5 * 1000 < Instant.now().toEpochMilli() - lastUsed) {
			meta.addEnchant(Enchantment.SHARPNESS, 1, true);
			meta.itemName(
					Component.text(getName())
							.appendSpace()
							.append(Component.translatable("enchantment.level.1").color(NamedTextColor.BLUE))
			);
		} else {
			int level = Math.min(meta.getEnchantLevel(Enchantment.SHARPNESS) + 1, 8);
			meta.addEnchant(Enchantment.SHARPNESS, level, true);
			meta.itemName(
					Component.text(getName())
							.appendSpace()
							.append(Component.translatable("enchantment.level." + level).color(NamedTextColor.RED))
			);
		}
		meta.getPersistentDataContainer().set(getKey(), PersistentDataType.LONG, Instant.now().toEpochMilli());
		item.setItemMeta(meta);
	}
}
