package ramune314159265.realoni.skills;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.AtomicDouble;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;
import ramune314159265.realoni.Realoni;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class Tongue extends Skill {
	static final float maxLength = 15;
	static final float speed = 40;
	static final float catchableDistance = 0.8f;

	@Override
	public String getName() {
		return "舌";
	}

	@Override
	public void use(Player player) {
		AtomicDouble tongueLength = new AtomicDouble(0);
		AtomicDouble tongueLengthVector = new AtomicDouble(speed);
		AtomicReference<LivingEntity> caughtEntity = new AtomicReference<>(null);
		BlockDisplay tongueDisplay = (BlockDisplay) player.getWorld().spawnEntity(player.getEyeLocation(), EntityType.BLOCK_DISPLAY);
		tongueDisplay.setBlock(Material.RED_CONCRETE.createBlockData());
		tongueDisplay.setInterpolationDuration(3);

		BukkitScheduler openExec = Bukkit.getScheduler();
		openExec.runTaskTimer(Realoni.getInstance(), (BukkitTask task) -> {
			if (maxLength <= tongueLength.addAndGet(tongueLengthVector.get() / 20)) {
				tongueLengthVector.set(-speed);
			}
			if (tongueLength.get() < 0) {
				if (!Objects.isNull(caughtEntity.get())) {
					caughtEntity.get().damage(4, player);
					caughtEntity.get().setVelocity(new Vector(0, 1.1, 0));
				}
				tongueDisplay.remove();
				task.cancel();
				return;
			}
			Location tongueTipLocation = offsetWithLocalVector(player.getEyeLocation(), 0, -0.2, tongueLength.get());
			if (0 < tongueLengthVector.get() && (!tongueTipLocation.getBlock().isPassable() && !Objects.isNull(player.getWorld().rayTraceBlocks(tongueTipLocation, player.getLocation().getDirection().normalize(), (tongueLengthVector.get() / 20) / 2)))) {
				tongueLengthVector.set(-speed);
			}
			if (Objects.isNull(caughtEntity.get()) && !tongueTipLocation.getNearbyLivingEntities(catchableDistance).isEmpty()) {
				try {
					LivingEntity nearestEntity = tongueTipLocation.getNearbyLivingEntities(catchableDistance)
							.stream()
							.filter(e -> {
								if(e.equals(player)){
									return false;
								}
								if (!(e instanceof Player p)) {
									return true;
								}
								return Realoni.processingGame.getPlayerRole(p).isSurvivor();
							})
							.sorted(Comparator.comparing(e -> e.getLocation().distance(tongueTipLocation), Comparator.naturalOrder()))
							.toList().getFirst();
					caughtEntity.set(nearestEntity);
				} catch (Exception ignored) {
				}
			}
			if (!Objects.isNull(caughtEntity.get())) {
				caughtEntity.get().teleport(tongueTipLocation);
			}
			tongueDisplay.setTransformation(new Transformation(
					new Vector3f(),
					new AxisAngle4f(),
					new Vector3f(0.15f, 0.04f, (float) tongueLength.get()),
					new AxisAngle4f()
			));
			tongueDisplay.teleport(offsetWithLocalVector(player.getEyeLocation(), 0.075, -0.2, 0));
			tongueDisplay.setRotation(player.getYaw(), player.getPitch());
		}, 0, 1);
	}

	public Location offsetWithLocalVector(Location origin, double right, double up, double forward) {
		Vector direction = origin.getDirection().normalize();

		Vector rightVec = direction.clone().crossProduct(new Vector(0, 1, 0)).normalize();

		Vector upVec = new Vector(0, 1, 0);

		Vector calculatedOffset = new Vector(0, 0, 0)
				.add(rightVec.multiply(right))
				.add(upVec.multiply(up))
				.add(direction.multiply(forward));

		return origin.clone().add(calculatedOffset);
	}
}
