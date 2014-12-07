package com.goudagames.ld31.render;


public class FontRenderer {

	private static float r = 1f, g = 1f, b = 1f, a = 1f;
	private static float pt = 12f;
	private static float padding = 0f;
	
	public static void setColor(float r1, float g1, float b1) {
		
		setColor(r1, g1, b1, 1f);
	}
	
	public static void setColor(float r1, float g1, float b1, float a1) {
		
		r = r1;
		g = g1;
		b = b1;
		a = a1;
	}
	
	public static void setSize(float size) {
		
		pt = size;
	}
	
	public static void setPadding(float p) {
		
		padding = p;
	}
	
	public static float getStringHeight() {
		
		float scale = pt / 12f;
		return scale * 12f;
	}
	
	public static float getStringHeight(float size) {
		
		float scale = size / 12f;
		return scale * 12f;
	}
	
	public static float getStringWidth(String text) {
		
		float scale = pt / 12f;
		return scale * 9f * text.length();
	}
	
	public static float getStringWidth(String text, float size) {
		
		float scale = size / 12f;
		return scale * 9f * text.length();
	}
	
	public static void renderString(float x, float y, String text) {
		
		RenderEngine.instance.setRGBA(r, g, b, a);
		
		float scale = pt / 12f;
		float width = 9f;
		float height = 12f;
		
		text = text.toUpperCase();
		
		for (int i = 0; i < text.length(); i++) {
			
			int c = text.codePointAt(i) - 32;
			
			if (c >= 0 && c < 96) {
				
				float u = (float) ((c % 12f) * 9f);
				float v = (float) (Math.floor(c / 12f) * 12f);
				float mod = 0f;
				
				if (i > 0) {
					mod = padding;
				}
				RenderEngine.instance.renderTexturedQuad(x + width * scale / 2f + i * width * scale + mod * i, y + height * scale / 2f, width * scale,
						height * scale, 0f, u, v, 9f, 12f, true, "font");
			}
		}
	}
}
