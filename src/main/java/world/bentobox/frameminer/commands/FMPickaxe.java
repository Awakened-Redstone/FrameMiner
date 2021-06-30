package world.bentobox.frameminer.commands;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import world.bentobox.bentobox.api.commands.CompositeCommand;
import world.bentobox.bentobox.api.user.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FMPickaxe extends CompositeCommand {
    public FMPickaxe(CompositeCommand adminCommand) {
        super(adminCommand, "pickaxe");
    }

    @Override
    public void setup() {
        setPermission("admin.frameminer.getpickaxe");
        setDescription("frameminer.admin.getpickaxe.description");
    }

    @Override
    public boolean execute(User user, String label, List<String> args) {
        ItemStack item = new ItemStack(Material.NETHERITE_PICKAXE);
        if (user.getInventory().firstEmpty() != -1) {
            ItemMeta meta = item.getItemMeta();
            meta.setLore(Collections.singletonList("§7Brutal I"));
            item.setItemMeta(meta);
            user.getInventory().addItem(item);
        } else {
            user.sendMessage("§cInventory full. Can't give the pickaxe.");
        }
        return true;
    }

    @Override
    public Optional<List<String>> tabComplete(User user, String alias, List<String> args) {
        return Optional.empty();
    }
}
