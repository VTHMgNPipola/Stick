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

import com.prinjsystems.stick2d.Core;
import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * Have functionality to display graphics, and can apply input handlers.
 */
public class Display {
	private GraphicsConfiguration gc;
	private JFrame frame;
	private Canvas c;
	VolatileImage img; // Package private
	private Graphics2D g;
	private BufferStrategy bs;
	private boolean closing;
	
	private boolean click, pressing, dragging, moving;
	private int mouseX, mouseY;
	private boolean[] keys;
	
	public Display(String title) throws IOException {
		closing = false;
		gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		frame = new JFrame(title, gc);
		c = new Canvas(gc);
		c.setBounds(0, 0, Core.getWidth(), Core.getHeight());
		img = gc.createCompatibleVolatileImage(Core.getWidth(), Core.getHeight());
		assert img != null;
		frame.setSize(Core.getWidth(), Core.getHeight());
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				closing = true;
			}
		});
		frame.add(c);
		frame.setIconImage(ImageIO.read(new File(Core.getIconFile())));
		
		keys = new boolean[65536];
		click = false;
		mouseX = mouseY = 0;
		c.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent me) {
				click = true;
				mouseX = me.getX();
				mouseY = me.getY();
			}

			@Override
			public void mousePressed(MouseEvent me) {
				pressing = true;
				mouseX = me.getX();
				mouseY = me.getY();
			}

			@Override
			public void mouseReleased(MouseEvent me) {
				pressing = false;
				mouseX = me.getX();
				mouseY = me.getY();
			}

			@Override
			public void mouseEntered(MouseEvent me) {
			}

			@Override
			public void mouseExited(MouseEvent me) {
			}
		});
		c.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent me) {
				dragging = true;
				mouseX = me.getX();
				mouseY = me.getY();
			}

			@Override
			public void mouseMoved(MouseEvent me) {
				moving = true;
				mouseX = me.getX();
				mouseY = me.getY();
			}
		});
	}
	
	final boolean isClicking() {
		boolean oldState = click;
		click = false;
		return oldState;
	}
	
	final boolean isPressing() {
		return pressing;
	}
	
	final boolean isDragging() {
		boolean oldState = dragging;
		dragging = false; // Only one component should be activated, so this is fine
		return oldState;
	}
	
	final boolean isMoving() {
		boolean oldState = moving;
		moving = false; // As before, just one component should be activated.
		return oldState;
	}
	
	final boolean getKeyState(int keyCode) {
		return keys[keyCode];
	}
	
	final int getMouseX() {
		return mouseX;
	}
	
	final int getMouseY() {
		return mouseY;
	}
	
	public void startup() {
		frame.setVisible(true);
		c.createBufferStrategy(2);
		bs = c.getBufferStrategy();
		g = (Graphics2D) bs.getDrawGraphics();
		assert g != null;
	}
	
	public void update() {
		assert g != null;
		if(img.validate(gc) == VolatileImage.IMAGE_INCOMPATIBLE) {
			img = gc.createCompatibleVolatileImage(Core.getWidth(), Core.getHeight());
		}
		g.drawImage(img, 0, 0, null);
		bs.show();
	}
	
	public void destroy() {
		frame.dispose();
		g.dispose();
		bs.dispose();
	}
	
	public boolean isClosing() {
		return closing;
	}
}
