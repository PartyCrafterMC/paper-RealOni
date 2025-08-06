package ramune314159265.realoni.roles;

import dev.iiahmed.disguise.Disguise;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import ramune314159265.realoni.Realoni;

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
	public String getNameTag() {
		return "11の鬼";
	}
}
