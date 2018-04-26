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
import java.awt.RenderingHints;
import java.awt.image.VolatileImage;
import java.util.List;

/**
 * Have the capabilities to render on screen.
 */
public class Renderer {
	private VolatileImage img;
	Graphics2D g;
	
	private Color bgc;
	private Camera c;
	
	public static final int INTERPOLATION_NEAREST_NEIGHBOR = 0;
	public static final int INTERPOLATION_BICUBIC = 1;
	public static final int INTERPOLATION_BILINEAR = 2;
	
	public Renderer(Display d) {
		img = d.img;
		g = (Graphics2D) img.getGraphics();
		bgc = Color.black;
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
	
	public Color getBackgroundColor() {
		return bgc;
	}
	
	private void clearScreen() {
		g.setColor(bgc);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		g.fillRect(0, 0, img.getWidth(), img.getHeight());
	}
	
	public void render(List<RenderObject> objects) {
		clearScreen();
		for(RenderObject ro : objects) {
			ro.setRenderer(this);
			ro.setCamera(c);
			ro.draw();
		}
	}
	
	public void setRenderQuality(boolean renderQuality) {
		if(renderQuality) {
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		} else {
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		}
	}
	
	public void setAntialiasing(boolean antialias) { // Defines java "integrated" antialiasing, that takes a on or off value.
		if(antialias) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
		} else {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		}
	}
	
	public void setInterpolation(int type) {
		switch (type) {
			case INTERPOLATION_NEAREST_NEIGHBOR:
				g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
				break;
			case INTERPOLATION_BICUBIC:
				g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
				break;
			case INTERPOLATION_BILINEAR:
				g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				break;
			default:
				break;
		}
	}
	
	public void setAlphaInterpolation(boolean aiQuality) {
		if(aiQuality) {
			g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		} else {
			g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
		}
	}
	
	public void setDithering(boolean enable) {
		if(enable) {
			g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		} else {
			g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
		}
	}
}
