package world.bentobox.frameminer;

import org.bukkit.Material;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.addons.Addon;
import world.bentobox.bentobox.api.configuration.Config;
import world.bentobox.bentobox.api.flags.Flag;
import world.bentobox.frameminer.commands.AdminCommand;
import world.bentobox.frameminer.event.BreakerEventListener;

public final class FrameMiner extends Addon {

    private static FrameMiner instance;

    public static Flag MINE_FRAME =
            new Flag.Builder("MINE_FRAME", Material.END_PORTAL_FRAME).listener(new BreakerEventListener(getInstance())).build();

    private Settings settings;

    @Override
    public void onEnable() {

        logError("Fail. Error located on frame mining permissions");
        setState(State.DISABLED);

        new AdminCommand(this);

        getPlugin().getFlagsManager().registerFlag(MINE_FRAME);
        getLogger().info("FrameMiner started");
        getLogger().info("\n\n\n\n\n\n\nMineDamage: " + getSettings().getDamage() + "\n\n\n\n\n\n\nResistance: " + getSettings().getResistance());
        getServer().getPluginManager().registerEvents(new BreakerEventListener(this), getPlugin());

    }

    @Override
    public void onLoad() {

        // Save the default config from config.yml
        saveDefaultConfig();
        // Load settings from config.yml. This will check if there are any issues with it too.
        loadSettings();
    }

    @Override
    public void onDisable() {
        getLogger().info(getPlugin().getName() + "Stopped");
    }


    private void loadSettings() {
        settings = new Config<>(this, Settings.class).loadConfigObject();

        if (settings == null) {
            // Disable
            logError("FrameMiner settings could not load! Addon disabled.");
            setState(State.DISABLED);
        }

    }

    @Override
    public void onReload() {
        loadSettings();
        getLogger().info("FrameMiner has been reloaded.");
    }

    /**
     * @return the settings
     */
    public Settings getSettings() {
        return settings;
    }

    public static FrameMiner getInstance() {
       return instance;
    }
}
