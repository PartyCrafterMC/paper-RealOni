package ramune314159265.realoni.skills;

import org.bukkit.entity.Player;

public class Tongue extends Skill {
	@Override
	public String getName() {
		return "舌";
	}

	@Override
	public void use(Player player) {
		player.sendMessage("used");
	}
}
