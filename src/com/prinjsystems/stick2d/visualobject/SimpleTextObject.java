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
import java.awt.geom.AffineTransform;

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
		AffineTransform at = new AffineTransform();
		at.scale(sx, sy);
		at.translate(x + -camera.getX(), y + -camera.getY());
		at.rotate(Math.toRadians(rotation), pivotX, pivotY);
		g.setTransform(at);
		g.setFont(font);
		g.setColor(fontColor);
		g.drawString(text, 0, 0);
		g.setColor(oldColor); // For some reason, background color is overwritten if re-set
		g.setTransform(oldAt);
	}
}
