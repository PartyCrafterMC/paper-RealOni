package ramune314159265.realoni.skills;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import ramune314159265.realoni.Realoni;

import java.util.concurrent.atomic.AtomicInteger;

public class InvisibleFox extends SkillUsable {
	@Override
	public String getName() {
		return "透明狐";
	}

	@Override
	public void use(Player player) {
		player.setInvisible(true);
		BukkitScheduler scheduler = Bukkit.getScheduler();
		AtomicInteger tick = new AtomicInteger(-1);
		scheduler.runTaskTimer(Realoni.getInstance(), (BukkitTask task) -> {
			player.getWorld().setType(player.getLocation(), Material.FIRE);
			player.setFireTicks(-1);
			if (200 <= tick.addAndGet(1)) {
				player.setInvisible(false);
				task.cancel();
			}
		}, 0, 1);
	}
}

