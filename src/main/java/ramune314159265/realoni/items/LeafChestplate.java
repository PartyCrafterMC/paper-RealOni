package ramune314159265.realoni.items;

import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import ramune314159265.realoni.Realoni;

import java.util.UUID;

public class LeafChestplate extends CustomItem{
	@Override
	public String getName() {
		return "リーフチェストプレート";
	}

	@Override
	public ItemStack getItemStack() {
		ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE);
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		meta.itemName(Component.text(getName()));
		meta.setRarity(ItemRarity.UNCOMMON);
		meta.setColor(Color.fromRGB(5819984));
		meta.addItemFlags(ItemFlag.HIDE_DYE);
		meta.addAttributeModifier(
				Attribute.MAX_HEALTH,
				new AttributeModifier(
						NamespacedKey.minecraft("generic.max_health"),
						12d,
						AttributeModifier.Operation.ADD_NUMBER,
						EquipmentSlotGroup.CHEST
				)
		);
		item.setItemMeta(meta);
		return item;
	}

	@Override
	public NamespacedKey getKey() {
		return new NamespacedKey(Realoni.getInstance(), "leaf_chestplate");
	}

	@Override
	public void initialize() {
		ShapedRecipe recipe = new ShapedRecipe(
				getKey(),
				this.getItemStack()
		);
		recipe.shape(
				"L L",
				"LLL",
				"LLL"
		);
		recipe.setIngredient('L', new RecipeChoice.MaterialChoice(Tag.LEAVES));
		Bukkit.addRecipe(recipe);
	}
}
