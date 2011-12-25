package net.tweakcraft.tweakcart.model;

import org.bukkit.entity.Minecart;
import org.bukkit.entity.PoweredMinecart;
import org.bukkit.entity.StorageMinecart;

public enum CartType {
    StorageMinecart(),
    PoweredMinecart(),
    Minecart();

    public CartType getCartType(Minecart cart) {
        if (cart instanceof StorageMinecart) {
            return CartType.StorageMinecart;
        } else if (cart instanceof PoweredMinecart) {
            return CartType.PoweredMinecart;
        } else {
            return CartType.Minecart;
        }
    }
}
