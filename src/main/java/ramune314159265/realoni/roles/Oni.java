package ramune314159265.realoni.roles;

import org.bukkit.entity.Player;

public class Oni extends RoleAbstract {
	public Oni(Player player) {
		this.player = player;
	}

	@Override
	public String getDeathMessage() {
		return "殺人鬼を撃破した！";
	}

	@Override
	public String getNameTag() {
		return "鬼";
	}

	@Override
	public boolean isSurvivor() {
		return false;
	}

	@Override
	public void initialize() {

	}
}
