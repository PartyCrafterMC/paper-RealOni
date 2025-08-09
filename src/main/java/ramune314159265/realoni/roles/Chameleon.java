package ramune314159265.realoni.roles;

import dev.iiahmed.disguise.Disguise;
import io.papermc.paper.datacomponent.item.Equippable;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ramune314159265.realoni.Realoni;
import ramune314159265.realoni.items.Items;
import ramune314159265.realoni.skills.Mimicry;
import ramune314159265.realoni.skills.Skill;
import ramune314159265.realoni.skills.Tongue;

import java.util.Arrays;
import java.util.List;

public class Chameleon extends Oni {
	public Chameleon(Player player) {
		super(player);
	}

	@Override
	public void initialize() {
		Disguise disguise = Disguise.builder()
				.setName("11の鬼")
				.build();
		Realoni.disguiseProvider.disguise(player, disguise);

		player.setGameMode(GameMode.SURVIVAL);
		setInventory();
		player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, -1, 0, false, false, true));
		AttributeInstance healthAttribute = player.getAttribute(Attribute.MAX_HEALTH);
		healthAttribute.addModifier(new AttributeModifier(NamespacedKey.fromString("chameleon_health", Realoni.getInstance()), 40d, AttributeModifier.Operation.ADD_NUMBER));
		player.setHealth(60);
	}

	@Override
	public void exit() {
		try {
			player.removePotionEffect(PotionEffectType.NIGHT_VISION);
			player.getAttribute(Attribute.MAX_HEALTH).removeModifier(NamespacedKey.fromString("chameleon_health", Realoni.getInstance()));
			player.setHealth(60);
		} catch (Exception ignored) {
		}
	}

	public void setInventory() {
		ItemStack chestplate = new ItemStack(Material.NETHERITE_CHESTPLATE);
		ItemMeta chestplateMeta = chestplate.getItemMeta();
		chestplateMeta.addEnchant(Enchantment.PROTECTION, 2, true);
		chestplateMeta.setUnbreakable(true);
		chestplate.setItemMeta(chestplateMeta);

		ItemStack leggings = new ItemStack(Material.NETHERITE_LEGGINGS);
		ItemMeta leggingsMeta = leggings.getItemMeta();
		leggingsMeta.addEnchant(Enchantment.PROTECTION, 3, true);
		leggingsMeta.setUnbreakable(true);
		leggings.setItemMeta(leggingsMeta);

		ItemStack boots = new ItemStack(Material.NETHERITE_BOOTS);
		ItemMeta bootsMeta = boots.getItemMeta();
		bootsMeta.addEnchant(Enchantment.PROTECTION, 3, true);
		bootsMeta.addEnchant(Enchantment.FEATHER_FALLING, 3, true);
		bootsMeta.addAttributeModifier(
				Attribute.MOVEMENT_SPEED,
				new AttributeModifier(NamespacedKey.minecraft(""), 1d, AttributeModifier.Operation.ADD_SCALAR)
		);
		bootsMeta.setUnbreakable(true);
		boots.setItemMeta(bootsMeta);

		ItemStack sword = new ItemStack(Material.NETHERITE_SWORD);
		ItemMeta swordMeta = sword.getItemMeta();
		swordMeta.addEnchant(Enchantment.SHARPNESS, 1,true);
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
		bowMeta.addEnchant(Enchantment.PUNCH, 2,true);
		bowMeta.addEnchant(Enchantment.POWER, 2,true);
		bowMeta.addEnchant(Enchantment.INFINITY, 1,true);
		bowMeta.setUnbreakable(true);
		bow.setItemMeta(bowMeta);

		Inventory inv = player.getInventory();
		inv.setItem(0, sword);
		inv.setItem(1, axe);
		inv.setItem(2, pickaxe);
		inv.setItem(3, shovel);
		inv.setItem(4, bow);
		EntityEquipment equipment = player.getEquipment();
		equipment.setChestplate(chestplate);
		equipment.setLeggings(leggings);
		equipment.setBoots(boots);
		inv.addItem(new ItemStack(Material.COOKIE, 64));
		inv.addItem(new ItemStack(Material.FLINT_AND_STEEL));
		inv.addItem(new ItemStack(Material.BEDROCK, 64));
		inv.addItem(new ItemStack(Material.WATER_BUCKET));
		inv.addItem(new ItemStack(Material.LAVA_BUCKET));
		inv.addItem(new ItemStack(Material.ARROW));
		inv.addItem(new ItemStack(Material.SHEARS));
		inv.addItem(Arrays.stream(Items.customItems)
				.filter(i -> i.getName().equals("擬態モブ召喚"))
				.toList().getFirst().getItemStack().asQuantity(5));
	}

	@Override
	public List<Skill> getSkills() {
		return List.of(
				new Tongue(),
				new Mimicry()
		);
	}

	@Override
	public Disguise getDisguise() {
		return Disguise.builder()
				.setName("11の鬼")
				.build();
	}
}
