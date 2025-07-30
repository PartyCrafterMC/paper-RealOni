package ramune314159265.realoni.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import ramune314159265.realoni.Realoni;

public class GlassSword extends CustomItem{
	@Override
	public String getName() {
		return "ガラスソード";
	}

	@Override
	public ItemStack getItemStack() {
		ItemStack item = new ItemStack(Material.IRON_SWORD);
		Damageable meta = (Damageable) item.getItemMeta();
		meta.itemName(Component.text(getName()));
		meta.setRarity(ItemRarity.UNCOMMON);
		meta.setMaxDamage(1);
		meta.addEnchant(Enchantment.SHARPNESS, 10, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		return item;
	}

	@Override
	public NamespacedKey getKey() {
		return new NamespacedKey(Realoni.getInstance(), "glass_sword");
	}

	@Override
	public void initialize() {
		ShapedRecipe recipe = new ShapedRecipe(
				getKey(),
				this.getItemStack()
		);
		recipe.shape(
				" G ",
				" G ",
				"INI"
		);
		recipe.setIngredient('G', Material.GLASS);
		recipe.setIngredient('I', Material.IRON_INGOT);
		recipe.setIngredient('N', Material.NETHER_STAR);
		Bukkit.addRecipe(recipe);
	}
}
