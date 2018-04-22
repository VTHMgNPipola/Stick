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
import java.awt.Shape;
import java.awt.geom.AffineTransform;

/**
 * Renders data as a primitive AWT shape.
 */
public class PrimitiveObject extends RenderObject {
	private Shape shape;
	private Color c;
	private boolean hollow;
	
	public PrimitiveObject(Shape shape, Color c, float x, float y, float sx, float sy, float rotation) {
		super(x, y, sx, sy, rotation);
		this.shape = shape;
		this.c = c;
		hollow = true;
	}
	
	public PrimitiveObject(Shape shape, Color c, float x, float y) {
		this(shape, c, x, y, 1.0f, 1.0f, 0.0f);
	}
	
	public PrimitiveObject(Shape shape, float x, float y) {
		this(shape, Color.green, x, y); // Color is green just because is the first one come in my mind.
	}
	
	public PrimitiveObject(Shape shape) {
		this(shape, 0.0f, 0.0f); // Some shapes have their own position, so the in-class one will confuse things.
	}
	
	public void setShape(Shape shape) {
		this.shape = shape;
	}
	
	public void setHollow(boolean hollow) {
		this.hollow = hollow;
	}
	
	public Shape getShape() {
		return shape;
	}
	
	public boolean isHollow() {
		return hollow;
	}
	
	@Override
	protected void draw() {
		Color oldColor = g.getColor();
		g.setColor(c);
		AffineTransform at = new AffineTransform();
		at.setToIdentity();
		at.scale(sx, sy);
		at.translate(x + shape.getBounds().x, y + shape.getBounds().y);
		at.rotate(Math.toRadians(rotation), pivotX, pivotY);
		g.setTransform(at);
		if(hollow) {
			g.draw(shape);
		} else {
			g.fill(shape);
		}
		g.setColor(oldColor);
	}	
}
