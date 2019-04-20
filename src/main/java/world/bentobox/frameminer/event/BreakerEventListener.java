package world.bentobox.frameminer.event;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.EndPortalFrame;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import world.bentobox.frameminer.FrameMiner;

import java.util.Objects;

public class BreakerEventListener implements Listener {

    public FrameMiner addon;
    public EndPortalFrame frame;

    @EventHandler
    public void onBlockDamage (BlockDamageEvent event) {
        if (event.getBlock().getType().equals(Material.END_PORTAL_FRAME) && event.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE) && Objects.requireNonNull(Objects.requireNonNull(event.getItemInHand().getItemMeta()).getLore()).contains("§7Brutal I")) {
            try {
                Thread.sleep (300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!event.getBlock().isEmpty()) {

                ItemStack item = new ItemStack(event.getItemInHand());
                ItemMeta meta = item.getItemMeta();
                Damageable dmg = (Damageable) meta;
                if (dmg != null) {
                    if (!event.getItemInHand().getItemMeta().isUnbreakable()) {
                        if (event.getItemInHand().getEnchantments().containsKey(Enchantment.DURABILITY)) {
                            //Reduce the damage depending on the Unbreaking level
                            int durability = event.getItemInHand().getEnchantmentLevel(Enchantment.DURABILITY);
                            if ((getResistance(event) * durability) < getMineDamage(event) && event.getItemInHand().getEnchantmentLevel(Enchantment.DURABILITY) > 0) { //Resistant Pickaxe
                                //Damage the pickaxe
                                int damage = dmg.getDamage() + getMineDamage(event) - (getResistance(event) * durability);
                                dmg.setDamage(damage);
                                item.setItemMeta(meta);
                                event.getPlayer().setItemInHand(item);
                                //Mine the frame
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
                            event.getBlock().breakNaturally();
                            event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), new ItemStack(Material.END_PORTAL_FRAME));
                        if (event.getBlock().getType().equals(frame.getMaterial()) && frame.hasEye()) {
                            event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), new ItemStack(Material.ENDER_EYE));
                        }
                        }
                    } else if (event.getItemInHand().getItemMeta().isUnbreakable()) { //Unbreakable Pickaxe
                        //Mine the frame
                        event.getBlock().breakNaturally();
                        event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), new ItemStack(Material.END_PORTAL_FRAME));
                    } else {
                        event.getPlayer().sendMessage("§c§lError §8§l: §7Something is wrong with your pickaxe");
                    }
                } else {
                    event.getPlayer().sendMessage("§c§lError §8§l: §7Something is wrong with your pickaxe");
                }
            }
        }
    }

    private short getMineDamage(BlockDamageEvent event) {
        return addon.getSettings().getDamage();
    }

    private short getResistance(BlockDamageEvent event) {
        return addon.getSettings().getResistance();
    }
}
