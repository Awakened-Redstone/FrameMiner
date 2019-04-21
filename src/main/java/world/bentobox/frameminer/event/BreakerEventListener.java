package world.bentobox.frameminer.event;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.EndPortalFrame;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import world.bentobox.frameminer.FrameMiner;

import java.util.Objects;

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
        getServer().getScheduler().scheduleSyncDelayedTask(addon.getPlugin(), () -> {
            if (event.getBlock().getType().equals(Material.END_PORTAL_FRAME) && event.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE) && Objects.requireNonNull(Objects.requireNonNull(event.getItemInHand().getItemMeta()).getLore()).contains("§7Brutal I")) {
            /*try {
                Thread.sleep (300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
                if (!event.getBlock().isEmpty()) {
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
                                            event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), new ItemStack(Material.ENDER_EYE));
                                        }
                                        event.getBlock().breakNaturally();
                                        event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), new ItemStack(Material.END_PORTAL_FRAME));
                                    } else { //Totally resistant Pickaxe
                                        //Mine the frame
                                        event.getBlock().breakNaturally();
                                        event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), new ItemStack(Material.END_PORTAL_FRAME));
                                    }
                                } else { //Normal Pickaxe
                                    //Apply the damage on the tool
                                    int damage = dmg.getDamage() + getMineDamage(event);
                                    dmg.setDamage(damage);
                                    item.setItemMeta(meta);
                                    event.getPlayer().setItemInHand(item);
                                    //Mine the frame
                                    if (event.getBlock().getBlockData().getAsString().contains("eye=true")) {
                                        event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), new ItemStack(Material.ENDER_EYE));
                                    }
                                    event.getBlock().breakNaturally();
                                    event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), new ItemStack(Material.END_PORTAL_FRAME));
                                }
                            } else if (event.getItemInHand().getItemMeta().isUnbreakable()) { //Unbreakable Pickaxe
                                //Mine the frame
                                if (event.getBlock().getBlockData().getAsString().contains("eye=true")) {
                                    event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), new ItemStack(Material.ENDER_EYE));
                                }
                                event.getBlock().breakNaturally();
                                event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), new ItemStack(Material.END_PORTAL_FRAME));
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
    }

    private int getMineDamage(BlockDamageEvent event) {
        return addon.getSettings().getDamage();
    }

    private int getResistance(BlockDamageEvent event) {
        return addon.getSettings().getResistance();
    }

    private void removePortal(BlockDamageEvent event) {
        int x = event.getBlock().getLocation().getBlockX();
        int y = event.getBlock().getLocation().getBlockZ();
        event.getBlock().getLocation().setZ(event.getBlock().getLocation().getBlockX() - 3);
        event.getBlock().getLocation().setX(event.getBlock().getLocation().getBlockX() - 3);
        Location loc = event.getBlock().getLocation();
        loc.setX(loc.getBlockX() + 1);
        if (event.getBlock().getLocation().getBlockX() == x + 3) {
            event.getBlock().getLocation().setZ(event.getBlock().getLocation().getBlockY() + 1);
        }

    }
}
