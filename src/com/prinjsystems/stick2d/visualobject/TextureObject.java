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
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
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
	private Color maskColor;
	private float maskAlpha;
	private Rectangle2D.Float clip;
	
	public TextureObject(String texturePath, float x, float y, float sx, float sy, float rotation) throws IOException {
		super(x, y, sx, sy, rotation);
		texture = ImageIO.read(new File(texturePath));
		clip = new Rectangle2D.Float(0, 0, texture.getWidth(), texture.getHeight());
	}
	
	public TextureObject(String texturePath, float x, float y) throws IOException {
		this(texturePath, x, y, 1.0f, 1.0f, 0.0f);
	}
	
	public void updateTexture(String texturePath) throws IOException {
		texture = ImageIO.read(new File(texturePath));
	}
	
	public void setClip(Rectangle2D.Float clip) {
		this.clip = clip;
	}
	
	public void setClip(float clipX, float clipY, float clipSizeX, float clipSizeY) {
		clip = new Rectangle2D.Float(clipX, clipY, clipSizeX, clipSizeY);
	}
	
	public void translateClip(float x, float y) {
		clip.x += x;
		clip.y += y;
	}
	
	public void setMaskColor(Color maskColor) { // If null, no mask will be applied.
		this.maskColor = maskColor;
	}
	
	public void setMaskAlpha(float alphaValue) { // From 0.0f to 1.0f
		maskAlpha = alphaValue;
	}
	
	public void resetMask() {
		maskColor = null;
		maskAlpha = 0;
	}
	
	public int getTextureWidth() {
		return texture.getWidth();
	}
	
	public int getTextureHeight() {
		return texture.getHeight();
	}
	
	public Rectangle2D.Float getClip() {
		return clip;
	}
	
	public Color getMaskColor() {
		return maskColor;
	}
	
	public float getMaskAlpha() {
		return maskAlpha;
	}
	
	@Override
	protected void draw() {
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
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1 - maskAlpha);
		g.setComposite(ac);
		BufferedImage img = applyMask(texture.getSubimage((int) clip.x, (int) clip.y, (int) clip.width, (int) clip.height));
		g.drawImage(img, 0, 0, null);
		g.setTransform(oldAt);
	}
	
	private BufferedImage applyMask(BufferedImage texture) {
		if(maskColor == null) {
			return texture;
		}
		
		BufferedImage result = new BufferedImage(texture.getWidth(), texture.getHeight(), texture.getType());
		for(int x = 0; x < texture.getWidth(); x++) {
			for(int y = 0; y < texture.getHeight(); y++) {
				int cValue = texture.getRGB(x, y);
				if(cValue >> 24 == 0x00) continue; // If pixel is transparent, doesn't mask it
				Color c = new Color(cValue);
				Color newColor = new Color(c.getRed() & maskColor.getRed(),
						c.getGreen() & maskColor.getGreen(), c.getBlue() & maskColor.getBlue(),
						c.getAlpha() & maskColor.getAlpha());
				result.setRGB(x, y, newColor.getRGB());
			}
		}
		
		return result;
	}
}
