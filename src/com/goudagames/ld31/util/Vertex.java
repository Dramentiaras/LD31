package com.goudagames.ld31.util;



public class Vertex {

	protected float[] xy = new float[] {0f, 0f};
	protected float[] rgba = new float[] {1f, 1f, 1f, 1f};
	private float[] uv = new float[] {0f, 0f};
	
	public static int elementBytes = 4;
	
	public static int positionElementCount = 2;
	public static int colorElementCount = 4;
	public static int textureElementCount = 2;
	
	public static int positionBytesCount = positionElementCount * elementBytes;
	public static int colorByteCount = colorElementCount * elementBytes;
	public static int textureByteCount = textureElementCount * elementBytes;
	
	public static int positionByteOffset = 0;
	public static int colorByteOffset = positionByteOffset + positionBytesCount;
	public static int textureByteOffset = colorByteOffset + colorByteCount;
	
	public static int elementCount = positionElementCount + 
			colorElementCount + textureElementCount;	

	public static int stride = positionBytesCount + colorByteCount + 
			textureByteCount;
	
	public Vertex setXY(float x, float y) {
		xy = new float[] {x, y};
		return this;
	}
	
	public Vertex setColor(float r, float g, float b, float a) {
		this.rgba = new float[] {r, g, b, a};
		return this;
	}
	
	public float[] getXY() {
		return xy;
	}
	
	public float[] getRGBA() {
		return rgba;
	}

	public Vertex setUV(float u, float v) {
		this.uv = new float[] {u, v};
		return this;
	}
	
	public float[] getUV() {
		return new float[] {this.uv[0], this.uv[1]};
	}

	public float[] getElements() {
		float[] out = new float[Vertex.elementCount];
		int i = 0;
			
		out[i++] = this.xy[0];
		out[i++] = this.xy[1];

		out[i++] = this.rgba[0];
		out[i++] = this.rgba[1];
		out[i++] = this.rgba[2];
		out[i++] = this.rgba[3];

		out[i++] = this.uv[0];
		out[i++] = this.uv[1];
			
		return out;
	}
}
