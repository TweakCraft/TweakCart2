package net.tweakcraft.tweakcart.api.event;

import net.tweakcraft.tweakcart.model.Direction;
import org.bukkit.block.Sign;
import org.bukkit.entity.Minecart;

public class TweakVehiclePassesSignEvent extends VehicleSignEvent {
    public TweakVehiclePassesSignEvent(Minecart c, Direction d, Sign s, String kw) {
        super(c, d, s, kw);
    }
}
