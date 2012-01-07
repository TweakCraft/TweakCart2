package net.tweakcraft.tweakcart.api.event;

import net.tweakcraft.tweakcart.model.Direction;
import org.bukkit.block.Sign;
import org.bukkit.entity.Minecart;

public class VehicleSignEvent {
    private Minecart minecart;
    private Direction direction;
    private Sign sign;
    private String keyword;
    
    public VehicleSignEvent(Minecart c, Direction d, Sign s, String kw) {
        minecart = c;
        direction = d;
        sign = s;
        keyword = kw;
    }

    public Sign getSign() {
        return sign;
    }

    public Minecart getMinecart() {
        return minecart;
    }

    public Direction getDirection() {
        return direction;
    }
    
    public String getKeyword(){
        return keyword;
    }
}
