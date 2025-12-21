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
		AttributeInstance jumpAttribute = player.getAttribute(Attribute.JUMP_STRENGTH);
		jumpAttribute.addModifier(new AttributeModifier(NamespacedKey.fromString("fox_jump", Realoni.getInstance()), 0.3d, AttributeModifier.Operation.ADD_SCALAR));
	}

	@Override
	public void exit() {
		try {
			super.exit();
			player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
			player.getAttribute(Attribute.JUMP_STRENGTH).removeModifier(NamespacedKey.fromString("fox_jump", Realoni.getInstance()));
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
