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

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.UUID;

/**
 * Defines a renderable object, that can be primitive or texture.
 */
public abstract class RenderObject {
	protected float pivotX, pivotY;
	protected float x, y;
	protected float sx, sy;
	protected float rotation;
	protected Graphics2D g;
	protected Camera camera;
	protected String id;
	
	public RenderObject(float x, float y, float sx, float sy, float rotation) {
		this.x = x;
		this.y = y;
		this.sx = sx;
		this.sy = sy;
		id = UUID.randomUUID().toString();
	}
	
	public RenderObject(float x, float y) {
		this(x, y, 1.0f, 1.0f, 0.0f);
	}
	
	final void setRenderer(Graphics2D g) { // Final to prevent bugs when getting the package-private renderer.
		this.g = g;
	}
	
	final Graphics2D getRenderer() {
		return g;
	}
	
	final void setCamera(Camera camera) {
		this.camera = camera;
	}
	
	public void setRotationPivot(float pivotX, float pivotY) {
		this.pivotX = pivotX;
		this.pivotY = pivotY;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void setScaleX(float sx) {
		this.sx = sx;
	}
	
	public void setScaleY(float sy) {
		this.sy = sy;
	}
	
	public void setRotation(float rotation) { // Rotation is in degrees, not radians.
		this.rotation = rotation;
	}
	
	public void translate(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
	public void rotate(float rotation) { // Rotation is in degrees, not radians.
		this.rotation += rotation;
	}
	
	public void scale(float sx, float sy) {
		this.sx += sx;
		this.sy += sy;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getScaleX() {
		return sx;
	}
	
	public float getScaleY() {
		return sy;
	}
	
	public float getRotation() {
		return rotation;
	}
	
	public String getId() {
		return id;
	}
	
	protected abstract void draw();
	
	public abstract Rectangle getBounds();
}
