package com.goudagames.ld31.gui;

import com.goudagames.ld31.base.Game;
import com.goudagames.ld31.entity.EntityPlayer;
import com.goudagames.ld31.render.RenderEngine;

public class GuiHealthBar extends GuiObject {

	public GuiHealthBar(float x, float y) {
		
		super(x, y, 72f, 336f);
	}
	
	@Override
	public void render() {
		
		EntityPlayer player = Game.instance.player;
		
		RenderEngine.instance.setRGBA(1f, 1f, 1f, 1f);
		float f = (player.temperature - 18f) / (37.5f - 18f);
		
		RenderEngine.instance.renderTexturedQuad(x, y, width, height, 0f, 0f, 0f, 18f, 84f, true, "gui");
		RenderEngine.instance.renderTexturedQuad(x- 2, y - height / 2f + 52f + 110 * f, 44f, 56f + 220 * f, 0f, 18f, 55f - 55f * f, 11f, 14f + 55f * f, true, "gui");
	}
}
