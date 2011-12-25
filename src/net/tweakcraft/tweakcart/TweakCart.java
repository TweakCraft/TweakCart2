package net.tweakcraft.tweakcart;

import net.tweakcraft.tweakcart.listeners.TweakCartBlockListener;
import net.tweakcraft.tweakcart.listeners.TweakCartVehicleListener;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;


public class TweakCart extends JavaPlugin {
    private static Logger log = Logger.getLogger("Minecraft");
    private TweakCartVehicleListener vehicleListener;
    private TweakCartBlockListener blockListener;

    @Override
    public void onDisable() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onEnable() {
        log.info("[TweakCart] Enabling TweakCart");
        // Initialising variables
        vehicleListener = new TweakCartVehicleListener();
        blockListener = new TweakCartBlockListener();

        // Load plugin-manager and register events
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.VEHICLE_MOVE, vehicleListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_DISPENSE, blockListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.VEHICLE_COLLISION_BLOCK, vehicleListener, Event.Priority.Normal, this);
    }

}
