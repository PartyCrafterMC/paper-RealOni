package ramune314159265.realoni.roles;

import org.bukkit.entity.Player;

abstract public class RoleAbstract {
	public Player player;

	public abstract String getDeathMessage();

	public abstract String getNameTag();

	public abstract boolean isSurvivor();
}
