package world.bentobox.frameminer.commands;

import world.bentobox.bentobox.api.commands.CompositeCommand;
import world.bentobox.bentobox.api.commands.admin.AdminReloadCommand;
import world.bentobox.bentobox.api.user.User;

import java.util.List;
import java.util.Optional;

public class FMReloadCommand extends AdminReloadCommand {


    public FMReloadCommand(CompositeCommand adminCommand) {
        super(adminCommand);
    }

    @Override
    public boolean execute(User user, String label, List<String> args) {
        getAddon().onReload();
        getPlugin().reloadConfig();
        user.sendMessage("§b§lServer §8§l: §7FrameMiner has been reloaded.");
        return true;
    }

    public Optional<List<String>> tabComplete(User user, String alias, List<String> args) {
        return Optional.empty();
    }
}
