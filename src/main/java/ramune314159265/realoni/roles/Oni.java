package ramune314159265.realoni.roles;

import dev.iiahmed.disguise.Disguise;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import ramune314159265.realoni.Realoni;
import ramune314159265.realoni.skills.Skill;
import ramune314159265.realoni.skills.SkillUsable;

import java.util.List;
import java.util.Optional;

public class Oni extends Role {
	public static Team oniTeam;
	static {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getMainScoreboard();
		oniTeam = Optional.ofNullable(board.getTeam("oni")).orElseGet(() -> board.registerNewTeam("oni"));
		oniTeam.displayName(Component.text("鬼"));
		oniTeam.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.FOR_OWN_TEAM);
		oniTeam.setAllowFriendlyFire(false);
		oniTeam.setCanSeeFriendlyInvisibles(true);
	}

	public Oni(Player player) {
		this.player = player;
	}

	@Override
	public String getDeathMessage() {
		return "殺人鬼を撃破した！";
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
		getSkills();
		Realoni.disguiseProvider.disguise(player, getDisguise());
		player.setGameMode(GameMode.SURVIVAL);
		oniTeam.addPlayer(player);
	}

	@Override
	public void exit() {
		oniTeam.removeEntity(player);
	}

	@Override
	public void tick() {

	}
}
