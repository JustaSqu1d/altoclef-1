package adris.altoclef.tasks.movement;

import adris.altoclef.tasksystem.Task;
import gay.solonovamax.altoclef.AltoClef;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Optional;

public class FollowPlayerTask extends Task {

    private final String _playerName;

    public FollowPlayerTask(String playerName) {
        _playerName = playerName;
    }

    @Override
    protected void onStart() {

    }

    @Override
    protected Task onTick() {
        Optional<Vec3d> lastPos = AltoClef.INSTANCE.getEntityTracker().getPlayerMostRecentPosition(_playerName);

        if (lastPos.isEmpty()) {
            setDebugState("No player found/detected. Doing nothing until player loads into render distance.");
            return null;
        }
        Vec3d target = lastPos.get();

        if (target.isInRange(AltoClef.INSTANCE.getPlayer().getPos(), 1) && !AltoClef.INSTANCE.getEntityTracker().isPlayerLoaded(_playerName)) {
            AltoClef.INSTANCE.logWarning("Failed to get to player \"" + _playerName + "\". We moved to where we last saw them but now have no idea where they are.");
            stop();
            return null;
        }

        Optional<PlayerEntity> player = AltoClef.INSTANCE.getEntityTracker().getPlayerEntity(_playerName);
        if (player.isEmpty()) {
            // Go to last location
            return new GetToBlockTask(new BlockPos((int) target.x, (int) target.y, (int) target.z), false);
        }
        return new GetToEntityTask(player.get(), 2);
    }

    @Override
    protected void onStop(Task interruptTask) {

    }

    @Override
    protected boolean isEqual(Task other) {
        if (other instanceof FollowPlayerTask task) {
            return task._playerName.equals(_playerName);
        }
        return false;
    }

    @Override
    protected String toDebugString() {
        return "Going to player " + _playerName;
    }
}
