package world.bentobox.frameminer.event;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import world.bentobox.frameminer.FrameMiner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class AnvilEnchantListener implements Listener {

    FrameMiner addon;

    public AnvilEnchantListener(FrameMiner addon) {
        super();
        this.addon = addon;
    }

    @EventHandler
    public void openAnvil(PlayerInteractEvent event) {
        try {
            if (event.getClickedBlock() != null) {
                Material anvil = Material.ANVIL;
                Player player = event.getPlayer();
                if (event.getClickedBlock().getType() == anvil && player.isSneaking() && player.getInventory().getItemInMainHand().getType() == Material.DIAMOND_PICKAXE) {
                    ItemStack result = player.getInventory().getItemInMainHand().clone();
                    ItemMeta resultMeta = result.getItemMeta();

                    ItemStack pickaxe = player.getInventory().getItemInMainHand();

                    ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
                    ItemMeta bookMeta = book.getItemMeta();

                    List<String> bookLore = new ArrayList<>(Collections.singletonList("§7Brutal I"));
                    if(bookMeta == null)
                        return;
                    bookMeta.setLore(bookLore);
                    book.setItemMeta(bookMeta);

                    if (pickaxe.getItemMeta() != null && resultMeta.hasLore() && player.getInventory().getItemInMainHand().hasItemMeta() && player.getInventory().getItemInMainHand().getItemMeta().getLore().contains("§7Brutal I")) {
                        return;
                    }

                    if (resultMeta != null) {
                        if (resultMeta.hasLore()) {
                            List<String> oldLore = new ArrayList<>(resultMeta.getLore());
                            List<String> newLore = new ArrayList<>(Collections.singletonList("§7Brutal I"));
                            List<String> lore = new ArrayList<>();
                            lore.addAll(newLore);
                            lore.addAll(oldLore);
                            resultMeta.setLore(lore);
                            result.setItemMeta(resultMeta);
                        } else {
                            List<String> lore = new ArrayList<>(Collections.singletonList("§7Brutal I"));
                            resultMeta.setLore(lore);
                            result.setItemMeta(resultMeta);
                        }
                    } else {
                        List<String> lore = new ArrayList<>(Collections.singletonList("§7Brutal I"));
                        resultMeta.setLore(lore);
                        result.setItemMeta(resultMeta);
                    }

                    // create merchant:
                    Merchant merchant = Bukkit.createMerchant("§8FrameMiner Anvil");

                    // setup trading recipes:
                    List<MerchantRecipe> merchantRecipes = new ArrayList<>();
                    MerchantRecipe recipe = new MerchantRecipe(result, 10000); // no max-uses limit
                    recipe.setExperienceReward(false); // no experience rewards
                    recipe.addIngredient(pickaxe);
                    recipe.addIngredient(book);
                    merchantRecipes.add(recipe);

                    // apply recipes to merchant:
                    merchant.setRecipes(merchantRecipes);

                    // open trading window:
                    player.openMerchant(merchant, true);
                }
            }
        } catch (Exception e) {
            //nothing
        }
    }
}
