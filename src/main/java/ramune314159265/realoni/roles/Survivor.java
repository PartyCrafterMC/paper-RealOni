package ramune314159265.realoni.roles;

import org.bukkit.entity.Player;

public class Survivor extends RoleAbstract {
	public Survivor(Player player){
		this.player = player;
	}

	@Override
	public String getDeathMessage() {
		return this.player.getName() + " は死亡しゲームから脱落した";
	}

	@Override
	public String getNameTag() {
		return null;
	}
}
