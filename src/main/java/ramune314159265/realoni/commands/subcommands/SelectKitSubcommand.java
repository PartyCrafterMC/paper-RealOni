package ramune314159265.realoni.commands.subcommands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ramune314159265.realoni.kits.Kit;
import ramune314159265.realoni.kits.Kits;

import java.util.Arrays;
import java.util.List;

public class SelectKitSubcommand extends SubCommand {
	@Override
	public String getName() {
		return "selectkit";
	}

	@Override
	public void onCommand(CommandSender sender, List<String> args) {
		if (!(sender instanceof Player player)) {
			return;
		}
		if (args.size() < 2) {
			Kits.removePlayer(player);
			sender.sendMessage(Component.text("キットの選択を解除しました"));
			return;
		}

		try {
			Kit kit = Arrays.stream(Kits.kitList)
					.filter(i -> i.getName().equals(args.get(1)))
					.toList().getFirst();

			Kits.setPlayerKit(player, kit);
			sender.sendMessage(Component.text(kit.getName() + " を選択しました"));
		} catch (Exception ignored) {
			sender.sendMessage(Component.text("キットが存在しません").color(NamedTextColor.RED));
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, List<String> args) {
		return Arrays.stream(Kits.kitList).map(Kit::getName).toList();
	}

	@Override
	public boolean isAvailable() {
		return true;
	}
}
