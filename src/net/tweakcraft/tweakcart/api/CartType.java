package net.tweakcraft.tweakcart.api;

import org.bukkit.entity.Minecart;
import org.bukkit.entity.PoweredMinecart;
import org.bukkit.entity.StorageMinecart;

public enum CartType {
    MINECART,
    STORAGECART,
    POWEREDCART;
    
    public static CartType getCartType(Minecart m){
        if(m instanceof PoweredMinecart){
            return POWEREDCART;
        }else if(m instanceof StorageMinecart){
            return STORAGECART;
        }else{
            return MINECART;
        }
    }
}
