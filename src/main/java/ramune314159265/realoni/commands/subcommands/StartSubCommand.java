package ramune314159265.realoni.commands.subcommands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import ramune314159265.realoni.Realoni;

import java.util.List;
import java.util.Objects;

public class StartSubCommand extends SubCommand {
	@Override
	public String getName() {
		return "start";
	}

	@Override
	public void onCommand(CommandSender sender, List<String> args) {
		if (!Objects.isNull(Realoni.processingGame)) {
			sender.sendMessage(Component.text().content("既に開始されています").color(NamedTextColor.RED).build());
			return;
		}
		if (Realoni.chunky.isRunning(Realoni.defaultWorld.getName())) {
			sender.sendMessage(Component.text().content("チャンクの生成中です").color(NamedTextColor.RED).build());
			return;
		}

		Realoni.broadcast(Component.text().content("10秒後に開始します").color(NamedTextColor.GREEN).build());

		BukkitScheduler openExec = Bukkit.getScheduler();
		openExec.runTaskLater(Realoni.getInstance(), (BukkitTask task) -> {
			Realoni.broadcast(Component.text().content("ゲームスタート！").color(NamedTextColor.LIGHT_PURPLE).build());
			Realoni.startGame();
		}, 200);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, List<String> args) {
		return List.of();
	}

	@Override
	public boolean isAvailable() {
		return !Realoni.chunky.isRunning(Realoni.defaultWorld.getName()) && Objects.isNull(Realoni.processingGame);
	}
}
