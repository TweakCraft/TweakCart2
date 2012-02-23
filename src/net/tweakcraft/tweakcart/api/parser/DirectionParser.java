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
			if (line.length() > 1 && line.charAt(1) == DirectionCharacter.DELIMITER.getCharacter()) {
				return DirectionCharacter.getDirection(line.charAt(0));
			}
		}
		return Direction.SELF;
	}

	public static String removeDirection(String line) {
		if (line.length() > 2) {
			if (line.length() > 1 && line.charAt(1) == DirectionCharacter.DELIMITER.getCharacter()) {
				return line.substring(2);
			}
		}
		return line;
	}

	private enum DirectionCharacter {
		DELIMITER('+'),
		NORTH('n', Direction.NORTH),
		EAST('e', Direction.EAST),
		SOUTH('s', Direction.SOUTH),
		WEST('w', Direction.WEST);

		private char character;
		private Direction direction;


		private DirectionCharacter(char c) {
			character = c;
			direction = Direction.SELF;
		}

		private DirectionCharacter(char c, Direction d) {
			character = c;
			direction = d;
		}

		public char getCharacter() {
			return character;
		}

		public Direction getDirection() {
			return direction;
		}

		public static Direction getDirection(char c) {
			switch(c){
			case 'N':
			case 'n':
				return Direction.NORTH;
			case 'S':
			case 's':
				return Direction.SOUTH;
			case 'E':
			case 'e':
				return Direction.EAST;
			case 'W':
			case 'w':
				return Direction.WEST;
			default:
				return Direction.SELF;
			}
		}

		public static DirectionCharacter getDirectionCharacter(Direction d) {
			switch(d){
			case NORTH:
				return DirectionCharacter.NORTH;
			case SOUTH:
				return DirectionCharacter.SOUTH;
			case EAST:
				return DirectionCharacter.EAST;
			case WEST:
				return DirectionCharacter.WEST;
			default:
				return DirectionCharacter.DELIMITER;
			}
		}
		public static DirectionCharacter getDirectionCharacter(char c) {
			Direction d = getDirection(c);
			return d != null ? getDirectionCharacter(d) : null;
		}
	}
}
