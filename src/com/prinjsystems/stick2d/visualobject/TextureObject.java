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
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Renders a texturized object. Have as input a image file, that is loaded and will
 * be renderized when needed.
 */
public class TextureObject extends RenderObject {
	private BufferedImage texture;
	
	public TextureObject(String texturePath, float x, float y, float sx, float sy, float rotation) throws IOException {
		super(x, y, sx, sy, rotation);
		texture = ImageIO.read(new File(texturePath));
	}
	
	public TextureObject(String texturePath, float x, float y) throws IOException {
		this(texturePath, x, y, 1.0f, 1.0f, 0.0f);
	}
	
	public void updateTexture(String texturePath) throws IOException {
		texture = ImageIO.read(new File(texturePath));
	}
	
	public int getTextureWidth() {
		return texture.getWidth();
	}
	
	public int getTextureHeight() {
		return texture.getHeight();
	}
	
	@Override
	protected void draw() {
		AffineTransform at = new AffineTransform();
		at.scale(sx, sy);
		at.rotate(Math.toRadians(rotation), pivotX, pivotY);
		g.setTransform(at);
		g.drawImage(texture, (int) x, (int) y, null);
	}
}
