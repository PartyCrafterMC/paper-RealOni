package ramune314159265.realoni.roles;

import dev.iiahmed.disguise.Disguise;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ramune314159265.realoni.Realoni;
import ramune314159265.realoni.skills.*;

import java.util.List;

public class Fox extends Oni{
	public Fox(Player player) {
		super(player);
	}

	@Override
	public void initialize() {
		super.initialize();

		setInventory();
		player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, -1, 0, false, false, false));
		player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, -1, 1, false, false, false));
		player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, -1, 2, false, false, false));
		AttributeInstance healthAttribute = player.getAttribute(Attribute.MAX_HEALTH);
		healthAttribute.addModifier(new AttributeModifier(NamespacedKey.fromString("fox_health", Realoni.getInstance()), 40d, AttributeModifier.Operation.ADD_NUMBER));
		player.setHealth(60);
	}

	@Override
	public void exit() {
		try {
			super.exit();
			player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
			player.removePotionEffect(PotionEffectType.JUMP_BOOST);
			player.removePotionEffect(PotionEffectType.RESISTANCE);
			player.getAttribute(Attribute.MAX_HEALTH).removeModifier(NamespacedKey.fromString("fox_health", Realoni.getInstance()));
			player.setHealth(20);
		} catch (Exception ignored) {
		}
	}

	public void setInventory() {
		ItemStack sword = new ItemStack(Material.NETHERITE_SWORD);
		ItemMeta swordMeta = sword.getItemMeta();
		swordMeta.addEnchant(Enchantment.FIRE_ASPECT, 2,true);
		swordMeta.addEnchant(Enchantment.KNOCKBACK, 1,true);
		swordMeta.addEnchant(Enchantment.SMITE, 10,true);
		swordMeta.setUnbreakable(true);
		sword.setItemMeta(swordMeta);

		ItemStack axe = new ItemStack(Material.NETHERITE_AXE);
		ItemMeta axeMeta = axe.getItemMeta();
		axeMeta.addEnchant(Enchantment.EFFICIENCY, 3,true);
		axeMeta.setUnbreakable(true);
		axe.setItemMeta(axeMeta);

		ItemStack pickaxe = new ItemStack(Material.NETHERITE_PICKAXE);
		ItemMeta pickaxeMeta = axe.getItemMeta();
		pickaxeMeta.addEnchant(Enchantment.EFFICIENCY, 5,true);
		pickaxeMeta.setUnbreakable(true);
		pickaxe.setItemMeta(pickaxeMeta);

		ItemStack shovel = new ItemStack(Material.NETHERITE_SHOVEL);
		ItemMeta shovelMeta = shovel.getItemMeta();
		shovelMeta.addEnchant(Enchantment.EFFICIENCY, 5,true);
		shovelMeta.setUnbreakable(true);
		shovel.setItemMeta(shovelMeta);

		ItemStack bow = new ItemStack(Material.BOW);
		ItemMeta bowMeta = bow.getItemMeta();
		bowMeta.addEnchant(Enchantment.POWER, 1,true);
		bowMeta.addEnchant(Enchantment.FLAME, 1,true);
		bowMeta.addEnchant(Enchantment.INFINITY, 1,true);
		bowMeta.setUnbreakable(true);
		bow.setItemMeta(bowMeta);

		Inventory inv = player.getInventory();
		inv.setItem(0, sword);
		inv.setItem(1, axe);
		inv.setItem(2, pickaxe);
		inv.setItem(3, shovel);
		inv.setItem(4, bow);
		inv.addItem(new ItemStack(Material.BREAD, 64));
		inv.addItem(new ItemStack(Material.GOLDEN_APPLE, 3));
		inv.addItem(new ItemStack(Material.FLINT_AND_STEEL));
		inv.addItem(new ItemStack(Material.BEDROCK, 64));
		inv.addItem(new ItemStack(Material.WATER_BUCKET));
		inv.addItem(new ItemStack(Material.LAVA_BUCKET));
		inv.addItem(new ItemStack(Material.ARROW));
		inv.addItem(new ItemStack(Material.SHEARS));
	}

	@Override
	public List<Skill> getSkills() {
		return List.of(
				new HealthCheck(),
				new SweetBerryRestrict(),
				new InvisibleFox(),
				new CursedFox(),
				new FloorLava()
		);
	}

	@Override
	public Disguise getDisguise() {
		return Disguise.builder()
				.setName("拾漆の鬼")
				.build();
	}
}
