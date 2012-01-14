package net.tweakcraft.tweakcart.util;

public class StringUtil {
    public static String stripBrackets(String string){
        if(string.length() > 2){
            int startindex = 0;
            int endindex = string.length();
            if(string.charAt(0) == '['){
                startindex = 1;
            }
            
            if(string.charAt(string.length()-1) == ']'){
                endindex = string.length()-1;
            }
            return string.substring(startindex, endindex);
        }else{
            return string.replaceAll("[\\[\\]]", "");
            
        }
    }
}
