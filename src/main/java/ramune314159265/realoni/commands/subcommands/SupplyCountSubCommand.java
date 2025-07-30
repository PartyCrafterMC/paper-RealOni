package ramune314159265.realoni.commands.subcommands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.command.CommandSender;
import ramune314159265.realoni.Realoni;
import ramune314159265.realoni.Supply;

import java.util.List;
import java.util.Objects;

public class SupplyCountSubCommand extends SubCommand {
	@Override
	public String getName() {
		return "supplycount";
	}

	@Override
	public void onCommand(CommandSender sender, List<String> args) {
		sender.sendMessage("物資 (" + Supply.blocks.size() + "個)の位置:");
		Supply.blocks.forEach(block -> sender.sendMessage(
				Component.text(block.getX() + ", " + block.getY() + ", " + block.getZ())
						.clickEvent(ClickEvent.runCommand("/tp " + block.getX() + " " + block.getY() + " " + block.getZ()))
						.hoverEvent(HoverEvent.showText(Component.text("クリックしてTP")))
		));
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
