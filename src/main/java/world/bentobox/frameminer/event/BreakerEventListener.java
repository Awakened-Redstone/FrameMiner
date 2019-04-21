package world.bentobox.frameminer.event;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.EndPortalFrame;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import world.bentobox.frameminer.FrameMiner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class BreakerEventListener implements Listener {

    FrameMiner addon;
    EndPortalFrame frame;

    public BreakerEventListener(FrameMiner addon) {
        super();
        this.addon = addon;
    }

    @EventHandler
    public void onBlockDamage (BlockDamageEvent event) {
        if (event.getPlayer().getInventory().firstEmpty() != -1) {
            getServer().getScheduler().scheduleSyncDelayedTask(addon.getPlugin(), () -> {
                if (event.getBlock().getType().equals(Material.END_PORTAL_FRAME) && event.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE) && Objects.requireNonNull(Objects.requireNonNull(event.getItemInHand().getItemMeta()).getLore()).contains("§7Brutal I")) {
            /*try {
                Thread.sleep (300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
                    if (!event.getBlock().isEmpty()) {
                        Player player = event.getPlayer();
                        ItemStack item = new ItemStack(event.getItemInHand());
                        ItemMeta meta = item.getItemMeta();
                        Damageable dmg = (Damageable) meta;
                        try {
                            if (dmg != null) {
                                if (!event.getItemInHand().getItemMeta().isUnbreakable()) {
                                    if (event.getItemInHand().getEnchantments().containsKey(Enchantment.DURABILITY)) {
                                        //Reduce the damage depending on the Unbreaking level
                                        int durability = event.getItemInHand().getEnchantmentLevel(Enchantment.DURABILITY);
                                        int reduce = getResistance(event) * durability;
                                        if (reduce < getMineDamage(event) && durability > 0) { //Resistant Pickaxe
                                            //Damage the pickaxe
                                            int damage = dmg.getDamage() + getMineDamage(event) - reduce;
                                            dmg.setDamage(damage);
                                            item.setItemMeta(meta);
                                            event.getPlayer().setItemInHand(item);
                                            //Mine the frame
                                            if (event.getBlock().getBlockData().getAsString().contains("eye=true")) {
                                                player.getInventory().addItem(new ItemStack(Material.ENDER_EYE));
                                                X = event.getBlock().getLocation().getBlockX();
                                                Y = event.getBlock().getLocation().getBlockY();
                                                Z = event.getBlock().getLocation().getBlockZ();
                                                removePortal(event);
                                            }
                                            event.getBlock().breakNaturally();
                                            player.getInventory().addItem(new ItemStack(Material.END_PORTAL_FRAME));
                                            /*event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), new ItemStack(Material.END_PORTAL_FRAME));*/
                                        } else { //Totally resistant Pickaxe
                                            //Mine the frame
                                            //Mine the frame
                                            if (event.getBlock().getBlockData().getAsString().contains("eye=true")) {
                                                player.getInventory().addItem(new ItemStack(Material.ENDER_EYE));
                                                X = event.getBlock().getLocation().getBlockX();
                                                Y = event.getBlock().getLocation().getBlockY();
                                                Z = event.getBlock().getLocation().getBlockZ();
                                                removePortal(event);
                                            }
                                            event.getBlock().breakNaturally();
                                            player.getInventory().addItem(new ItemStack(Material.END_PORTAL_FRAME));
                                            /*event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), new ItemStack(Material.END_PORTAL_FRAME));*/
                                        }
                                    } else { //Normal Pickaxe
                                        //Apply the damage on the tool
                                        int damage = dmg.getDamage() + getMineDamage(event);
                                        dmg.setDamage(damage);
                                        item.setItemMeta(meta);
                                        event.getPlayer().setItemInHand(item);
                                        //Mine the frame
                                        if (event.getBlock().getBlockData().getAsString().contains("eye=true")) {
                                            player.getInventory().addItem(new ItemStack(Material.ENDER_EYE));
                                            X = event.getBlock().getLocation().getBlockX();
                                            Y = event.getBlock().getLocation().getBlockY();
                                            Z = event.getBlock().getLocation().getBlockZ();
                                            removePortal(event);
                                        }
                                        event.getBlock().breakNaturally();
                                        player.getInventory().addItem(new ItemStack(Material.END_PORTAL_FRAME));
                                        /*event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), new ItemStack(Material.END_PORTAL_FRAME));*/
                                    }
                                } else if (event.getItemInHand().getItemMeta().isUnbreakable()) { //Unbreakable Pickaxe
                                    //Mine the frame
                                    if (event.getBlock().getBlockData().getAsString().contains("eye=true")) {
                                        player.getInventory().addItem(new ItemStack(Material.ENDER_EYE));
                                        X = event.getBlock().getLocation().getBlockX();
                                        Y = event.getBlock().getLocation().getBlockY();
                                        Z = event.getBlock().getLocation().getBlockZ();
                                        removePortal(event);
                                    }
                                    event.getBlock().breakNaturally();
                                    player.getInventory().addItem(new ItemStack(Material.END_PORTAL_FRAME));
                                    /*event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), new ItemStack(Material.END_PORTAL_FRAME));*/
                                } else {
                                    event.getPlayer().sendMessage("§c§lError §8§l: §7Something is wrong with your pickaxe");
                                }
                            } else {
                                event.getPlayer().sendMessage("§c§lError §8§l: §7Something is wrong with your pickaxe");
                            }
                        } catch (Exception e) {
                            //Error message cancelled.
                        }
                    }
                }
            }, 3L);
        } else {event.getPlayer().sendMessage("§c§lError §8§l: §7Can't mine the frame. For security you need at least §a§l1 §b§lempty §7slot. ");}
    }

    private int X;
    private int Y;
    private int Z;


    /*private int z;
    private int x;

    private int A;*/

    private int getMineDamage(BlockDamageEvent event) {
        return addon.getSettings().getDamage();
    }

    private int getResistance(BlockDamageEvent event) {
        return addon.getSettings().getResistance();
    }

    /*private void removePortal(BlockDamageEvent event) {
        Location loc = event.getBlock().getLocation();
        A = 7;
        z = loc.getBlockZ() - 3;
        x = loc.getBlockX() + 4;
        getLogger().info("\n\n\n\n\nX: " + X + "\nx: " + x + "\nZ: " + Z + "\nz: " + z + "\nB: " + (x - 7) + "\nA: " + A);
        removePortalLoop(event);
    }*/

    private void removePortal(BlockDamageEvent event) {
        //Make sure you put your world name
        Player player = event.getPlayer();
        World world = player.getWorld();
        //List of materials that you want removed, just copy the structure and add all block materials(types) you wish
        List<Material> removedMaterials = new ArrayList<Material>();
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



    /*private void removePortalLoop(BlockDamageEvent event) {
        while (A > 0) {
            Block block = event.getBlock();
            Material air = Material.AIR;
            Material portal = Material.END_PORTAL;
            getLogger().info("\n\n\n" + portal +", " + block + ", " + block.getType());
            if (A > 0) {

                x = x - 7;
                block.getLocation().setX(x);
                block.getLocation().setZ(z);
                getLogger().info("\n\n\n" + portal +", " + block + ", " + block.getType());
                if (block.getType() == portal)
                    block.setType(air); // x = -3

                x = x + 1;
                block.getLocation().setX(x);
                block.getLocation().setZ(z);
                if (block.getType() == portal)
                    block.setType(air); // x = -2

                x = x + 1;
                block.getLocation().setX(x);
                block.getLocation().setZ(z);
                if (block.getType() == portal)
                    block.setType(air); // x = -1

                x = x + 1;
                block.getLocation().setX(x);
                block.getLocation().setZ(z);
                if (block.getType() == portal)
                    block.setType(air); // x = 0

                x = x + 1;
                block.getLocation().setX(x);
                block.getLocation().setZ(z);
                if (block.getType() == portal)
                    block.setType(air); // x = 1

                x = x + 1;
                block.getLocation().setX(x);
                block.getLocation().setZ(z);
                if (block.getType() == portal)
                    block.setType(air); // x = 2

                x = x + 1;
                block.getLocation().setX(x);
                block.getLocation().setZ(z);
                if (block.getType() == portal)
                    block.setType(air); // x = 3

                z = z + 1;
                block.getLocation().setX(x);
                block.getLocation().setZ(z);
                getLogger().info("\n\n\n" + portal +", " + block + ", " + block.getType());

                A = A - 1;
                block.getLocation().setX(x);
                block.getLocation().setZ(z);
                getLogger().info("\n\n\n" + portal +", " + block + ", " + block.getType());

            }
        }
    }*/
}
