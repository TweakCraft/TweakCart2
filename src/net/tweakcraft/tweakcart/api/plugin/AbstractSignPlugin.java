package net.tweakcraft.tweakcart.api.plugin;

import net.tweakcraft.tweakcart.TweakCart;
import net.tweakcraft.tweakcart.api.event.TweakVehicleCollidesWithSignEvent;
import net.tweakcraft.tweakcart.api.event.TweakVehiclePassesSignEvent;

public abstract class AbstractSignPlugin extends AbstractBlockPlugin {

    public AbstractSignPlugin(TweakCart p) {
        super(p);
    }

    /**
     * To be implemented by a plugin, register events etc.
     */
    public abstract void onEnable();

    /**
     * Could be overriden, when a cart passes a sign, this method should be called for all
     * registered subplugins
     *
     * @param event TweakVehiclePassesSignEvent with information of event
     */
    public void onSignPass(TweakVehiclePassesSignEvent event) {
    }

    /**
     * Could be overriden, when a cart collides with a sign, this method should be called for all
     * registered subplugins
     *
     * @param event onSignCollisionEvent with information of event
     */
    public void onSignCollision(TweakVehicleCollidesWithSignEvent event) {
    }
}
