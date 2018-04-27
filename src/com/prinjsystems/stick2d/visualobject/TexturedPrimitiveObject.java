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
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.TexturePaint;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Fills a primitive object with a texture (not resizes it, but replicates).
 */
public class TexturedPrimitiveObject extends RenderObject {
	private Shape shape;
	private Color shapeColor;
	private TexturePaint tp;
	private Stroke stroke;
	private boolean drawBorders;
	
	public static final int JUNCTION_CUT = 0;
	public static final int JUNCTION_NATURAL = 1;
	public static final int JUNCTION_ROUND = 2;
	
	public TexturedPrimitiveObject(Shape shape, String texturePath, Rectangle anchor, float x, float y, float sx, float sy, float rotation) {
		super(x, y, sx, sy, rotation);
		this.shape = shape;
		try {
			tp = new TexturePaint(ImageIO.read(new File(texturePath)), anchor);
		} catch (IOException ex) {
			System.err.println("Error reading texture!");
			System.exit(-1);
		}
	}
	
	public TexturedPrimitiveObject(Shape shape, String texturePath, Rectangle anchor, float x, float y) {
		this(shape, texturePath, anchor, x, y, 1.0f, 1.0f, 0.0f);
	}
	
	public TexturedPrimitiveObject(Shape shape, String texturePath, float x, float y) {
		super(x, y, 1.0f, 1.0f, 0.0f);
		this.shape = shape;
		try {
			BufferedImage bi = ImageIO.read(new File(texturePath));
			Rectangle anchor = new Rectangle(0, 0, bi.getWidth(), bi.getHeight());
			tp = new TexturePaint(bi, anchor);
		} catch (IOException ex) {
			System.err.println("Error reading texture!");
			System.exit(-1);
		}
	}
	
	public void setShape(Shape shape) {
		this.shape = shape;
	}
	
	public void setShapeColor(Color shapeColor) {
		this.shapeColor = shapeColor;
	}
	
	public void setBorderStroke(int size, int junctionType) {
		int type;
		switch (junctionType) {
			case JUNCTION_CUT:
				type = BasicStroke.JOIN_BEVEL;
				break;
			case JUNCTION_NATURAL:
				type = BasicStroke.JOIN_MITER;
				break;
			case JUNCTION_ROUND:
				type = BasicStroke.JOIN_ROUND;
				break;
			default:
				type = BasicStroke.JOIN_MITER;
				break;
		}
		stroke = new BasicStroke(size, BasicStroke.CAP_SQUARE, type); // Cap is not customizable, it is not supposed to be viewed
	}
	
	public void setTexture(String texturePath, Rectangle anchor) throws IOException {
		tp = new TexturePaint(ImageIO.read(new File(texturePath)), anchor);
	}
	
	public void setTexture(String texturePath) throws IOException {
		BufferedImage bi = ImageIO.read(new File(texturePath));
		Rectangle anchor = new Rectangle(0, 0, bi.getWidth(), bi.getHeight());
		tp = new TexturePaint(bi, anchor);
	}
	
	public void setDrawBorders(boolean drawBorders) {
		this.drawBorders = drawBorders;
	}
	
	@Override
	protected void draw() {
		Color oldColor = g.getColor();
		AffineTransform oldAt = g.getTransform();
		Paint oldPaint = g.getPaint();
		float cx = 0, cy = 0;
		if(camera != null) {
			cx = camera.getX();
			cy = camera.getY();
		}
		if(shape == null) {
			System.out.println("a");
		}
		AffineTransform at = new AffineTransform();
		at.setToIdentity();
		at.translate(x + -cx, y + -cy);
		at.rotate(Math.toRadians(rotation), pivotX, pivotY);
		at.scale(sx, sy);
		g.setTransform(at);
		g.setPaint(tp);
		g.fill(shape);
		g.setPaint(oldPaint);
		if(drawBorders) {
			Stroke oldStroke = g.getStroke();
			g.setColor(shapeColor);
			g.setStroke(stroke);
			g.draw(shape);
			g.setStroke(oldStroke);
		}
		g.setColor(oldColor);
		g.setTransform(oldAt);
	}
}
