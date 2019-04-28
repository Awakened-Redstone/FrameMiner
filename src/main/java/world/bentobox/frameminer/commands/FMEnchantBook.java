package world.bentobox.frameminer.commands;

import world.bentobox.bentobox.api.addons.Addon;
import world.bentobox.bentobox.api.commands.CompositeCommand;
import world.bentobox.bentobox.api.user.User;

import java.util.List;
import java.util.Optional;

public class FMEnchantBook extends CompositeCommand {
    public FMEnchantBook(CompositeCommand adminCommand) {
        super(adminCommand, "book");
    }

    @Override
    public void setup() {
        setPermission("admin.frameminer.book");
        setDescription("frameminer.admin.book.description");
    }

    @Override
    public boolean canExecute(User user, String label, List<String> args) {
        return false;
    }

    @Override
    public boolean execute(User user, String label, List<String> args) {

        return false;
    }

    @Override
    public Optional<List<String>> tabComplete(User user, String alias, List<String> args) {
        return Optional.empty();
    }
}
