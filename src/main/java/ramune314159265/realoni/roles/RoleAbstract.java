package ramune314159265.realoni.roles;

import org.bukkit.entity.Player;
import ramune314159265.realoni.skills.Skill;

import java.util.List;

abstract public class RoleAbstract {
	public Player player;

	public abstract String getDeathMessage();

	public abstract String getNameTag();

	public abstract boolean isSurvivor();

	public abstract List<Skill> getSkills();

	public abstract void initialize();

	public abstract void exit();
}
