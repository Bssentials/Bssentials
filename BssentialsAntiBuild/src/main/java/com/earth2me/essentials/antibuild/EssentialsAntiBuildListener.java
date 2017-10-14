package com.earth2me.essentials.antibuild;

//import com.earth2me.essentials.User;
//import net.ess3.api.IEssentials;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Level;

//import static com.earth2me.essentials.I18n.tl;

public class EssentialsAntiBuildListener implements Listener {
    final private transient IAntiBuild prot;
    // final private transient IEssentials ess;

    public EssentialsAntiBuildListener(final IAntiBuild parent) {
        this.prot = parent;
        // this.ess = prot.getEssentialsConnect().getEssentials();
    }

    private boolean metaPermCheck(final Player user, final String action, final int blockId, final short data) {
        final String blockPerm = "essentials.build." + action + "." + blockId;
        // TODO: check meta value
        return user.hasPermission(blockPerm);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockPlace(final BlockPlaceEvent event) {
        final Player user = event.getPlayer();
        final Block block = event.getBlockPlaced();
        final int typeId = block.getTypeId();
        block.getType();

        if (prot.getSettingBool(AntiBuildConfig.disable_build)
                && /* !user.canBuild() && */ !user.hasPermission("essentials.build")
                && !metaPermCheck(user, "place", block)) {
            // if (ess.getSettings().warnOnBuildDisallow()) {
            // user.sendMessage(tl("antiBuildPlace", type.toString()));
            // }
            event.setCancelled(true);
            return;
        }

        if (prot.checkProtectionItems(AntiBuildConfig.blacklist_placement, typeId)
                && !user.hasPermission("essentials.protect.exemptplacement")) {
            // if (ess.getSettings().warnOnBuildDisallow()) {
            // user.sendMessage(tl("antiBuildPlace", type.toString()));
            // }
            event.setCancelled(true);
            return;
        }

        if (prot.checkProtectionItems(AntiBuildConfig.alert_on_placement, typeId)
                && !user.hasPermission("essentials.protect.alerts.notrigger")) {
            // prot.getEssentialsConnect().alert(user, type.toString(),
            // tl("alertPlaced"));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockBreak(final BlockBreakEvent event) {
        final Player user = event.getPlayer();
        final Block block = event.getBlock();
        final int typeId = block.getTypeId();
        block.getType();

        if (prot.getSettingBool(AntiBuildConfig.disable_build) /*
         * &&
         * !user.canBuild
         * ()
         */
                && !user.hasPermission("essentials.build") && !metaPermCheck(user, "break", block)) {
            // if (ess.getSettings().warnOnBuildDisallow()) {
            // user.sendMessage(tl("antiBuildBreak", type.toString()));
            // }
            // TODO
            event.setCancelled(true);
            return;
        }

        if (prot.checkProtectionItems(AntiBuildConfig.blacklist_break, typeId)
                && !user.hasPermission("essentials.protect.exemptbreak")) {
            // if (ess.getSettings().warnOnBuildDisallow()) {
            // user.sendMessage(tl("antiBuildBreak", type.toString()));
            // } // TODO
            event.setCancelled(true);
            return;
        }

        if (prot.checkProtectionItems(AntiBuildConfig.alert_on_break, typeId)
                && !user.hasPermission("essentials.protect.alerts.notrigger")) {
            // TODO prot.getEssentialsConnect().alert(user, type.toString(),
            // tl("alertBroke"));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onHangingBreak(final HangingBreakByEntityEvent event) {
        final Entity entity = event.getRemover();
        if (entity instanceof Player) {
            final Player user = (Player) entity;
            final EntityType type = event.getEntity().getType();
            final boolean warn = false;// ess.getSettings().warnOnBuildDisallow();
            if (prot.getSettingBool(AntiBuildConfig.disable_build)
                    && /* !user.canBuild() && */ !user.hasPermission("essentials.build")) {
                if (type == EntityType.PAINTING
                        && !metaPermCheck(user, "break", Material.PAINTING.getId(), (short) 0)) {
                    if (warn) {
                        // TODO user.sendMessage(tl("antiBuildBreak",
                        // Material.PAINTING.toString()));
                    }
                    event.setCancelled(true);
                } else if (type == EntityType.ITEM_FRAME
                        && !metaPermCheck(user, "break", Material.ITEM_FRAME.getId(), (short) 0)) {
                    // if (warn) {
                    // user.sendMessage(tl("antiBuildBreak",
                    // Material.ITEM_FRAME.toString()));
                    // } TODO
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockPistonExtend(final BlockPistonExtendEvent event) {
        for (Block block : event.getBlocks()) {
            if (prot.checkProtectionItems(AntiBuildConfig.blacklist_piston, block.getTypeId())) {
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockPistonRetract(final BlockPistonRetractEvent event) {
        if (!event.isSticky()) {
            return;
        }
        final Block block = event.getBlock();
        if (prot.checkProtectionItems(AntiBuildConfig.blacklist_piston, block.getTypeId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteract(final PlayerInteractEvent event) {
        // Do not return if cancelled, because the interact event has 2 cancelled states.
        final Player user = event.getPlayer();
        final ItemStack item = event.getItem();

        if (item != null && prot.checkProtectionItems(AntiBuildConfig.blacklist_usage, item.getTypeId())
                && !user.hasPermission("essentials.protect.exemptusage")) {
            // if (ess.getSettings().warnOnBuildDisallow()) {
            // user.sendMessage(tl("antiBuildUse", item.getType().toString()));
            // } TODO
            event.setCancelled(true);
            return;
        }

        if (item != null && prot.checkProtectionItems(AntiBuildConfig.alert_on_use, item.getTypeId())
                && !user.hasPermission("essentials.protect.alerts.notrigger")) {
            // TODO prot.getEssentialsConnect().alert(user,
            // item.getType().toString(), tl("alertUsed"));
        }

        if (prot.getSettingBool(AntiBuildConfig.disable_use)
                && /* !user.canBuild() && */ !user.hasPermission("essentials.build")) {
            if (event.hasItem() && !metaPermCheck(user, "interact", item.getTypeId(), item.getDurability())) {
                event.setCancelled(true);
                // if (ess.getSettings().warnOnBuildDisallow()) {
                // user.sendMessage(tl("antiBuildUse",
                // item.getType().toString()));
                // }
                // TODO
                return;
            }
            if (event.hasBlock() && !metaPermCheck(user, "interact", event.getClickedBlock())) {
                event.setCancelled(true);
                // if (ess.getSettings().warnOnBuildDisallow()) {
                // user.sendMessage(tl("antiBuildInteract",
                // event.getClickedBlock().getType().toString()));
                // }
            }
        }
    }

    private boolean metaPermCheck(Player user, String string, Block clickedBlock) {
        // TODO Auto-generated method stub
        return this.metaPermCheck(user, string, clickedBlock.getTypeId(), clickedBlock.getData());
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onCraftItemEvent(final CraftItemEvent event) {
        HumanEntity entity = event.getWhoClicked();

        if (entity instanceof Player) {
            final Player user = (Player) entity;
            final ItemStack item = event.getRecipe().getResult();

            if (prot.getSettingBool(AntiBuildConfig.disable_use)
                    /* && !user.canBuild() */ && !user.hasPermission("essentials.build")) {
                if (!metaPermCheck(user, "craft", item.getTypeId(), item.getDurability())) {
                    event.setCancelled(true);
                    //if (ess.getSettings().warnOnBuildDisallow()) {
                    //    user.sendMessage(tl("antiBuildCraft", item.getType().toString()));
                    //} TODO
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {

        final Player user = event.getPlayer();
        final ItemStack item = event.getItem().getItemStack();

        if (prot.getSettingBool(AntiBuildConfig.disable_use)
                /* && !user.canBuild() */ && !user.hasPermission("essentials.build")) {
            if (!metaPermCheck(user, "pickup", item.getTypeId(), item.getDurability())) {
                event.setCancelled(true);
                event.getItem().setPickupDelay(50);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPlayerDropItem(final PlayerDropItemEvent event) {

        final Player user = event.getPlayer();
        final ItemStack item = event.getItemDrop().getItemStack();

        if (prot.getSettingBool(AntiBuildConfig.disable_use)
                /* && !user.canBuild() */ && !user.hasPermission("essentials.build")) {
            if (!metaPermCheck(user, "drop", item.getTypeId(), item.getDurability())) {
                event.setCancelled(true);
                user.updateInventory();
                // if (ess.getSettings().warnOnBuildDisallow()) {
                // user.sendMessage(tl("antiBuildDrop",
                // item.getType().toString()));
                // }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockDispense(final BlockDispenseEvent event) {
        final ItemStack item = event.getItem();
        if (prot.checkProtectionItems(AntiBuildConfig.blacklist_dispenser, item.getTypeId())) {
            event.setCancelled(true);
        }
    }
}
