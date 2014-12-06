package com.goudagames.ld31.render;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.goudagames.ld31.texture.Texture;

public class RenderEngine {

	public static RenderEngine instance;
	private static boolean initiated;
	
	private Matrix4f projection, model;
	
	private int vaoID, vboID, vboiID;
	private int program;
	
	private Vector2f[] quad;
	
	private float r, g, b, a;
	
	private int projLocation, modelLocation;
	
	public void renderTexturedQuad(float x, float y, float width, float height, float rotation,
			float u, float v, float tw, float th, boolean normalize, Texture texture) {
		
		model = new Matrix4f();
		
		x = Math.round(x);
		y = Math.round(y);
		
		if (normalize) {
			
			u /= texture.getWidth();
			v /= texture.getHeight();
			width /= texture.getWidth();
			height /= texture.getHeight();
		}
		
		Vector2f[] texCoords = new Vector2f[] {
				new Vector2f(u, v + th),
				new Vector2f(u, v),
				new Vector2f(u + tw, v),
				new Vector2f(u + tw, v + th)
		};
		
		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(quad.length * 8);
		for (int i = 0; i < quad.length; i++) {
			
			vertexBuffer.put(quad[i].x);
			vertexBuffer.put(quad[i].y);
			vertexBuffer.put(new float[] {r, g, b, a});
			vertexBuffer.put(new float[] {texCoords[i].x, texCoords[i].y});
		}
		vertexBuffer.flip();
		
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW);
	
		model.translate(new Vector2f(x, y));
		model.scale(new Vector3f(width, height, 0f));
		
		FloatBuffer projBuf = BufferUtils.createFloatBuffer(16);
		projection.store(projBuf); projBuf.flip();
		GL20.glUniformMatrix4(projLocation, false, projBuf);
		
		FloatBuffer modelBuf = BufferUtils.createFloatBuffer(16);
		model.store(modelBuf); modelBuf.flip();
		GL20.glUniformMatrix4(modelLocation, false, modelBuf);
		
		texture.bind();
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiID);
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_BYTE, 0);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public void setRGBA(float r, float g, float b, float a) {
		
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public void setRGB(float r, float g, float b) {
		
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public void setAlpha(float a) {
		
		this.a = a;
	}
	
	public void setRed(float r) {
		
		this.r = r;
	}
	
	public void setGreen(float g) {
		
		this.g = g;
	}
	
	public void setBlue(float b) {
		
		this.b = b;
	}
	
	public static void init() {
		
		instance = new RenderEngine();
		initiated = true;
	}
	
	public static boolean isInitiated() {
		
		return initiated;
	}
	
	public static Matrix4f ortho(float left, float right, float bottom, float top, float near, float far) {
		
		Matrix4f result = new Matrix4f();
		
		result.m00 = 2f / (right - left);
		result.m11 = 2f / (top - bottom);
		result.m22 = -2f / (far - near);
		result.m30 = -((right + left) / (right - left));
		result.m31 = -((top + bottom) / (top - bottom));
		result.m32 = (far + near) / (far - near);
		result.m33 = 1f;
		
		return result;
	}
	
	private static String loadShaderSource(String path) {
		
		StringBuilder result = new StringBuilder("");
		
		try {
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(path)));
			String line;
			while ((line = reader.readLine()) != null) {
				
				result.append(line).append("\n");
			}
		}
		catch (Exception ex) {
			
			System.err.println("Error when loading shader source");
			ex.printStackTrace();
		}
		
		return result.toString();
	}
	
	private RenderEngine() {
		
		projection = ortho(0, Display.getWidth(), 0, Display.getHeight(), -1, 1);
		
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);
		
		vboID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 32, 0);
		GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, 32, 8);
		GL20.glVertexAttribPointer(2, 2, GL11.GL_FLOAT, false, 32, 24);
		
		int[] index = new int[] {0, 1, 2, 0, 2, 3};
		quad = new Vector2f[] {
				new Vector2f(-0.5f, 0.5f),
				new Vector2f(-0.5f, -0.5f),
				new Vector2f(0.5f, -0.5f),
				new Vector2f(0.5f, 0.5f)
		};
		
		IntBuffer indecies = BufferUtils.createIntBuffer(index.length);
		indecies.put(index);
		indecies.flip();
		
		vboiID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiID);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indecies, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		
		int vs = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		
		GL20.glShaderSource(vs, loadShaderSource("com/goudagames/ld31/shader/vert.glsl"));
		GL20.glCompileShader(vs);
		
		int fs = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
		
		GL20.glShaderSource(fs, loadShaderSource("com/goudagames/ld31/shader/frag.glsl"));
		GL20.glCompileShader(fs);
		
		program = GL20.glCreateProgram();
		
		GL20.glAttachShader(program, vs);
		GL20.glAttachShader(program, fs);
		
		GL20.glBindAttribLocation(program, 0, "in_Position");
		GL20.glBindAttribLocation(program, 1, "in_Color");
		GL20.glBindAttribLocation(program, 2, "in_TexCoord");
		
		GL20.glLinkProgram(program);
		GL20.glValidateProgram(program);
		
		projLocation = GL20.glGetUniformLocation(program, "projection");
		modelLocation = GL20.glGetUniformLocation(program, "model");
		
		GL20.glUseProgram(program);
		
		r = g = b = a = 1f;
		
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		GL11.glClearColor(0f, 0f, 0f, 1f);
	}
}
