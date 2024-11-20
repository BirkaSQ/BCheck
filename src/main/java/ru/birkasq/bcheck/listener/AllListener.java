    package ru.birkasq.bcheck.listener;

    import java.util.UUID;
    import org.bukkit.event.EventHandler;
    import org.bukkit.event.Listener;
    import org.bukkit.event.entity.EntityDamageByEntityEvent;
    import org.bukkit.event.entity.EntityDamageEvent;
    import org.bukkit.event.entity.EntityPickupItemEvent;
    import org.bukkit.event.inventory.InventoryClickEvent;
    import org.bukkit.event.player.PlayerDropItemEvent;
    import org.bukkit.event.player.PlayerInteractEvent;
    import org.bukkit.event.player.PlayerMoveEvent;
    import ru.birkasq.bcheck.command.FreezeCommand;
    import ru.birkasq.bcheck.BCheck;

    public class AllListener
            implements Listener {
        private BCheck plugin;

        public AllListener(BCheck plugin) {
            this.plugin = plugin;
        }

        @EventHandler
        public void onMove(PlayerMoveEvent playerMoveEvent) {
            UUID player;
            if (this.plugin.getConfig().getBoolean("settings-event.PlayerMoveEvent") && FreezeCommand.freezedPlayers.contains(player = playerMoveEvent.getPlayer().getUniqueId())) {
                playerMoveEvent.setCancelled(true);
            }
        }

        @EventHandler
        public void onInteract(PlayerInteractEvent playerInteractEvent) {
            UUID player;
            if (this.plugin.getConfig().getBoolean("settings-event.PlayerInteractEvent") && FreezeCommand.freezedPlayers.contains(player = playerInteractEvent.getPlayer().getUniqueId())) {
                playerInteractEvent.setCancelled(true);
            }
        }

        @EventHandler
        public void onDrop(PlayerDropItemEvent playerDropItemEvent) {
            UUID player;
            if (this.plugin.getConfig().getBoolean("settings-event.PlayerDropItemEvent") && FreezeCommand.freezedPlayers.contains(player = playerDropItemEvent.getPlayer().getUniqueId())) {
                playerDropItemEvent.setCancelled(true);
            }
        }

        @EventHandler
        public void onDamage(EntityDamageEvent entityDamageEvent) {
            UUID player;
            if (this.plugin.getConfig().getBoolean("settings-event.EntityDamageEvent") && FreezeCommand.freezedPlayers.contains(player = entityDamageEvent.getEntity().getUniqueId())) {
                entityDamageEvent.setCancelled(true);
            }
        }

        @EventHandler
        public void onPicUp(EntityPickupItemEvent entityPickupItemEvent) {
            UUID player;
            if (this.plugin.getConfig().getBoolean("settings-event.EntityPickupItemEvent") && FreezeCommand.freezedPlayers.contains(player = entityPickupItemEvent.getEntity().getUniqueId())) {
                entityPickupItemEvent.setCancelled(true);
            }
        }

        @EventHandler
        public void onDamageEntity(EntityDamageByEntityEvent entityDamageByEntityEvent) {
            UUID player;
            if (this.plugin.getConfig().getBoolean("settings-event.EntityDamageByEntityEvent") && FreezeCommand.freezedPlayers.contains(player = entityDamageByEntityEvent.getDamager().getUniqueId())) {
                entityDamageByEntityEvent.setCancelled(true);
            }
        }

        @EventHandler
        public void onInventoryClick(InventoryClickEvent inventoryClickEvent) {
            UUID player;
            if (this.plugin.getConfig().getBoolean("settings-event.InventoryClickEvent") && FreezeCommand.freezedPlayers.contains(player = inventoryClickEvent.getWhoClicked().getUniqueId())) {
                inventoryClickEvent.setCancelled(true);
            }
        }
    }
