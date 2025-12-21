package ramune314159265.realoni.roles;

import dev.iiahmed.disguise.Disguise;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
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
		player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, -1, 0, false, false, false));
	}

	@Override
	public void exit() {
		try {
			super.exit();
			player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
			player.removePotionEffect(PotionEffectType.JUMP_BOOST);
		} catch (Exception ignored) {
		}
	}

	public void setInventory() {}

	@Override
	public List<Skill> getSkills() {
		return List.of(
				new HealthCheck(),
				new SweetBerryRestrict(),
				new InvisibleFox()
		);
	}

	@Override
	public Disguise getDisguise() {
		return Disguise.builder()
				.setName("拾漆の鬼")
				.build();
	}
}
