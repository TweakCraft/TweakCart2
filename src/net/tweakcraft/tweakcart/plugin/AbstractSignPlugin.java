package net.tweakcraft.tweakcart.plugin;

import net.tweakcraft.tweakcart.api.TweakCartEvent;

public abstract class AbstractSignPlugin extends AbstractBlockPlugin {

    /**
     * Register your events by the manager
     */
    public abstract void registerEvents(TweakCartEvent... events);

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
     * @param arg 
     */
    public void onSignPass(Object arg){}
    
    /**
     * Could be overriden, when a cart collides with a sign, this method should be called for all
     * registered subplugins
     * @param arg 
     */
    public void onSignCollision(Object arg){}



}
