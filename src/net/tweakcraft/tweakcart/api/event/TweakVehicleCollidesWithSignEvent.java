package net.tweakcraft.tweakcart.api.event;

import net.tweakcraft.tweakcart.model.Direction;
import org.bukkit.block.Sign;
import org.bukkit.entity.Minecart;

public class TweakVehicleCollidesWithSignEvent extends VehicleSignEvent {
    public TweakVehicleCollidesWithSignEvent(Minecart c, Direction d, Sign s) {
        super(c, d, s);
    }
}
