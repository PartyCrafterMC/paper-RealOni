package ramune314159265.realoni.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import ramune314159265.realoni.Realoni;

public class TeleportBall extends CustomItem{
	@Override
	public String getName() {
		return "テレポート玉";
	}

	@Override
	public ItemStack getItemStack() {
		ItemStack item = new ItemStack(Material.ENDER_EYE);
		ItemMeta meta = item.getItemMeta();
		meta.itemName(Component.text(getName()));
		meta.setRarity(ItemRarity.UNCOMMON);
		meta.setEnchantmentGlintOverride(true);
		item.setItemMeta(meta);
		return item;
	}

	@Override
	public NamespacedKey getKey() {
		return new NamespacedKey(Realoni.getInstance(), "teleport_ball");
	}

	@Override
	public void initialize() {
		ShapelessRecipe recipe = new ShapelessRecipe(
				getKey(),
				this.getItemStack()
		);
		recipe.addIngredient(Material.ENDER_PEARL);
		recipe.addIngredient(Material.NETHER_STAR);
		Bukkit.addRecipe(recipe);
	}
}
