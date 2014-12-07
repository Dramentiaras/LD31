package com.goudagames.ld31.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.goudagames.ld31.base.Game;
import com.goudagames.ld31.input.Input;
import com.goudagames.ld31.render.FontRenderer;
import com.goudagames.ld31.render.RenderEngine;

public class GuiMainMenu extends GuiObject {

	private float yMod = 0f;
	private float time = 0f;
	private boolean up;
	private boolean finish;
	
	public GuiMainMenu() {
		
		super(Display.getWidth() / 2f, Display.getHeight() / 2f, Display.getWidth(), Display.getHeight());
	}
	
	@Override
	public void update(float delta) {
		
		if (Input.isKeyPressed(Keyboard.KEY_W)) {
			
			up = true;
		}
		
		if (finish) {
			
			if (yMod < Display.getHeight() * 2f) {
				
				yMod += Display.getHeight() * delta;
				
				if (yMod >= Display.getHeight() * 2f) {
					
					Game.instance.mainmenu = false;
					Game.instance.removeGuiObject(this);
				}
			}
		}
		
		if (up && !finish) {
			
			if (yMod < Display.getHeight()) {
				
				yMod += Display.getHeight() * delta;
				
				if (yMod >= Display.getHeight()) {
					
					yMod = Display.getHeight();
				}
			}
			else {
				
				if (Input.isKeyPressed(Keyboard.KEY_UP)) {
					
					finish = true;
				}
			}
		}
		else if (!finish) {
			
			time += delta;
			
			if (time > 0.75f) {
				
				if (yMod == 10f) yMod = 0f;
				else {
					
					yMod = 10f;
				}
				time = 0f;
			}
		}
	}
	
	public void render() {
		
		FontRenderer.setColor(255f / 255f, 106f / 255f, 0f);
		FontRenderer.setSize(96f);
		FontRenderer.setPadding(2f);
		
		String text = "CARROT";
		FontRenderer.renderString(x - FontRenderer.getStringWidth(text) / 2f - 300f, y + 200f + yMod, text);
		
		FontRenderer.setColor(160f / 255f, 194f / 255f, 211f / 255f);
		FontRenderer.setSize(96f);
		FontRenderer.setPadding(2f);
		
		text = "CHAOS";
		FontRenderer.renderString(x - FontRenderer.getStringWidth(text) / 2f - 125f, y + 25f + yMod, text);
		
		FontRenderer.setColor(160f / 255f, 194f / 255f, 211f / 255f);
		FontRenderer.setSize(24f);
		FontRenderer.setPadding(2f);
		
		text = "press W to start";
		FontRenderer.renderString(x - FontRenderer.getStringWidth(text) / 2f - 225f, y - 15f + (up ? yMod:0f), text);
		
		RenderEngine.instance.setRGBA(0f, 0f, 0f, 0.5f);
		RenderEngine.instance.renderQuad(x, y - Display.getHeight() + (up ? yMod:0f), Display.getWidth(), Display.getHeight(), 0f);
		
		FontRenderer.setColor(160f / 255f, 194f / 255f, 211f / 255f);
		FontRenderer.setSize(24f);
		FontRenderer.setPadding(2f);
		
		text = " use WASD to move";
		FontRenderer.renderString(x - FontRenderer.getStringWidth(text) / 2f - 95f, 
				y - Display.getHeight() + 300f + (up ? yMod:0f), text);
		
		text = "use arrow keys to fire";
		FontRenderer.renderString(x - FontRenderer.getStringWidth(text) / 2f - 30f, 
				y - Display.getHeight() + 270f + (up ? yMod:0f), text);
		
		FontRenderer.setColor(0f, 0f, 0.5f);
		
		text = "< this is your temperature.";
		FontRenderer.renderString(x - Display.getWidth() / 2f + 96f, 
				y - Display.getHeight() + 50f + (up ? yMod:0f), text);
		
		text = "keep it high, or you'll freeze.";
		FontRenderer.renderString(x - Display.getWidth() / 2f + 96f, 
				y - Display.getHeight() + 16f + (up ? yMod:0f), text);
		
		text = "and keep moving, it's cold.";
		FontRenderer.renderString(x - Display.getWidth() / 2f + 96f, 
				y - Display.getHeight() - 16f + (up ? yMod:0f), text);
		
		FontRenderer.setColor(255f / 255f, 106f / 255f, 0f);
		
		text = "< COLLECT CARROTS";
		FontRenderer.renderString(x - Display.getWidth() / 2f + 148f,
				y - Display.getHeight() - Display.getHeight() / 2f + (up ? yMod:0f) + 108f, text);
		
		FontRenderer.setColor(0.8f, 0f, 0f);
		
		text = "< SCARVES INCREASE SPEED";
		FontRenderer.renderString(x - Display.getWidth() / 2f + 148f,
				y - Display.getHeight() - Display.getHeight() / 2f + (up ? yMod:0f) + 44f, text);
		
		FontRenderer.setColor(160f / 255f, 194f / 255f, 211f / 255f);
		
		text = "< you";
		FontRenderer.renderString(x + 32f,
				y - Display.getHeight() - Display.getHeight() / 2f + (up ? yMod:0f) + 4f, text);
		
		text = "Press up to start";
		FontRenderer.renderString(x + 164f,
				y - Display.getHeight() - Display.getHeight() / 2f + yMod + 128f, text);
	}
}
