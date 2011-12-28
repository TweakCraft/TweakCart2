package net.tweakcraft.tweakcart.api.plugin;

import org.bukkit.block.Sign;

public abstract class AbstractParser {
    public abstract Object parseSign(Sign s);
}
