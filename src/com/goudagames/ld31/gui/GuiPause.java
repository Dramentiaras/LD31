package com.goudagames.ld31.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.goudagames.ld31.base.Game;
import com.goudagames.ld31.input.Input;
import com.goudagames.ld31.render.FontRenderer;

public class GuiPause extends GuiObject {

	float scale = 0f;
	
	public GuiPause() {
		
		super(Display.getWidth() / 2f, Display.getHeight() / 2f, Display.getWidth(), Display.getHeight());
	}
	
	@Override
	public void update(float delta) {
		
		if (scale < 1f) {
			
			scale += 2f * delta;
			
			if (scale > 1f) scale = 1f;
		}
		
		if (scale == 1f && Input.isKeyPressed(Keyboard.KEY_ESCAPE)) {
			
			Game.instance.paused = false;
			Game.instance.removeGuiObject(this);
		}
	}
	
	@Override
	public void render() {
		
		FontRenderer.setColor(160f / 255f, 194f / 255f, 211f / 255f);
		FontRenderer.setSize(96f * scale);
		FontRenderer.setPadding(2f * scale);
		
		String text = "PAUSED";
		FontRenderer.renderString(x - FontRenderer.getStringWidth(text) / 2f, y, text);
		
		FontRenderer.setColor(160f / 255f, 194f / 255f, 211f / 255f);
		FontRenderer.setSize(24f * scale);
		FontRenderer.setPadding(2f * scale);
		
		text = "Press ESC to unpause";
		FontRenderer.renderString(x - FontRenderer.getStringWidth(text) / 2f, y - FontRenderer.getStringHeight() / 2f - 100f, text);
	}
}
