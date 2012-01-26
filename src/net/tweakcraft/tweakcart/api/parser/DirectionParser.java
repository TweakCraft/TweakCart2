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

package net.tweakcraft.tweakcart.api.parser;

import net.tweakcraft.tweakcart.model.Direction;

import java.util.HashMap;

public class DirectionParser {

    public static Direction parseDirection(String line) {
        if (line.length() > 2) {
            if (line.charAt(1) == DirectionCharacter.DELIMITER.getCharacter().charAt(0)) {
                return DirectionCharacter.getDirectionCharacter(Character.toString(line.charAt(0))).getDirection();
            }
        }
        return Direction.SELF;
    }

    public static String removeDirection(String line) {
        if (line.length() > 2) {
            if (line.charAt(1) == DirectionCharacter.DELIMITER.getCharacter().charAt(0)) {
                return line.substring(2);
            }
        }
        return line;
    }

    private enum DirectionCharacter {
        DELIMITER("+"),
        NORTH("n", Direction.NORTH),
        EAST("e", Direction.EAST),
        SOUTH("s", Direction.SOUTH),
        WEST("w", Direction.WEST);

        private String character;
        private Direction direction;
        private static HashMap<String, Direction> directionMap = new HashMap<String, Direction>();
        private static HashMap<Direction, DirectionCharacter> directionCharacterMap = new HashMap<Direction, DirectionCharacter>();

        static {
            for (DirectionCharacter d : DirectionCharacter.values()) {
                directionMap.put(d.getCharacter(), d.getDirection());
                directionCharacterMap.put(d.getDirection(), d);
            }
        }

        private DirectionCharacter(String c) {
            character = c;
            direction = Direction.SELF;
        }

        private DirectionCharacter(String c, Direction d) {
            character = c;
            direction = d;
        }

        public String getCharacter() {
            return character;
        }

        public Direction getDirection() {
            return direction;
        }

        public static Direction getDirection(String c) {
            Direction d = directionMap.get(c);
            return (d == null) ? Direction.SELF : d;
        }

        public static DirectionCharacter getDirectionCharacter(Direction d) {
            return directionCharacterMap.get(d);
        }

        public static DirectionCharacter getDirectionCharacter(String c) {
            Direction d = directionMap.get(c);
            if (d != null) {
                return directionCharacterMap.get(d);
            } else {
                return null;
            }
        }
    }
}
