package com.goudagames.ld31.gui;

import com.goudagames.ld31.base.Game;
import com.goudagames.ld31.render.FontRenderer;
import com.goudagames.ld31.render.RenderEngine;

public class GuiScore extends GuiObject {
	
	public GuiScore(float x, float y) {
		
		super(x, y, 0f, 0f);
	}
	
	@Override
	public void render() {
		
		RenderEngine.instance.setRGBA(1f, 1f, 1f, 1f);
		
		RenderEngine.instance.renderTexturedQuad(x, y, 48f, 16f, (float) -(Math.PI / 2f), 116f, 0f, 12f, 4f, true, "entities");
		RenderEngine.instance.renderTexturedQuad(x, y - 64f, 48f, 24f, 0f, 0f, 114f, 16f, 8f, true, "entities");
		
		FontRenderer.setColor(160f / 255f, 194f / 255f, 211f / 255f);
		FontRenderer.setSize(24f);
		
		String text =  ": " + Integer.toString(Game.instance.getCarrots());
		FontRenderer.renderString(x + 24f, y - FontRenderer.getStringHeight() / 2f, text);
		
		text =  ": " + Integer.toString(Game.instance.player.getScarves());
		FontRenderer.renderString(x + 24f, y - 64f - FontRenderer.getStringHeight() / 2f, text);
	}
}
