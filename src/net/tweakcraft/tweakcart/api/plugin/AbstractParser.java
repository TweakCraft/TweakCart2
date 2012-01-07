package net.tweakcraft.tweakcart.api.plugin;

import org.bukkit.block.Sign;

public abstract class AbstractParser {
    public String removeBrackets(String line){
        String result = line;
        if(result.length() > 0){
            if(result.charAt(0) == '['){
                result.substring(1);
            }
            if(result.charAt(result.charAt(result.length()-1)) == ']'){
                result.substring(0,result.length()-2);
            }
        }
        return result;
    }
    
    public abstract Object parseSign(Sign s);
}
