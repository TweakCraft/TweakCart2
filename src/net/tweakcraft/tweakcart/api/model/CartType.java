package net.tweakcraft.tweakcart.api.model;

import org.bukkit.Material;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.PoweredMinecart;
import org.bukkit.entity.StorageMinecart;

public enum CartType {
    MINECART(Material.MINECART),
    STORAGE_MINECART(Material.STORAGE_MINECART),
    POWERED_MINECART(Material.POWERED_MINECART);

    private Material material;

    private CartType(Material cartMaterial) {
        material = cartMaterial;
    }

    public static CartType getCartType(Minecart m) {
        if (m instanceof PoweredMinecart) {
            return POWERED_MINECART;
        } else if (m instanceof StorageMinecart) {
            return STORAGE_MINECART;
        } else {
            return MINECART;
        }
    }

    public Material getMaterial() {
        return material;
    }
}
