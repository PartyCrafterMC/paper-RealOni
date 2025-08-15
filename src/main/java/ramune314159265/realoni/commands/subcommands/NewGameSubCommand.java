package ramune314159265.realoni.commands.subcommands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import ramune314159265.realoni.Realoni;

import java.util.List;
import java.util.Objects;

public class NewGameSubCommand extends SubCommand {
	@Override
	public String getName() {
		return "newgame";
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

		sender.sendMessage(
				Component.text()
						.append(Component.text("/realoni start").clickEvent(ClickEvent.suggestCommand("/realoni start")))
						.append(Component.text(" で5秒後にゲームスタート"))
		);
		Realoni.newGame();
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
