package com.goudagames.ld31.base;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import com.goudagames.ld31.render.RenderEngine;

public class Launch {

	public static void main(String[] args) {
		
		try {
			
			PixelFormat format = new PixelFormat();
			ContextAttribs attribs = new ContextAttribs(3, 3)
					.withForwardCompatible(true).withProfileCore(true);
			
			Display.setDisplayMode(new DisplayMode(1296, 720));
			Display.setTitle("Carrot Chaos");
			Display.create(format, attribs);
			Keyboard.create();
			Mouse.create();
		}
		catch (Exception ex) {
			
			ex.printStackTrace();
		}
		
		RenderEngine.init();
		new Game();
	}
}
