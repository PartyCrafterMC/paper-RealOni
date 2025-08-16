package ramune314159265.realoni.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ThrowableProjectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import ramune314159265.realoni.Realoni;

public class Grenade extends CustomItem implements Listener {
	@Override
	public String getName() {
		return "グレネード";
	}

	@Override
	public ItemStack getItemStack() {
		ItemStack item = new ItemStack(Material.SNOWBALL, 2);
		ItemMeta meta = item.getItemMeta();
		meta.itemName(Component.text(getName()));
		meta.setRarity(ItemRarity.UNCOMMON);
		meta.setEnchantmentGlintOverride(true);
		item.setItemMeta(meta);
		return item;
	}

	@Override
	public NamespacedKey getKey() {
		return new NamespacedKey(Realoni.getInstance(), "grenade");
	}

	@Override
	public void initialize() {
		ShapedRecipe recipe = new ShapedRecipe(
				getKey(),
				this.getItemStack()
		);
		recipe.shape(
				"DID",
				"INI",
				"DID"
		);
		recipe.setIngredient('D', Material.DIRT);
		recipe.setIngredient('I', Material.IRON_INGOT);
		recipe.setIngredient('N', Material.NETHER_STAR);
		Bukkit.addRecipe(recipe);

		Realoni.getInstance().getServer().getPluginManager().registerEvents(this, Realoni.getInstance());
	}

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent e) {
		if (e.getEntity().getType() != EntityType.SNOWBALL) {
			return;
		}
		ThrowableProjectile entity = (ThrowableProjectile) e.getEntity();

		String name = ((TextComponent) entity.getItem().getItemMeta().itemName()).content();
		if (!name.equals(getName())) {
			return;
		}
		entity.getLocation().getWorld().createExplosion(entity.getLocation(), 5f, true);
	}
}
