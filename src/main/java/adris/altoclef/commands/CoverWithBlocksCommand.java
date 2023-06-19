package adris.altoclef.commands;

import adris.altoclef.commandsystem.ArgParser;
import adris.altoclef.commandsystem.Command;
import adris.altoclef.commandsystem.CommandException;
import adris.altoclef.tasks.construction.CoverWithBlocksTask;
import gay.solonovamax.altoclef.AltoClef;

public class CoverWithBlocksCommand extends Command {
    public CoverWithBlocksCommand() {
        super("coverwithblocks", "Cover nether lava with blocks.");
    }

    @Override
    protected void call(ArgParser parser) throws CommandException {
        AltoClef.INSTANCE.runUserTask(new CoverWithBlocksTask(), this::finish);
    }
}
