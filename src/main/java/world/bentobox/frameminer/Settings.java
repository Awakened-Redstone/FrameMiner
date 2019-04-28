package world.bentobox.frameminer;

import world.bentobox.bentobox.api.configuration.ConfigComment;
import world.bentobox.bentobox.api.configuration.ConfigEntry;
import world.bentobox.bentobox.api.configuration.StoreAt;
import world.bentobox.bentobox.database.objects.DataObject;

@StoreAt(filename="config.yml", path="addons/FrameMiner") // Explicitly call out what name this should have.
@ConfigComment("FrameMiner Configuration [version]")
public class Settings implements DataObject {

    /* Commands */
    @ConfigComment("")
    @ConfigComment("Main command to FrameMiner [Admins]")
    @ConfigEntry(path = "frameminer.commands.admin", needsReset = true)
    private String adminCommand = "framemineradmin fm fmadmin frameminer";

    /* Values */
    @ConfigComment("")
    @ConfigComment("Base damage that will be applied on the pickaxes")
    @ConfigEntry(path = "frameminer.values.damage")
    private int damage = 5;

    @ConfigComment("")
    @ConfigComment("Resistance of each unbreaking level.")
    @ConfigComment("The value set is multiplied by the unbreaking level.")
    @ConfigComment("The resistance works on this way: \"damage - (resistance * \"unbreaking level\")\"")
    @ConfigComment("If the value get to 0 or lower the pickaxe do not get damage.")
    @ConfigEntry(path = "frameminer.values.resistance")
    private int resistance = 1;

    /* Messages */
    @ConfigComment("")
    @ConfigComment("Warning to full Inventory")
    @ConfigEntry(path = "frameminer.messages.warnings.fullinv")
    private String fullInventory = "§cInventory full. Dropping Item.";



    private String uniqueId = "config";

    @Override
    public String getUniqueId() {
        return uniqueId;
    }

    @Override
    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public void setAdminCommand(String adminCommand) {
        this.adminCommand = adminCommand;
    }

    public String getAdminCommand() {
        return adminCommand;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    public int getResistance() {
        return resistance;
    }

    public void setFullInventory(String fullInventory) {
        this.fullInventory = fullInventory;
    }

    public String getFullInventory() {
        return fullInventory;
    }
}