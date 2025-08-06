package ramune314159265.realoni.roles;

import dev.iiahmed.disguise.Disguise;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import ramune314159265.realoni.Realoni;
import ramune314159265.realoni.skills.Skill;
import ramune314159265.realoni.skills.Tongue;

import java.util.List;

public class Cameleon extends Oni {
	public Cameleon(Player player) {
		super(player);
	}

	@Override
	public void initialize() {
		player.setGameMode(GameMode.SURVIVAL);

		Disguise disguise = Disguise.builder()
				.setName(getNameTag())
				.build();
		Realoni.disguiseProvider.disguise(player, disguise);
	}

	@Override
	public List<Skill> getSkills() {
		return List.of(
				new Tongue()
		);
	}

	@Override
	public String getNameTag() {
		return "11の鬼";
	}
}
