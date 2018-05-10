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
package com.prinjsystems.stick2d.visualobject;

import com.prinjsystems.stick2d.render.RenderObject;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/**
 * Displays text on screen with system fonts. Has a text and a font name as
 * input, and draws the text with the selected font in the screen.
 */
public class SimpleTextObject extends RenderObject {
	private String text;
	private Font font;
	private Color fontColor;

	public SimpleTextObject(String text, Font font, Color fontColor, float x, float y) {
		super(x, y);
		this.text = text;
		this.font = font;
		this.fontColor = fontColor;
	}

	public SimpleTextObject(String text, float x, float y) {
		this(text, new Font("Arial", Font.PLAIN, 21), Color.black, x, y);
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setFont(Font font) {
		this.font = font;
	}
	
	public void setFont(String name, int size) { // Will be always plain
		this.font = new Font(name, Font.PLAIN, size);
	}
	
	public void setFontColor(Color color) {
		this.fontColor = color;
	}
	
	public String getText() {
		return text;
	}
	
	public Font getFont() {
		return font;
	}
	
	public String getFontName() {
		return font.getFontName();
	}
	
	public String getFontFamily() {
		return font.getFamily();
	}
	
	public int getFontSize() {
		return font.getSize();
	}

	@Override
	protected void draw() {
		Color oldColor = g.getColor();
		AffineTransform oldAt = g.getTransform();
		float cx = 0, cy = 0;
		if(camera != null) {
			cx = camera.getX();
			cy = camera.getY();
		}
		AffineTransform at = new AffineTransform();
		at.translate(x + -cx, y + -cy);
		at.rotate(Math.toRadians(rotation), pivotX, pivotY);
		at.scale(sx, sy);
		g.setTransform(at);
		g.setFont(font);
		g.setColor(fontColor);
		String[] lines = text.split("\n");
		for(int i = 0; i < lines.length; i++) {
			g.drawString(lines[i], 0, g.getFontMetrics(g.getFont()).getHeight() * i);
		}
		g.setColor(oldColor); // For some reason, background color is overwritten if re-set
		g.setTransform(oldAt);
	}
	
	@Override
	public Rectangle getBounds() {
		Rectangle2D r2d = g.getFontMetrics(font).getStringBounds(text, g);
		return new Rectangle((int) r2d.getX(), (int) r2d.getY(), (int) r2d.getWidth(), (int) r2d.getHeight());
	}
}
