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
import java.awt.event.KeyListener;
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
	
	public void addKeyboardListener(KeyListener kl) {
		c.addKeyListener(kl);
	}
	
	public void addMouseListener(MouseListener ml) {
		c.addMouseListener(ml);
	}
	
	public void addMouseMotionListener(MouseMotionListener mml) {
		c.addMouseMotionListener(mml);
	}
	
	public boolean isClosing() {
		return closing;
	}
}
