/*
 * Copyright (c) 2012.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package net.tweakcraft.tweakcart.api.listeners;

import org.bukkit.block.Sign;

@Deprecated
public abstract class AbstractParser {
    /**
     * @deprecated use SingUtil.stripBrackets
     * @param line
     * @return
     */
    @Deprecated
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
