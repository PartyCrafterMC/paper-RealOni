package ramune314159265.realoni.roles;

import dev.iiahmed.disguise.Disguise;
import org.bukkit.entity.Player;
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
	}

	public void setInventory() {}

	@Override
	public List<Skill> getSkills() {
		return List.of(
				new HealthCheck()
		);
	}

	@Override
	public Disguise getDisguise() {
		return Disguise.builder()
				.setName("拾漆の鬼")
				.build();
	}
}
