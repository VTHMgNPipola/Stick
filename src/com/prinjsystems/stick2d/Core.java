/*
 * Copyright (C) 2018 PrinJ Systems
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.prinjsystems.stick2d;

/**
 * This class holds basic information about the rendering engine.
 */
public class Core {
	private static int width;
	private static int height;
	private static String iconFile;
	
	static {
		//System.setProperty("java.awt.headless", "false");
		width = 800;
		height = 600;
		iconFile = "logo-small.png";
	}
	
	public static void setWidth(int w) {
		width = w;
	}
	
	public static void setHeight(int h) {
		height = h;
	}
	
	public static void setResolution(int w, int h) {
		width = w;
		height = h;
	}
	
	public static void setAccelerated(boolean value) {
		System.setProperty("sun.java2d.opengl", String.valueOf(value));
	}
	
	public static void setFrameIcon(String iconFilepath) {
		iconFile = iconFilepath;
	}
	
	public static int getWidth() {
		return width;
	}
	
	public static int getHeight() {
		return height;
	}
	
	public static String getIconFile() {
		return iconFile;
	}
}
