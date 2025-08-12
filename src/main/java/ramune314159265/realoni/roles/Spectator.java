package ramune314159265.realoni.roles;

import dev.iiahmed.disguise.Disguise;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import ramune314159265.realoni.skills.Skill;

import java.util.List;

public class Spectator extends Role {
	public Spectator(Player player) {
		this.player = player;
	}

	@Override
	public String getDeathMessage() {
		return null;
	}

	@Override
	public Disguise getDisguise() {
		return Disguise.builder().build();
	}

	@Override
	public boolean isSurvivor() {
		return false;
	}

	@Override
	public List<Skill> getSkills() {
		return List.of();
	}

	@Override
	public void initialize() {
		player.setGameMode(GameMode.SPECTATOR);
	}

	@Override
	public void exit() {

	}

	@Override
	public void tick() {

	}
}
