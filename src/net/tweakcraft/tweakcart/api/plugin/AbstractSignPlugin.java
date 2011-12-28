package net.tweakcraft.tweakcart.api.plugin;

import net.tweakcraft.tweakcart.api.event.TweakVehicleCollidesWithSignEvent;
import net.tweakcraft.tweakcart.api.event.TweakVehiclePassesSignEvent;

public abstract class AbstractSignPlugin extends AbstractBlockPlugin {

    /**
     * To be implemented by a plugin
     */
    public abstract void onEnable();

    /**
     * To be implemented by a plugin
     * The keyword is the word used to find a plugin when an
     * signpass/signcollision event is created
     */
    public abstract void RegisterSignEvents();

    /**
     * Could be overriden, when a cart passes a sign, this method should be called for all
     * registered subplugins
     *
     * @param event
     */
    public void onSignPass(TweakVehiclePassesSignEvent event) {
    }

    /**
     * Could be overriden, when a cart collides with a sign, this method should be called for all
     * registered subplugins
     *
     * @param event
     */
    public void onSignCollision(TweakVehicleCollidesWithSignEvent event) {
    }


}
