package net.tweakcraft.tweakcart.api.model;

import org.bukkit.Material;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.minecart.*;

public enum CartType {
    MINECART(Material.MINECART),
    STORAGE_MINECART(Material.STORAGE_MINECART),
    POWERED_MINECART(Material.POWERED_MINECART),
    HOPPER_MINECART(Material.HOPPER_MINECART),
    EXPLOSIVE_MINECART(Material.EXPLOSIVE_MINECART),
    COMMAND_MINECART(Material.COMMAND_MINECART);

    private Material material;

    private CartType(Material cartMaterial) {
        material = cartMaterial;
    }

    public static CartType getCartType(Minecart m) {
        if (m instanceof PoweredMinecart) {
            return POWERED_MINECART;
        } else if (m instanceof StorageMinecart) {
            return STORAGE_MINECART;
        } else if (m instanceof HopperMinecart) {
            return HOPPER_MINECART;
        } else if (m instanceof ExplosiveMinecart){
            return EXPLOSIVE_MINECART;
        } else if (m instanceof CommandMinecart){
            return COMMAND_MINECART;
        } else {
            return MINECART;
        }
    }

    public Material getMaterial() {
        return material;
    }
}
