package com.goudagames.ld31.base;

import org.lwjgl.opengl.Display;

import com.goudagames.ld31.render.RenderEngine;
import com.goudagames.ld31.util.Time;

public class Game {

	private boolean running = false;
	
	public Game() {
		
		Time.init();
		
		start();
	}
	
	public void start() {
		
		running = true;
		loop();
	}
	
	public void loop() {
		
		while (running) {
			
			update(Time.getDelta());
			render();
			
			Display.update();
			Time.update();
			
			if (Display.isCloseRequested()) {
				
				stop();
			}
			
			Display.sync(60);
		}
	}
	
	public void stop() {
		
		running = false;
	}
	
	public void update(float delta) {
		
	}
	
	public void render() {
		
	}
}
