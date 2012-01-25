package net.tweakcraft.tweakcart;

import net.tweakcraft.tweakcart.listeners.TweakCartBlockListener;
import net.tweakcraft.tweakcart.listeners.TweakCartPlayerListener;
import net.tweakcraft.tweakcart.listeners.TweakCartVehicleListener;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;


public class TweakCart extends JavaPlugin {
    private static Logger logger = Logger.getLogger("Minecraft");
    private TweakCartVehicleListener vehicleListener;
    private TweakCartBlockListener blockListener;
    private TweakCartPlayerListener playerListener;

    @Override
    public void onDisable() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onEnable() {
        log(String.format("Enabling! Version: %s", this.getDescription().getVersion()));
        // Initialising variables
        vehicleListener = new TweakCartVehicleListener();
        blockListener = new TweakCartBlockListener();
        playerListener = new TweakCartPlayerListener();
        // Load plugin-manager and register events
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.VEHICLE_MOVE, vehicleListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_DISPENSE, blockListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.VEHICLE_COLLISION_BLOCK, vehicleListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Event.Priority.Normal, this);
        //pm.registerEvent(Event.Type.REDSTONE_CHANGE, blockListener, Event.Priority.Normal, this);
    }

    public void log(String info, Level level) {
        logger.log(level, "[TweakCart] " + info);
    }

    public void log(String info) {
        log(info, Level.INFO);
    }
    
    public TweakPluginManager getPluginManager(){
        return TweakPluginManager.getInstance();
    }
}
