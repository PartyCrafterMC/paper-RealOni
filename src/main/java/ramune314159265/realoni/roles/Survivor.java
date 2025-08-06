package ramune314159265.realoni.roles;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ramune314159265.realoni.skills.Skill;

import java.util.List;

public class Survivor extends RoleAbstract {
	public Survivor(Player player) {
		this.player = player;
	}

	@Override
	public String getDeathMessage() {
		return player.getName() + " は死亡しゲームから脱落した";
	}

	@Override
	public String getNameTag() {
		return null;
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
		inv.setItem(0, new ItemStack(Material.STONE_SWORD));
		inv.setItem(1, new ItemStack(Material.STONE_PICKAXE));
		inv.setItem(2, new ItemStack(Material.STONE_SHOVEL));
		inv.setItem(3, new ItemStack(Material.STONE_AXE));
		inv.setItem(4, new ItemStack(Material.COOKIE, 15));
		inv.setItem(5, new ItemStack(Material.TORCH, 64));
	}
}
