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

import java.util.List;

/**
 * RenderComponent's task is to provide camera-less UI for the user. It consists of
 * a list of common RenderObjects, that will not be affected by the camera and can
 * detect keyboard and mouse events.
 */
public abstract class RenderComponent extends RenderObject {
	protected List<RenderObject> components;
	
	private boolean wasPressing;
	
	public RenderComponent(List<RenderObject> components, float x, float y) {
		super(x, y);
		this.components = components;
		wasPressing = false;
	}
	
	protected abstract void mouseMoved(int mouseX, int mouseY);
	protected abstract void mouseDrag(int mouseX, int mouseY);
	
	protected abstract void mouseClicked(int mouseX, int mouseY);
	protected abstract void mousePressed(int mouseX, int mouseY);
	protected abstract void mouseReleased(int mouseX, int mouseY);
	
	protected abstract void keyClicked(int keyCode); // Key code follows KeyEvent VK_* constants
	protected abstract void keyPressed(int keyCode); // Key code follows KeyEvent VK_* constants
	protected abstract void keyReleased(int keyCode); // Key code follows KeyEvent VK_* constants
	
	final boolean isInside(float x, float y) {
		if(components.get(0).getBounds().contains(x, y)) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	protected void draw() {
		Camera nullCamera = new Camera(0, 0);
		for(RenderObject ro : components) {
			ro.setRenderer(getRenderer());
			ro.setCamera(nullCamera);
			ro.draw();
		}
	}
}
