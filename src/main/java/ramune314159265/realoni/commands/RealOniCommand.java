package ramune314159265.realoni.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import ramune314159265.realoni.commands.subcommands.SelectSubCommand;
import ramune314159265.realoni.commands.subcommands.StartSubCommand;
import ramune314159265.realoni.commands.subcommands.SubCommand;
import ramune314159265.realoni.commands.subcommands.SupplyCountSubCommand;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RealOniCommand implements CommandExecutor, TabCompleter {
	static SubCommand[] commands = {
			new StartSubCommand(),
			new SupplyCountSubCommand(),
			new SelectSubCommand()
	};

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		List<String> argsArray = Arrays.asList(args);
		if (argsArray.isEmpty()) {
			return false;
		}

		String subCommandName = argsArray.getFirst();

		Optional<SubCommand> subCommandOptional = Arrays.stream(RealOniCommand.commands)
				.filter(c -> c.getName().equals(subCommandName))
				.findFirst();
		if (subCommandOptional.isEmpty()) {
			sender.sendMessage(Component.text()
					.content("サブコマンド: " + String.join(", ", Arrays.stream(RealOniCommand.commands).map(SubCommand::getName).toList()))
					.color(NamedTextColor.GREEN).build()
			);
			return true;
		}

		subCommandOptional.get().onCommand(sender, argsArray);
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (!command.getName().equalsIgnoreCase("realoni")) {
			return null;
		}
		if (args.length == 1) {
			return Arrays.stream(RealOniCommand.commands)
					.filter(SubCommand::isAvailable)
					.map(SubCommand::getName).toList();
		}
		if (2 <= args.length) {
			return Arrays.stream(RealOniCommand.commands)
					.filter(c -> c.getName().equals(args[0]))
					.findFirst()
					.map(subCommand -> subCommand.onTabComplete(sender, Arrays.asList(args)))
					.orElse(List.of());
		}
		return null;
	}
}
