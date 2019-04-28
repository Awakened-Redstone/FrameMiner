package world.bentobox.frameminer.commands;

import org.bukkit.inventory.ItemStack;
import world.bentobox.bentobox.api.commands.CompositeCommand;
import world.bentobox.bentobox.api.user.User;

import java.util.*;

public class FMEnchant extends CompositeCommand {
    public FMEnchant(CompositeCommand adminCommand) {
        super(adminCommand, "enchant");
    }

    @Override
    public void setup() {
        setPermission("admin.frameminer.enchant");
        setDescription("frameminer.admin.enchant.description");
    }

    @Override
    public boolean canExecute(User user, String label, List<String> args) {
        return false;
    }

    @Override
    public boolean execute(User user, String label, List<String> args) {
        ItemStack item = user.getInventory().getItemInHand();
        List<String> lore = new ArrayList<String>(Collections.singletonList("Brutal I"));
        lore.addAll(Objects.requireNonNull(Objects.requireNonNull(item.getItemMeta()).getLore()));
        item.getItemMeta().setLore(lore);
        user.getInventory().setItemInHand(item);
        return true;
    }

    @Override
    public Optional<List<String>> tabComplete(User user, String alias, List<String> args) {
        return Optional.empty();
    }
}
