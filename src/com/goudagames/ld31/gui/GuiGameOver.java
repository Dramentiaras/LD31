package com.goudagames.ld31.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.goudagames.ld31.base.Game;
import com.goudagames.ld31.input.Input;
import com.goudagames.ld31.render.FontRenderer;

public class GuiGameOver extends GuiObject {

	float scale = 0f;
	boolean remove = false;
	
	public GuiGameOver() {
		
		super(Display.getWidth() / 2f, Display.getHeight() / 2f, Display.getWidth(), Display.getHeight());
	}
	
	public void update(float delta) {
		
		if (scale < 1f && !remove) {
			
			scale += 2f * delta;
			
			if (scale > 1f) scale = 1f;
		}
		
		if (remove) {
			
			scale -= 2f * delta;
			
			if (scale <= 0f) {
				
				Game.instance.removeGuiObject(this);
			}
		}
		
		if (Input.isKeyReleased(Keyboard.KEY_R)) {
			
			Game.instance.reset();
			remove = true;
		}
	}
	
	public void render() {
		
		FontRenderer.setColor(1f, 0f, 0f);
		FontRenderer.setSize(96f * scale);
		FontRenderer.setPadding(2f * scale);
		
		String text = "GAME OVER";
		FontRenderer.renderString(x - FontRenderer.getStringWidth(text) / 2f, y, text);
		
		FontRenderer.setColor(160f / 255f, 194f / 255f, 211f / 255f);
		FontRenderer.setSize(24f * scale);
		FontRenderer.setPadding(2f * scale);
		
		text = "Press R to restart";
		FontRenderer.renderString(x - FontRenderer.getStringWidth(text) / 2f, y - FontRenderer.getStringHeight() / 2f - 100f, text);
		
		FontRenderer.setColor(1f, 106f / 255f, 0f);
		
		text = "you collected " + Game.instance.getCarrots() + " carrots";
		FontRenderer.renderString(x - FontRenderer.getStringWidth(text) / 2f, y - FontRenderer.getStringHeight() / 2f - 50f, text);
	}
}
