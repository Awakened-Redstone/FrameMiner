package world.bentobox.frameminer.commands;

import world.bentobox.bentobox.api.commands.CompositeCommand;
import world.bentobox.bentobox.api.commands.admin.AdminReloadCommand;
import world.bentobox.bentobox.api.localization.TextVariables;
import world.bentobox.bentobox.api.user.User;
import world.bentobox.frameminer.FrameMiner;

import java.util.List;

public class AdminCommand extends CompositeCommand {

    public AdminCommand(FrameMiner addon) {
        super(addon,
                addon.getSettings().getAdminCommand().split(" ")[0],
                addon.getSettings().getAdminCommand().split(" "));
    }

    @Override
    public void setup() {
        setPermission("admin.frameminer.*");
        new AdminReloadCommand(this);
    }

    @Override
    public boolean execute(User user, String label, List<String> args) {

        if (!args.isEmpty()) {
            user.sendMessage("general.errors.unknown-command", TextVariables.LABEL, getTopLabel());
            return false;
        }
        return false;
    }
}
