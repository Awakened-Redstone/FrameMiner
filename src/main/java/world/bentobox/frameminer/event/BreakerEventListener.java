package world.bentobox.frameminer.event;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import world.bentobox.bentobox.api.user.User;
import world.bentobox.frameminer.FrameMiner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.bukkit.Bukkit.createBossBar;
import static org.bukkit.Bukkit.getServer;
import static org.bukkit.Sound.ENTITY_ITEM_BREAK;

public class BreakerEventListener implements Listener {

    FrameMiner addon;

    public BreakerEventListener(FrameMiner addon) {
        super();
        this.addon = addon;
    }

    @EventHandler
    public void onBlockDamage(BlockDamageEvent event) {
        try {
            Player player = event.getPlayer();
            User user = User.getInstance(player);
            Location loc = event.getBlock().getLocation();
            ItemStack air = new ItemStack(Material.AIR);
            int slot = player.getInventory().getHeldItemSlot();
            getServer().getScheduler().scheduleSyncDelayedTask(addon.getPlugin(), () -> {
                if (event.getBlock().getType().equals(Material.END_PORTAL_FRAME) &&
                        event.getItemInHand().getType().equals(Material.NETHERITE_PICKAXE) ) {
                    if (FrameMiner.getInstance().getSettings().getNormalPickaxe() || (
                            event.getItemInHand().hasItemMeta() &&
                                    event.getItemInHand().getItemMeta().hasLore() &&
                                    event.getItemInHand().getItemMeta().getLore().contains("§7Brutal I"))) {
                        if (addon.getIslands().getIslandAt(loc).map(i -> i.isAllowed(user, FrameMiner.MINE_FRAME)).orElse(false)) {
                            if (!event.getBlock().isEmpty() && player.getInventory().getHeldItemSlot() == slot) {
                                ItemStack item = player.getInventory().getItemInMainHand();
                                ItemMeta meta = item.getItemMeta();
                                Damageable dmg = (Damageable) meta;
                                if (dmg != null) {
                                    if (!event.getItemInHand().getItemMeta().isUnbreakable()) {
                                        if (event.getItemInHand().getEnchantments().containsKey(Enchantment.DURABILITY)) {
                                            //Reduce the damage depending on the Unbreaking level
                                            int durability = event.getItemInHand().getEnchantmentLevel(Enchantment.DURABILITY);
                                            int reduce = getResistance() * durability;
                                            if (reduce < getMineDamage() && durability > 0) { //Resistant Pickaxe
                                                //Damage the pickaxe
                                                int damage = dmg.getDamage() + getMineDamage() - reduce;
                                                dmg.setDamage(damage);
                                                item.setItemMeta(meta);
                                                if (damage >= 1560) {
                                                    item = air;
                                                    player.playSound(player.getLocation(), ENTITY_ITEM_BREAK, 100, 1);
                                                    player.getInventory().setItemInMainHand(item);
                                                }
                                                //Mine the frame
                                                if (event.getBlock().getBlockData().getAsString().contains("eye=true")) {
                                                    addItem(new ItemStack(Material.ENDER_EYE), player);
                                                    X = event.getBlock().getLocation().getBlockX();
                                                    Y = event.getBlock().getLocation().getBlockY();
                                                    Z = event.getBlock().getLocation().getBlockZ();
                                                    removePortal(event);
                                                }
                                                event.getBlock().breakNaturally();
                                                player.playSound(player.getLocation(), ENTITY_ITEM_BREAK, 5, 0);
                                                addItem(new ItemStack(Material.END_PORTAL_FRAME), player);
                                                /*event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), new ItemStack(Material.END_PORTAL_FRAME));*/
                                            } else { //Totally resistant Pickaxe
                                                //Mine the frame
                                                //Mine the frame
                                                if (event.getBlock().getBlockData().getAsString().contains("eye=true")) {
                                                    addItem(new ItemStack(Material.ENDER_EYE), player);
                                                    X = event.getBlock().getLocation().getBlockX();
                                                    Y = event.getBlock().getLocation().getBlockY();
                                                    Z = event.getBlock().getLocation().getBlockZ();
                                                    removePortal(event);
                                                }
                                                event.getBlock().breakNaturally();
                                                player.playSound(player.getLocation(), ENTITY_ITEM_BREAK, 5, 0);
                                                addItem(new ItemStack(Material.END_PORTAL_FRAME), player);
                                                /*event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), new ItemStack(Material.END_PORTAL_FRAME));*/
                                            }
                                        } else { //Normal Pickaxe
                                            //Apply the damage on the tool
                                            int damage = dmg.getDamage() + getMineDamage();
                                            dmg.setDamage(damage);
                                            item.setItemMeta(meta);
                                            if (damage >= 1560) {
                                                item = air;
                                                player.playSound(player.getLocation(), ENTITY_ITEM_BREAK, 100, 1);
                                                player.getInventory().setItemInMainHand(item);
                                            }
                                            //Mine the frame
                                            if (event.getBlock().getBlockData().getAsString().contains("eye=true")) {
                                                addItem(new ItemStack(Material.ENDER_EYE), player);
                                                X = event.getBlock().getLocation().getBlockX();
                                                Y = event.getBlock().getLocation().getBlockY();
                                                Z = event.getBlock().getLocation().getBlockZ();
                                                removePortal(event);
                                            }
                                            event.getBlock().breakNaturally();
                                            player.playSound(player.getLocation(), ENTITY_ITEM_BREAK, 5, 0);
                                            addItem(new ItemStack(Material.END_PORTAL_FRAME), player);
                                            /*event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), new ItemStack(Material.END_PORTAL_FRAME));*/
                                        }
                                    } else if (event.getItemInHand().getItemMeta().isUnbreakable()) { //Unbreakable Pickaxe
                                        //Mine the frame
                                        if (event.getBlock().getBlockData().getAsString().contains("eye=true")) {
                                            addItem(new ItemStack(Material.ENDER_EYE), player);
                                            X = event.getBlock().getLocation().getBlockX();
                                            Y = event.getBlock().getLocation().getBlockY();
                                            Z = event.getBlock().getLocation().getBlockZ();
                                            removePortal(event);
                                        }
                                        event.getBlock().breakNaturally();
                                        player.playSound(player.getLocation(), ENTITY_ITEM_BREAK, 5, 0);
                                        addItem(new ItemStack(Material.END_PORTAL_FRAME), player);
                                        /*event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), new ItemStack(Material.END_PORTAL_FRAME));*/
                                    } else {
                                        event.getPlayer().sendMessage("§c§lError §8§l: §7Something is wrong with your pickaxe");
                                    }
                                } else {
                                    event.getPlayer().sendMessage("§c§lError §8§l: §7Something is wrong with your pickaxe");
                                }
                            } else if (player.getInventory().getHeldItemSlot() != slot) {
                                player.sendMessage("§b§lFrameMiner§8: §cNice try.");
                                player.setLevel(player.getLevel() - 5);
                                player.sendTitle("§b§lFrameMiner", "§cDon't do that again", 15, 500, 0);
                                BossBar bossBar = createBossBar("Cooldown", BarColor.RED, BarStyle.SEGMENTED_12, BarFlag.CREATE_FOG, BarFlag.DARKEN_SKY);
                                bossBar.addPlayer(player);
                                bossBar.setVisible(true);
                                bossBar.setProgress(0);
                                for (double i = 0.00; bossBar.getProgress() < 1; i = i + 0.01) {
                                    bossBar.setProgress(i);
                                    bossBar.setTitle("§cCooldown [§a" + i * 100 + "§a%§c]");
                                    getServer().getScheduler().scheduleSyncDelayedTask(addon.getPlugin(), () -> {
                                        player.sendTitle("§b§lFrameMiner", "§cDon't do that again", 0, 500, 0);
                                    }, 3L);
                                    if (bossBar.getProgress() == 1) {
                                        player.resetTitle();
                                        bossBar.removeAll();
                                        bossBar.removePlayer(player);
                                        bossBar.setVisible(false);
                                    }
                                }
                            }
                        } else {
                            player.sendMessage("§cIsland protected: Frame breaking disabled.");
                        }
                    }
                }
            }, 3L);
        } catch (Exception e) {
            //Error message cancelled.
        }
    }

