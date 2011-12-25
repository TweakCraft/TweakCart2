package net.tweakcraft.tweakcart.event;

import org.bukkit.block.Sign;

public class VehicleSignEvent {
    private Sign sign;

    public VehicleSignEvent(Sign s) {
        sign = s;
    }

    public Sign getSign() {
        return sign;
    }
}
