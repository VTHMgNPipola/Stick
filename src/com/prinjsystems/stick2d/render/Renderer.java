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
package com.prinjsystems.stick2d.render;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.VolatileImage;

/**
 * Have the capabilities to render on screen.
 */
public class Renderer {
	private VolatileImage img;
	private Graphics2D g;
	
	private Color bgc;
	private Camera c;
	
	public Renderer(Display d) {
		img = d.img;
		g = (Graphics2D) img.getGraphics();
	}
	
	public Renderer(Display d, Camera c) {
		this(d);
		this.c = c;
	}
	
	public void setCamera(Camera c) {
		this.c = c;
	}
	
	public void setBackgroundColor(Color bgc) {
		this.bgc = bgc;
	}
	
	private void clearScreen() {
		g.setColor(bgc);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		g.fillRect(0, 0, img.getWidth(), img.getHeight());
	}
	
	public void render() {
		clearScreen();
	}
}