    private int X;
    private int Y;
    private int Z;

    private int getMineDamage() {
        return addon.getSettings().getDamage();
    }

    private int getResistance() {
        return addon.getSettings().getResistance();
    }

    private String getInventoryFull() {
        return addon.getSettings().getFullInventory();
    }

    private void removePortal(BlockDamageEvent event) {
        //Make sure you put your world name
        Player player = event.getPlayer();
        World world = player.getWorld();
        //List of materials that you want removed, just copy the structure and add all block materials(types) you wish
        List<Material> removedMaterials = new ArrayList<>();
        removedMaterials.add(Material.END_PORTAL);
        //This runs through each block in the specified region
        for (int x = X - 3; x < X + 4; x++) {
            for (int y = Y - 3; y < Y + 4; y++) {
                for (int z = Z - 3; z < Z + 4; z++) {
                    Block b = world.getBlockAt(x, y, z);
                    //If the types of blocks to be removed contain the type of the block being inspected, set it to air(remove it).
                    if (removedMaterials.contains(b.getType())) {
                        b.setType(Material.AIR);
                    }
                }
            }
        }
    }

    private void addItem(ItemStack item, Player player) {
        Location loc = player.getLocation();
        World world = player.getWorld();
        if (player.getInventory().firstEmpty() != -1) {
            player.getInventory().addItem(item);
        } else {
            world.dropItem(loc, item);
            player.sendMessage(getInventoryFull());
        }
    }
}
