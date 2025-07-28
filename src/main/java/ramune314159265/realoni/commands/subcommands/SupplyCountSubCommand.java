package ramune314159265.realoni.commands.subcommands;

import org.bukkit.command.CommandSender;
import ramune314159265.realoni.Realoni;
import ramune314159265.realoni.Supply;

import java.util.List;
import java.util.Objects;

public class SupplyCountSubCommand extends SubCommand{
	@Override
	public String getName() {
		return "supplycount";
	}

	@Override
	public void onCommand(CommandSender sender, List<String> args) {
		sender.sendMessage("物資の位置 (" + Supply.blocks.size() + ")の位置:");
		Supply.blocks.forEach(block -> sender.sendMessage(block.getX() + ", " + block.getY() + ", " + block.getZ()));
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, List<String> args) {
		return List.of();
	}

	@Override
	public boolean isAvailable() {
		return !Objects.isNull(Realoni.processingGame);
	}
}
