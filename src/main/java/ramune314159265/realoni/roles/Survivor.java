package ramune314159265.realoni.roles;

import dev.iiahmed.disguise.Disguise;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import ramune314159265.realoni.kits.Kits;
import ramune314159265.realoni.skills.Skill;
import ramune314159265.realoni.skills.SkillUsable;

import java.util.List;

public class Survivor extends Role {
	public Survivor(Player player) {
		this.player = player;
	}

	@Override
	public String getDeathMessage() {
		return player.getName() + " は死亡しゲームから脱落した";
	}

	@Override
	public Disguise getDisguise() {
		return Disguise.builder().build();
	}

	@Override
	public boolean isSurvivor() {
		return true;
	}

	@Override
	public List<Skill> getSkills() {
		return List.of();
	}

	@Override
	public void initialize() {
		player.setGameMode(GameMode.SURVIVAL);

		Inventory inv = player.getInventory();
		inv.clear();
		Kits.getPlayerKit(player).setItems(player);
	}

	@Override
	public void exit() {

	}

	@Override
	public void tick() {

	}
}
