package com.goudagames.ld31.input;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Input {

	private static boolean[] keys = new boolean[256];
	private static boolean[] buttons = new boolean[3];
	
	public static void update() {
		
		for (int i = 0; i < keys.length; i++) {
			
			keys[i] = Keyboard.isKeyDown(i);
		}
		
		for (int i = 0; i < buttons.length; i++) {
			
			buttons[i] = Mouse.isButtonDown(i);
		}
	}
	
	public static boolean isKeyPressed(int code) {
		
		return !keys[code] && Keyboard.isKeyDown(code);
	}
	
	public static boolean isKeyReleased(int code) {
		
		return keys[code] && !Keyboard.isKeyDown(code);
	}
	
	public static boolean isKeyDown(int code) {
		
		return Keyboard.isKeyDown(code);
	}
	
	public static boolean isButtonClicked(int code) {
		
		return !buttons[code] && Mouse.isButtonDown(code);
	}
	
	public static boolean isButtonReleased(int code) {
		
		return buttons[code] && !Mouse.isButtonDown(code);
	}
	
	public static boolean isButtonDown(int code) {
		
		return Mouse.isButtonDown(code);
	}
}
